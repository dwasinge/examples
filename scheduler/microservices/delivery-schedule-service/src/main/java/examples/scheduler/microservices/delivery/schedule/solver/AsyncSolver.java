package examples.scheduler.microservices.delivery.schedule.solver;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.kie.server.api.model.instance.SolverInstance.SolverStatus;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import examples.scheduler.domain.DeliverySchedule;

@Component
public class AsyncSolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncSolver.class);

	private ConcurrentHashMap<String, Solver<DeliverySchedule>> scheduleIdToSolverMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, SolverStatus> scheduleIdToSolverStatusMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, DeliverySchedule> scheduleIdToBestSolution = new ConcurrentHashMap<>();

	private SolverFactory<DeliverySchedule> solverFactory;

	@Autowired
	public AsyncSolver(SolverFactory<DeliverySchedule> solverFactory) {
		this.solverFactory = solverFactory;
	}

	@Async
	public CompletableFuture<DeliverySchedule> asyncSolve(DeliverySchedule unsolved) {

		DeliverySchedule solved = null;

		// cannot continue without schedule id
		String scheduleId = unsolved.getId();
		if (StringUtils.isBlank(scheduleId)) {
			throw new IllegalArgumentException("cannot get/create solver because schedule id is blank.");
		}

//		if (scheduleIdToSolverStatusMap.containsKey(scheduleId)
//				&& SolverStatus.SOLVING == scheduleIdToSolverStatusMap.get(scheduleId)) {
//			LOGGER.debug("returning because solver already in solving state.");
//			return;
//		}

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

				scheduleIdToBestSolution.put(scheduleId, solution.getNewBestSolution());
				LOGGER.debug("best solution changed and should persist schedule");

			}
		});

		try {

			scheduleIdToSolverStatusMap.put(scheduleId, SolverStatus.SOLVING);
			solved = solver.solve(unsolved);
			LOGGER.debug("solving has finished for schedule id {}", scheduleId);

		} finally {

			scheduleIdToSolverMap.remove(scheduleId);
			scheduleIdToSolverStatusMap.put(scheduleId, SolverStatus.NOT_SOLVING);
			LOGGER.debug("solver removed for schedule id {}", scheduleId);

		}

		return CompletableFuture.completedFuture(solved);

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

	public Optional<DeliverySchedule> getWorkingSolution(String scheduleId) {

		Optional<DeliverySchedule> optional = Optional.empty();

		if (scheduleIdToBestSolution.containsKey(scheduleId)) {
			optional = Optional.of(scheduleIdToBestSolution.get(scheduleId));
		}

		return optional;

	}

}
