package examples.scheduler.microservices.delivery.schedule.config;

import org.optaplanner.core.api.solver.SolverFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import examples.scheduler.domain.DeliverySchedule;

@Configuration
public class SolverConfig {

	private static final String SOLVER_CONFIG = "examples/scheduler/business/automation/delivery/planner/deliveryScheduleSolver.xml";

	@Bean
	public SolverFactory<DeliverySchedule> solverFactory() {
		return SolverFactory.createFromXmlResource(SOLVER_CONFIG);
	}

}
