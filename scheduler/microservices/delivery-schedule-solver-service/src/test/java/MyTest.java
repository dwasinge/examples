import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import examples.scheduler.domain.DeliveryAssignment;
import examples.scheduler.domain.DeliveryRole;
import examples.scheduler.domain.DeliverySchedule;
import examples.scheduler.domain.crew.CrewMember;
import org.junit.Assert;

public class MyTest extends CamelTestSupport {

	

	@Test
	public void testThis() {

		DeliverySchedule schedule = createUnsolvedSchedule();

		DeliverySchedule solved = (DeliverySchedule)template.requestBody("direct:in", schedule);
		Assert.assertNotNull(solved);

		for(DeliveryAssignment assignment : solved.getDeliveryAssignmentList() ) {

			System.out.println(assignment.getDeliveryRoleId() + " : " + assignment.getCrewMember().getLastName());

		}

	}

	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:in").to("optaplanner:examples/scheduler/business/automation/delivery/planner/deliveryScheduleSolver.xml");

				from("optaplanner:examples/scheduler/business/automation/delivery/planner/deliveryScheduleSolver.xml")
						.to("log:com.mycompany.order?showAll=true&multiline=true").to("mock:result");
			}
		};
	}

	private DeliverySchedule createUnsolvedSchedule() {

		DeliverySchedule unsolved = new DeliverySchedule();
		unsolved.setId("0");

		// Delivery Roles
		DeliveryRole pilot = new DeliveryRole("pilot", new HashSet<>(Arrays.asList("0")));
		pilot.setId("0");
		DeliveryRole navigator = new DeliveryRole("navigator", new HashSet<>(Arrays.asList("1")));
		navigator.setId("1");
		DeliveryRole deliveryBoy = new DeliveryRole("deliveryBoy", new HashSet<>(Arrays.asList("2")));
		navigator.setId("2");
		DeliveryRole robotDeliveryBoy = new DeliveryRole("robotDeliveryBoy", new HashSet<>(Arrays.asList("3")));
		robotDeliveryBoy.setId("3");

		unsolved.getDeliveryRoleList()
				.addAll(new ArrayList<>(Arrays.asList(pilot, navigator, deliveryBoy, robotDeliveryBoy)));

		// Crew
		CrewMember turanga = new CrewMember("0", "Taranga", "Leela", new HashSet<>(Arrays.asList("0")));
		turanga.setId("0");
		CrewMember philip = new CrewMember("1", "Philip", "Fry", new HashSet<>(Arrays.asList("1", "2")));
		philip.setId("1");
		CrewMember bender = new CrewMember("2", "Bender", "Rodriquez", new HashSet<>(Arrays.asList("2", "3")));
		bender.setId("2");
		CrewMember amy = new CrewMember("3", "Amy", "Wong", new HashSet<>(Arrays.asList("1")));
		amy.setId("3");

		unsolved.getCrewMemberList().addAll(new ArrayList<>(Arrays.asList(turanga, philip, bender, amy)));

		// No Crew Member Availability Restrictions

		// Delivery Assignments to assign
		OffsetDateTime startTime = OffsetDateTime.of(2019, 3, 1, 10, 0, 0, 0, ZoneOffset.UTC);
		OffsetDateTime endTime = OffsetDateTime.of(2019, 3, 1, 12, 0, 0, 0, ZoneOffset.UTC);

		DeliveryAssignment assignPilot = new DeliveryAssignment("0", startTime, endTime, null, pilot.getId());
		assignPilot.setId("0");
		DeliveryAssignment assignNavigator = new DeliveryAssignment("0", startTime, endTime, null, navigator.getId());
		assignNavigator.setId("1");
		DeliveryAssignment assignDeliveryBoy = new DeliveryAssignment("0", startTime, endTime, null,
				deliveryBoy.getId());
		assignDeliveryBoy.setId("2");

		unsolved.getDeliveryAssignmentList()
				.addAll(new ArrayList<>(Arrays.asList(assignPilot, assignNavigator, assignDeliveryBoy)));

		return unsolved;

	}

}
