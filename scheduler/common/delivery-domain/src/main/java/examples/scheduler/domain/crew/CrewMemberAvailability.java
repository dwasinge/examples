package examples.scheduler.domain.crew;

import java.time.OffsetDateTime;

import com.thoughtworks.xstream.annotations.XStreamConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewMemberAvailability {

	private String crewMemberId;

	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime startDateTime;
	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime endDateTime;

	private CrewMemberAvailabilityState state;

}
