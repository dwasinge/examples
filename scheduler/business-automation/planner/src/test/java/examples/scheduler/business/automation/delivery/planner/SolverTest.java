package examples.scheduler.business.automation.delivery.planner;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import examples.scheduler.business.automation.delivery.planner.domain.DeliveryAssignment;
import examples.scheduler.business.automation.delivery.planner.domain.DeliveryRole;
import examples.scheduler.business.automation.delivery.planner.domain.DeliverySchedule;
import examples.scheduler.business.automation.delivery.planner.domain.crew.CrewMember;
import examples.scheduler.business.automation.delivery.planner.domain.crew.CrewMemberAvailability;
import examples.scheduler.business.automation.delivery.planner.domain.crew.CrewMemberAvailabilityState;
import examples.scheduler.business.automation.delivery.planner.util.SolverUtils;

public class SolverTest {

	@Test
	public void testSolveAllCrewAvailable() {

		// given
		DeliverySchedule unsolved = createUnsolvedSchedule();

		// when
		DeliverySchedule solved = SolverUtils.assignAircrewToDelivery(unsolved,
				"examples/scheduler/business/automation/delivery/planner/deliveryScheduleSolver.xml");
		Assert.assertNotNull(solved);

		// then
		List<DeliveryAssignment> assignmentList = solved.getDeliveryAssignmentList();
		Assert.assertNotNull(assignmentList);

		for (DeliveryAssignment assignment : assignmentList) {

			System.out.print("delivery role id:" + assignment.getDeliveryRoleId() + " - ");
			System.out.println("crew member assigned:" + assignment.getCrewMember().getLastName());

		}

	}

	@Test
	public void testSolveFryUnavailable() {

		// given
		DeliverySchedule unsolved = createUnsolvedSchedule();

		// add availablity constraint for fry
		OffsetDateTime startDateTime = OffsetDateTime.of(2019, 3, 1, 8, 0, 0, 0, ZoneOffset.UTC);
		OffsetDateTime endDateTime = OffsetDateTime.of(2019, 3, 1, 11, 0, 0, 0, ZoneOffset.UTC);
		CrewMemberAvailability availability = new CrewMemberAvailability("1", startDateTime, endDateTime, CrewMemberAvailabilityState.UNAVAILABLE);
		unsolved.getCrewMemberAvailabilityList().add(availability);

		// when
		DeliverySchedule solved = SolverUtils.assignAircrewToDelivery(unsolved,
				"examples/scheduler/business/automation/delivery/planner/deliveryScheduleSolver.xml");
		Assert.assertNotNull(solved);

		// then
		List<DeliveryAssignment> assignmentList = solved.getDeliveryAssignmentList();
		Assert.assertNotNull(assignmentList);

		for (DeliveryAssignment assignment : assignmentList) {

			System.out.print("delivery role id:" + assignment.getDeliveryRoleId() + " - ");
			System.out.println("crew member assigned:" + assignment.getCrewMember().getLastName());

		}

	}

	private DeliverySchedule createUnsolvedSchedule() {

		DeliverySchedule unsolved = new DeliverySchedule();
		unsolved.setId("0");

		// Delivery Roles
		DeliveryRole pilot = new DeliveryRole("0", "pilot", new HashSet<>(Arrays.asList("0")));
		DeliveryRole navigator = new DeliveryRole("1", "navigator", new HashSet<>(Arrays.asList("1")));
		DeliveryRole deliveryBoy = new DeliveryRole("2", "deliveryBoy", new HashSet<>(Arrays.asList("2")));
		DeliveryRole robotDeliveryBoy = new DeliveryRole("3", "robotDeliveryBoy", new HashSet<>(Arrays.asList("3")));

		unsolved.getDeliveryRoleList()
				.addAll(new ArrayList<>(Arrays.asList(pilot, navigator, deliveryBoy, robotDeliveryBoy)));

		// Crew
		CrewMember turanga = new CrewMember("0", "0", "Taranga", "Leela", new HashSet<>(Arrays.asList("0")));
		CrewMember philip = new CrewMember("1", "1", "Philip", "Fry", new HashSet<>(Arrays.asList("1", "2")));
		CrewMember bender = new CrewMember("2", "2", "Bender", "Rodriquez", new HashSet<>(Arrays.asList("2", "3")));
		CrewMember amy = new CrewMember("3", "3", "Amy", "Wong", new HashSet<>(Arrays.asList("1")));

		unsolved.getCrewMemberList().addAll(new ArrayList<>(Arrays.asList(turanga, philip, bender, amy)));

		// No Crew Member Availability Restrictions

		// Delivery Assignments to assign
		OffsetDateTime startTime = OffsetDateTime.of(2019, 3, 1, 10, 0, 0, 0, ZoneOffset.UTC);
		OffsetDateTime endTime = OffsetDateTime.of(2019, 3, 1, 12, 0, 0, 0, ZoneOffset.UTC);

		DeliveryAssignment assignPilot = new DeliveryAssignment("0", "0", startTime, endTime, null, pilot.getId());
		DeliveryAssignment assignNavigator = new DeliveryAssignment("1", "0", startTime, endTime, null,
				navigator.getId());
		DeliveryAssignment assignDeliveryBoy = new DeliveryAssignment("2", "0", startTime, endTime, null,
				deliveryBoy.getId());

		unsolved.getDeliveryAssignmentList()
				.addAll(new ArrayList<>(Arrays.asList(assignPilot, assignNavigator, assignDeliveryBoy)));

		return unsolved;

	}

}
