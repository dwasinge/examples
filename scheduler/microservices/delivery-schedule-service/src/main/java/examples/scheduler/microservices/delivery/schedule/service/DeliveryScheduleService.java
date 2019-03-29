package examples.scheduler.microservices.delivery.schedule.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import examples.scheduler.domain.DeliverySchedule;
import examples.scheduler.domain.exception.ResourceNotFoundException;
import examples.scheduler.microservices.delivery.schedule.camel.ScheduleEnrichmentRoute;
import examples.scheduler.microservices.delivery.schedule.solver.AsyncSolver;

@Component
public class DeliveryScheduleService extends AbstractMongoService<DeliverySchedule> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryScheduleService.class);

	private ProducerTemplate template;
	private AsyncSolver solver;

	@Autowired
	public DeliveryScheduleService(MongoRepository<DeliverySchedule, String> repository, ProducerTemplate template,
			AsyncSolver solver) {
		super(repository);
		this.template = template;
		this.solver = solver;
	}

	public void solve(String scheduleId) {

		// Get schedule with id
		DeliverySchedule scheduleToSolve = get(scheduleId);

		// try to enrich schedule before solving
		DeliverySchedule enrichedSchedule = (DeliverySchedule) template
				.sendBody(ScheduleEnrichmentRoute.SCHEDULE_ENRICHMENT_ROUTE, ExchangePattern.InOut, scheduleToSolve);

		// start the solver route
		CompletableFuture<DeliverySchedule> completableFuture = solver.asyncSolve(enrichedSchedule);
		CompletableFuture.allOf(completableFuture);

		// update the schedule in the data store
		try {
			update(completableFuture.get());
		} catch (Exception e) {
			LOGGER.error("an exception happened when trying to get schedule from future.", e);
		}

	}

	public DeliverySchedule getWorkingSolution(String scheduleId) {

		Optional<DeliverySchedule> optional = solver.getWorkingSolution(scheduleId);
		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no working solution found for schedule id '" + scheduleId + "'");
		}

		return optional.get();

	}

	public void terminateEarly(String scheduleId) {
		solver.terminate(scheduleId);
	}

}
