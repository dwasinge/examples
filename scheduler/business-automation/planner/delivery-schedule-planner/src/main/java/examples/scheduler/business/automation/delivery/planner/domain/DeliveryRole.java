package examples.scheduler.business.automation.delivery.planner.domain;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRole {

	private String id;

	private String name;
	private Set<String> requiredSkillCodeSet;

}
