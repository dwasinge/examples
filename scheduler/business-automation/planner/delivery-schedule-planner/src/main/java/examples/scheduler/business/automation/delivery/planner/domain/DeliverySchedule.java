package examples.scheduler.business.automation.delivery.planner.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.optaplanner.core.api.domain.autodiscover.AutoDiscoverMemberType;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.persistence.jaxb.api.score.buildin.hardmediumsoft.HardMediumSoftScoreJaxbXmlAdapter;

import examples.scheduler.business.automation.delivery.planner.domain.crew.CrewMember;
import examples.scheduler.business.automation.delivery.planner.domain.crew.CrewMemberAvailability;
import examples.scheduler.business.automation.delivery.planner.domain.skill.Skill;
import lombok.Data;

@Data
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
@PlanningSolution(autoDiscoverMemberType = AutoDiscoverMemberType.FIELD)
public class DeliverySchedule {

	private String id;

//	@ProblemFactCollectionProperty
//	private List<Skill> skillList = new ArrayList<>();

	@ProblemFactCollectionProperty
	private List<DeliveryRole> deliveryRoleList = new ArrayList<>();

	@ValueRangeProvider(id = "availableCrewMember")
	@ProblemFactCollectionProperty
	private List<CrewMember> crewMemberList = new ArrayList<>();

	@ProblemFactCollectionProperty
	private List<CrewMemberAvailability> crewMemberAvailabilityList = new ArrayList<>();

	@PlanningEntityCollectionProperty
	private List<DeliveryAssignment> deliveryAssignmentList = new ArrayList<>();

	@PlanningScore
	@XmlJavaTypeAdapter(HardMediumSoftScoreJaxbXmlAdapter.class)
	private HardMediumSoftScore score = null;

}
