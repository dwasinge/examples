package examples.scheduler.microservices.delivery.schedule.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import examples.scheduler.domain.DeliveryAssignment;

@Repository
public interface DeliveryAssignmentRepository extends MongoRepository<DeliveryAssignment, String>{

}
