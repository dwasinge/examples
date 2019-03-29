package examples.scheduler.domain;

import java.util.Set;

import examples.scheduler.domain.common.AbstractPersistable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeliveryRole extends AbstractPersistable {

	private String name;
	private Set<String> requiredSkillCodeSet;

}
