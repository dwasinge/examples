package examples.scheduler.domain.skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

	private String id;
	private String code;
	private String name;
	private String description;

}
