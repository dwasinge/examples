package examples.scheduler.microservices.delivery.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import examples.scheduler.domain.DeliveryRole;

@Component
public class DeliveryRoleService extends AbstractMongoService<DeliveryRole>{

	@Autowired
	public DeliveryRoleService(MongoRepository<DeliveryRole, String> repository) {
		super(repository);
	}

}
