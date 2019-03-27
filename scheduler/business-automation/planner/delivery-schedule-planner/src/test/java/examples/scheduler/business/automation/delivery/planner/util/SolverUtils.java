package examples.scheduler.business.automation.delivery.planner.util;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import examples.scheduler.business.automation.delivery.planner.SolverTest;
import examples.scheduler.business.automation.delivery.planner.domain.DeliverySchedule;

public class SolverUtils {

	public static DeliverySchedule assignAircrewToDelivery(DeliverySchedule unsolvedSchedule, String solverConfigFile) {

		KieContainer kieContainer = KieServices.Factory.get()
				.getKieClasspathContainer(SolverTest.class.getClassLoader());
		SolverFactory<DeliverySchedule> solverFactory = SolverFactory.createFromKieContainerXmlResource(kieContainer,
				solverConfigFile);
		Solver<DeliverySchedule> solver = solverFactory.buildSolver();
		return solver.solve(unsolvedSchedule);

	}

}
