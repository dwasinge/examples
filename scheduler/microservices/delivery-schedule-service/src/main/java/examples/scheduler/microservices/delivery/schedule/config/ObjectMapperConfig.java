package examples.scheduler.microservices.delivery.schedule.config;

import org.optaplanner.persistence.jackson.api.OptaPlannerJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;

@Configuration
public class ObjectMapperConfig {

	@Bean
	public Module optaPlannerJacksonModule() {
		return OptaPlannerJacksonModule.createModule();
	}

}
