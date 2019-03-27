package examples.scheduler.domain.common;

import org.springframework.data.annotation.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractPersistable {

	@Id
	@ApiModelProperty(hidden = true)
    private String id;

}
