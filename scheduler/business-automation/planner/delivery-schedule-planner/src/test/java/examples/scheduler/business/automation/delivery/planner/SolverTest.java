package examples.scheduler.business.automation.delivery.planner;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import examples.scheduler.domain.DeliveryAssignment;
import examples.scheduler.domain.DeliveryRole;
import examples.scheduler.domain.DeliverySchedule;
import examples.scheduler.domain.crew.CrewMember;
import examples.scheduler.domain.crew.CrewMemberAvailability;
import examples.scheduler.domain.crew.CrewMemberAvailabilityState;

public class SolverTest {

	private static final String SOLVER_XML = "deliveryScheduleSolver-test.xml";

	@Test
	public void testSolveAllCrewAvailable() {

		// given
		DeliverySchedule unsolved = createUnsolvedSchedule();

		// when
		DeliverySchedule solved = solve(unsolved, SOLVER_XML);
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
		DeliverySchedule solved = solve(unsolved, SOLVER_XML);
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
		DeliveryAssignment assignNavigator = new DeliveryAssignment("0", startTime, endTime, null,
				navigator.getId());
		assignNavigator.setId("1");
		DeliveryAssignment assignDeliveryBoy = new DeliveryAssignment("0", startTime, endTime, null,
				deliveryBoy.getId());
		assignDeliveryBoy.setId("2");

		unsolved.getDeliveryAssignmentList()
				.addAll(new ArrayList<>(Arrays.asList(assignPilot, assignNavigator, assignDeliveryBoy)));

		return unsolved;

	}

	private DeliverySchedule solve(DeliverySchedule unsolved, String solverConfigFile) {

		SolverFactory<DeliverySchedule> factory = SolverFactory.createFromXmlResource(solverConfigFile);
		Solver<DeliverySchedule> solver = factory.buildSolver();
		
		return solver.solve(unsolved);

	}

}
