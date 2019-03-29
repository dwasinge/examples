package examples.scheduler.domain;

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

import examples.scheduler.domain.common.AbstractPersistable;
import examples.scheduler.domain.crew.CrewMember;
import examples.scheduler.domain.crew.CrewMemberAvailability;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(callSuper = true)
@PlanningSolution(autoDiscoverMemberType = AutoDiscoverMemberType.FIELD)
public class DeliverySchedule extends AbstractPersistable {

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
