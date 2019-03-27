package examples.scheduler.domain.crew;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.thoughtworks.xstream.annotations.XStreamConverter;

import examples.scheduler.domain.common.AbstractPersistable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CrewMemberAvailability extends AbstractPersistable {

	@NotBlank
	private String crewMemberId;

	@NotNull
	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime startDateTime;
	@NotNull
	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime endDateTime;
	@NotNull
	private CrewMemberAvailabilityState state;

}
