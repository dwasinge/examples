package examples.scheduler.microservices.delivery.schedule.camel;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import examples.scheduler.domain.DeliveryAssignment;
import examples.scheduler.domain.DeliveryRole;
import examples.scheduler.domain.DeliverySchedule;
import examples.scheduler.domain.crew.CrewMember;
import examples.scheduler.microservices.delivery.schedule.service.DeliveryAssignmentService;
import examples.scheduler.microservices.delivery.schedule.service.DeliveryRoleService;

@Component
public class ScheduleEnrichmentRoute extends RouteBuilder {

	public static final String SCHEDULE_ENRICHMENT_ROUTE_ID = "schedule-enrichment-route";
	public static final String SCHEDULE_ENRICHMENT_ROUTE = "direct:" + SCHEDULE_ENRICHMENT_ROUTE_ID;

	public static final String CREW_MEMBER_ENRICHMENT_ROUTE_ID = "crew-member-enrichment-route";
	public static final String CREW_MEMBER_ENRICHMENT_ROUTE = "direct:" + CREW_MEMBER_ENRICHMENT_ROUTE_ID;

	public static final String DELIVERY_ROLE_ROUTE_ID = "delivery-role-route";
	public static final String DELIVERY_ROLE_ROUTE = "direct:" + DELIVERY_ROLE_ROUTE_ID;

	public static final String DELIVERY_ASSIGNMENT_ROUTE_ID = "delivery-assignment-route";
	public static final String DELIVERY_ASSIGNMENT_ROUTE = "direct:" + DELIVERY_ASSIGNMENT_ROUTE_ID;

	@Override
	public void configure() throws Exception {

		//getContext().setTracing(true);

		from(SCHEDULE_ENRICHMENT_ROUTE)
			.routeId(SCHEDULE_ENRICHMENT_ROUTE_ID)
			.setHeader("schedule", simple("${body}"))
			.setBody(simple("${null}"))
			.to(CREW_MEMBER_ENRICHMENT_ROUTE)
			.to(DELIVERY_ROLE_ROUTE)
			.to(DELIVERY_ASSIGNMENT_ROUTE)
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {

					DeliverySchedule schedule = exchange.getIn().getHeader("schedule", DeliverySchedule.class);

					CrewMember[] crewMemberArray = exchange.getIn().getHeader("crewMemberList", CrewMember[].class);
					schedule.getCrewMemberList().addAll(Arrays.asList(crewMemberArray));

					DeliveryRole[] deliveryRoleArray = exchange.getIn().getHeader("deliveryRoleList", DeliveryRole[].class);
					schedule.getDeliveryRoleList().addAll(Arrays.asList(deliveryRoleArray));

					DeliveryAssignment[] deliveryAssignmentArray = exchange.getIn().getHeader("deliveryAssignmentList", DeliveryAssignment[].class);
					schedule.getDeliveryAssignmentList().addAll(Arrays.asList(deliveryAssignmentArray));

					exchange.getIn().setBody(schedule);
					
					
				}
			});

		// get all crew member from the configured service
		from(CREW_MEMBER_ENRICHMENT_ROUTE)
			.routeId(CREW_MEMBER_ENRICHMENT_ROUTE_ID)
			.enrich("{{crew.member.service.endpoint}}")
			.unmarshal()
				.json(JsonLibrary.Jackson, CrewMember[].class)
			.setHeader("crewMemberList", simple("${body}"))
			.setBody(simple("${null}"));

		// get all roles
		from(DELIVERY_ROLE_ROUTE)
			.routeId(DELIVERY_ROLE_ROUTE_ID)
			.bean(DeliveryRoleService.class, "getAll")
			.setHeader("deliveryRoleList", simple("${body}"))
			.setBody(simple("${null}"));

		// get all assignments
		from(DELIVERY_ASSIGNMENT_ROUTE)
			.routeId(DELIVERY_ASSIGNMENT_ROUTE_ID)
			.bean(DeliveryAssignmentService.class, "getAll")
			.setHeader("deliveryAssignmentList", simple("${body}"))
			.setBody(simple("${null}"));

		// TODO:  get all crew member assignments

	}

}
