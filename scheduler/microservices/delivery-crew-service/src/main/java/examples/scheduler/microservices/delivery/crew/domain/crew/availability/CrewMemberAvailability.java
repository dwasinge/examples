package examples.scheduler.microservices.delivery.crew.domain.crew.availability;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import examples.scheduler.microservices.delivery.crew.domain.common.AbstractPersistable;
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
    private OffsetDateTime startDateTime;
    @NotNull
    private OffsetDateTime endDateTime;

    @NotNull
    private CrewMemberAvailabilityState state;

}