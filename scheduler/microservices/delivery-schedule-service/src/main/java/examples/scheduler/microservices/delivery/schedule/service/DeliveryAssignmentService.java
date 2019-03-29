package examples.scheduler.microservices.delivery.schedule.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import examples.scheduler.domain.DeliveryAssignment;

@Component
public class DeliveryAssignmentService extends AbstractMongoService<DeliveryAssignment> {

	public DeliveryAssignmentService(MongoRepository<DeliveryAssignment, String> repository) {
		super(repository);
	}

}
