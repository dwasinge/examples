package examples.scheduler.microservices.delivery.schedule.solver.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import examples.scheduler.microservices.delivery.schedule.solver.config.AmqConfig;

@Component
public class SolveScheduleRoute extends RouteBuilder{

	private static final String SOLVER_SCHEDULE_ROUTE_ID = "solver-schedule-route";

	private static final String SOLVER_QUEUE_CONSUMER = "activemq:" + AmqConfig.SOLVER_QUEUE;

	@Override
	public void configure() throws Exception {

		from(SOLVER_QUEUE_CONSUMER)
			.routeId(SOLVER_SCHEDULE_ROUTE_ID)
			.to(EnrichScheduleRoute.SCHEDULE_ENRICHMENT_ROUTE)
			.to("solverWrapper");
		
	}

}
