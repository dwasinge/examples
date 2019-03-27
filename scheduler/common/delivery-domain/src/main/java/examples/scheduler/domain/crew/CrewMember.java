package examples.scheduler.business.automation.delivery.planner.domain.crew;

import java.util.Collection;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewMember {

	private String id;
	private String accountNumber;
	private String firstName;
	private String lastName;
	private Set<String> skillProficiencyIdSet;

	public boolean hasSkill(String skillId) {
		return skillProficiencyIdSet.contains(skillId);
	}

	public boolean hasSkills(Collection<String> skillIds) {
		return skillProficiencyIdSet.containsAll(skillIds);
	}

}
