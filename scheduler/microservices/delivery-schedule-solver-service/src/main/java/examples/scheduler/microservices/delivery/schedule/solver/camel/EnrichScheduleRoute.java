package examples.scheduler.microservices.delivery.schedule.solver.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import examples.scheduler.domain.DeliveryAssignment;
import examples.scheduler.domain.DeliveryRole;
import examples.scheduler.domain.DeliverySchedule;
import examples.scheduler.domain.crew.CrewMember;
import examples.scheduler.domain.crew.CrewMemberAvailability;

@Component
public class EnrichScheduleRoute extends RouteBuilder {

	public static final String SCHEDULE_ENRICHMENT_ROUTE_ID = "enrich-schedule-route";
	public static final String SCHEDULE_ENRICHMENT_ROUTE = "direct:" + SCHEDULE_ENRICHMENT_ROUTE_ID;

	public static final String CREW_MEMBER_ENRICHMENT_ROUTE_ID = "crew-member-enrichment-route";
	public static final String CREW_MEMBER_ENRICHMENT_ROUTE = "direct:" + CREW_MEMBER_ENRICHMENT_ROUTE_ID;

	public static final String CREW_MEMBER_AVAILABILITY_ENRICHMENT_ROUTE_ID = "crew-member-availability-enrichment-route";
	public static final String CREW_MEMBER_AVAILABILITY_ENRICHMENT_ROUTE = "direct:" + CREW_MEMBER_AVAILABILITY_ENRICHMENT_ROUTE_ID;
	
	public static final String DELIVERY_ROLE_ROUTE_ID = "delivery-role-route";
	public static final String DELIVERY_ROLE_ROUTE = "direct:" + DELIVERY_ROLE_ROUTE_ID;

	public static final String DELIVERY_ASSIGNMENT_ROUTE_ID = "delivery-assignment-route";
	public static final String DELIVERY_ASSIGNMENT_ROUTE = "direct:" + DELIVERY_ASSIGNMENT_ROUTE_ID;

	public static final String SCHEDULE_HEADER = "schedule";
	public static final String CREW_MEMBER_LIST_HEADER = "crew-member-list";
	public static final String CREW_MEMBER_AVAILABILITY_LIST_HEADER = "crew-member-availability-list";
	public static final String DELIVERY_ROLE_LIST_HEADER = "delivery-role-list";
	public static final String DELIVERY_ASSIGNMENT_LIST_HEADER = "delivery-assignment-list";
	
	@Override
	public void configure() throws Exception {

		//getContext().setTracing(true);

		from(SCHEDULE_ENRICHMENT_ROUTE)
			.routeId(SCHEDULE_ENRICHMENT_ROUTE_ID)
			.unmarshal()
				.json(JsonLibrary.Jackson, DeliverySchedule.class)
			.setHeader(SCHEDULE_HEADER, simple("${body}"))
			.setBody(simple("${null}"))
			.to(CREW_MEMBER_ENRICHMENT_ROUTE)
			.to(DELIVERY_ROLE_ROUTE)
			.to(DELIVERY_ASSIGNMENT_ROUTE)
			.to("scheduleProcessor");

		// get all crew members
		from(CREW_MEMBER_ENRICHMENT_ROUTE)
			.routeId(CREW_MEMBER_ENRICHMENT_ROUTE_ID)
			.enrich("{{crew.member.service.endpoint}}")
			.unmarshal()
				.json(JsonLibrary.Jackson, CrewMember[].class)
			.setHeader(CREW_MEMBER_LIST_HEADER, simple("${body}"))
			.setBody(simple("${null}"));

		// get all crew member availability 
		from(CREW_MEMBER_AVAILABILITY_ENRICHMENT_ROUTE)
			.routeId(CREW_MEMBER_AVAILABILITY_ENRICHMENT_ROUTE_ID)
			.enrich("{{crew.member.availability.service.endpoint}}")
			.unmarshal()
				.json(JsonLibrary.Jackson, CrewMemberAvailability[].class)
				.setHeader(CREW_MEMBER_AVAILABILITY_LIST_HEADER, simple("${body}"))
				.setBody(simple("${null}"));
		
		// get all roles
		from(DELIVERY_ROLE_ROUTE)
			.routeId(DELIVERY_ROLE_ROUTE_ID)
			.enrich("{{delivery.schedule.role.endpoint}}")
			.unmarshal()
				.json(JsonLibrary.Jackson, DeliveryRole[].class)
			.setHeader(DELIVERY_ROLE_LIST_HEADER, simple("${body}"))
			.setBody(simple("${null}"));

		// get all assignments
		from(DELIVERY_ASSIGNMENT_ROUTE)
			.routeId(DELIVERY_ASSIGNMENT_ROUTE_ID)
			.enrich("{{delivery.schedule.assignment.endpoint}}")
			.unmarshal()
				.json(JsonLibrary.Jackson, DeliveryAssignment[].class)
			.setHeader(DELIVERY_ASSIGNMENT_LIST_HEADER, simple("${body}"))
			.setBody(simple("${null}"));

	}

}
