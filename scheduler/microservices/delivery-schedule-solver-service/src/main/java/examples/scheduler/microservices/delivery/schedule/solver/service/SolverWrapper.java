package examples.scheduler.microservices.delivery.schedule.solver.service;

import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Queue;

import org.apache.commons.lang3.StringUtils;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import examples.scheduler.domain.DeliverySchedule;
import examples.scheduler.microservices.delivery.schedule.solver.config.AmqConfig;

@Component
public class SolverWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(SolverWrapper.class);

	private ConcurrentHashMap<String, Solver<DeliverySchedule>> scheduleIdToSolverMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, SolverStatus> scheduleIdToSolverStatusMap = new ConcurrentHashMap<>();

	@Autowired
	private SolverFactory<DeliverySchedule> solverFactory;

	@Autowired
	private JmsMessagingTemplate template;

	@Autowired
	@Qualifier(AmqConfig.SOLVER_QUEUE)
	private Queue solvingQueue;

	@Autowired
	@Qualifier(AmqConfig.PERSIST_SCHEDULE_QUEUE)
	private Queue persistScheduleQueue;

	@Autowired
	@Qualifier(AmqConfig.ERROR_QUEUE)
	private Queue errorQueue;

	@Autowired
	private ObjectMapper objectMapper;

	// TODO: Need to handle errors and place on appropriate queue.

	public void solve(DeliverySchedule unsolved) {

		// cannot continue without schedule id
		String scheduleId = unsolved.getId();
		if (StringUtils.isBlank(scheduleId)) {
			throw new IllegalArgumentException("cannot get/create solver because schedule id is blank.");
		}

		// return if already solving
		if (scheduleIdToSolverStatusMap.containsKey(scheduleId)
				&& SolverStatus.SOLVING == scheduleIdToSolverStatusMap.get(scheduleId)) {
			LOGGER.debug("returning because solver already in solving state.");
			return;
		}

		Solver<DeliverySchedule> solver = scheduleIdToSolverMap.get(scheduleId);
		if (null == solver) {
			solver = solverFactory.buildSolver();
			scheduleIdToSolverMap.put(scheduleId, solver);
			LOGGER.debug("solver created for schedule id {}", scheduleId);
		}

		// add event listener - need to persist the schedule when updated
		solver.addEventListener(new SolverEventListener<DeliverySchedule>() {

			@Override
			public void bestSolutionChanged(BestSolutionChangedEvent<DeliverySchedule> solution) {

				String json = null;

				try {

					// marshal schedule to json
					json = objectMapper.writeValueAsString(solution.getNewBestSolution());

					// place new best solution on queue to update
					template.convertAndSend(persistScheduleQueue, json);

					LOGGER.debug("new best solution changed and placed on persist queue.");

				} catch (JsonProcessingException e) {
					// TODO: Handle this better
					LOGGER.error("failed to marshal and place on queue new best solution {}",
							solution.getNewBestSolution(), e);
				}

			}
		});

		// start solving
		try {

			scheduleIdToSolverStatusMap.put(scheduleId, SolverStatus.SOLVING);
			solver.solve(unsolved);
			LOGGER.debug("solving has finished for schedule id {}", scheduleId);
			/*
			 * NOTE: Shouldn't have to put on persist queue again because the last best
			 * solution should have been place on queue by event listener.
			 */

		} finally {

			scheduleIdToSolverMap.remove(scheduleId);
			scheduleIdToSolverStatusMap.put(scheduleId, SolverStatus.NOT_SOLVING);
			LOGGER.debug("solver removed for schedule id {}", scheduleId);

		}

	}

	public void terminate(String scheduleId) {

		Solver<DeliverySchedule> solver = scheduleIdToSolverMap.get(scheduleId);

		if (null != solver) {
			solver.terminateEarly();
			// not sure if should change status to terminating early or not solving
			scheduleIdToSolverStatusMap.put(scheduleId, SolverStatus.NOT_SOLVING);
		} else {
			throw new IllegalStateException(
					"The schedule with scheduletId (" + scheduleId + ") is not being solved currently.");
		}

		LOGGER.debug("solver set to terminate early.");

	}

}
