package examples.scheduler.microservices.delivery.crew.domain.crew;

import java.util.Set;

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
public class CrewMember extends AbstractPersistable {

    @NotBlank
    private String uniqueIdentifier;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Set<Long> skillProficiencyIdSet;

}