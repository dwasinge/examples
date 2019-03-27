package examples.scheduler.domain;

import java.time.OffsetDateTime;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import com.thoughtworks.xstream.annotations.XStreamConverter;

import examples.scheduler.domain.crew.CrewMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PlanningEntity
public class DeliveryAssignment {

	private String id;
	private String scheduleId;

	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime startDateTime;

	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime endDateTime;

	@PlanningVariable(valueRangeProviderRefs = { "availableCrewMember" }, nullable = true)
	private CrewMember crewMember;

	private String deliveryRoleId;

}
