package examples.scheduler.domain.crew;

import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import examples.scheduler.domain.common.AbstractPersistable;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CrewMember extends AbstractPersistable {

	@ApiModelProperty(hidden = true)
	private String accountNumber;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotNull
	private Set<String> skillProficiencyIdSet;

	public boolean hasSkill(String skillId) {
		return skillProficiencyIdSet.contains(skillId);
	}

	public boolean hasSkills(Collection<String> skillIds) {
		return skillProficiencyIdSet.containsAll(skillIds);
	}

}
