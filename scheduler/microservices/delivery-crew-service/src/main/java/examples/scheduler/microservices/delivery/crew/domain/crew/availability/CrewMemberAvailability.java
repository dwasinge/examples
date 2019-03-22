package examples.scheduler.microservices.delivery.crew.domain.crew.availability;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;

import examples.scheduler.microservices.delivery.crew.domain.common.AbstractPersistable;
import examples.scheduler.microservices.delivery.crew.domain.crew.CrewMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CrewMemberAvailability extends AbstractPersistable {

    @NotNull
    private CrewMember crewMember;

    @NotNull
    private OffsetDateTime startDateTime;
    @NotNull
    private OffsetDateTime endDateTime;

    @NotNull
    private CrewMemberAvailabilityState state;

}