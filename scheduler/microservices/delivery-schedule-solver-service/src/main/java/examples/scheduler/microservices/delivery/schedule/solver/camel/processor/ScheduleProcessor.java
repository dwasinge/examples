package examples.scheduler.microservices.delivery.schedule.solver.camel.processor;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import examples.scheduler.domain.DeliveryAssignment;
import examples.scheduler.domain.DeliveryRole;
import examples.scheduler.domain.DeliverySchedule;
import examples.scheduler.domain.crew.CrewMember;
import examples.scheduler.domain.crew.CrewMemberAvailability;
import examples.scheduler.microservices.delivery.schedule.solver.camel.EnrichScheduleRoute;

@Component
public class ScheduleProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		DeliverySchedule schedule = exchange.getIn().getHeader(EnrichScheduleRoute.SCHEDULE_HEADER,
				DeliverySchedule.class);

		CrewMember[] crewMemberArray = exchange.getIn().getHeader(EnrichScheduleRoute.CREW_MEMBER_LIST_HEADER,
				CrewMember[].class);
		if (null != crewMemberArray) {
			schedule.getCrewMemberList().addAll(Arrays.asList(crewMemberArray));
		}

		CrewMemberAvailability[] crewMemberAvailabilityArray = exchange.getIn()
				.getHeader(EnrichScheduleRoute.CREW_MEMBER_AVAILABILITY_LIST_HEADER, CrewMemberAvailability[].class);
		if (null != crewMemberAvailabilityArray) {
			schedule.getCrewMemberAvailabilityList().addAll(Arrays.asList(crewMemberAvailabilityArray));
		}

		DeliveryRole[] deliveryRoleArray = exchange.getIn().getHeader(EnrichScheduleRoute.DELIVERY_ROLE_LIST_HEADER,
				DeliveryRole[].class);
		if (null != deliveryRoleArray) {
			schedule.getDeliveryRoleList().addAll(Arrays.asList(deliveryRoleArray));
		}

		DeliveryAssignment[] deliveryAssignmentArray = exchange.getIn()
				.getHeader(EnrichScheduleRoute.DELIVERY_ASSIGNMENT_LIST_HEADER, DeliveryAssignment[].class);
		if (null != deliveryAssignmentArray) {
			schedule.getDeliveryAssignmentList().addAll(Arrays.asList(deliveryAssignmentArray));
		}

		exchange.getIn().setBody(schedule);

	}

}
