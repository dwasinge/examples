package examples.scheduler.microservices.delivery.schedule.solver.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import examples.scheduler.microservices.delivery.schedule.solver.config.AmqConfig;

@Component
public class PersistScheduleRoute extends RouteBuilder {

	private static final String PERSIST_SCHEDULE_ROUTE_ID = "persist-schedule-route";
	private static final String PERSIST_SCHEDULE_ROUTE = "direct:" + PERSIST_SCHEDULE_ROUTE_ID;

	private static final String PERSIST_SCHEDULE_QUEUE_PRODUCER = "activemq:" + AmqConfig.PERSIST_SCHEDULE_QUEUE;

	@Override
	public void configure() throws Exception {

		from(PERSIST_SCHEDULE_ROUTE)
			.routeId(PERSIST_SCHEDULE_ROUTE_ID)
			.to(PERSIST_SCHEDULE_QUEUE_PRODUCER);

	}

}
