package examples.scheduler.microservices.delivery.schedule.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import examples.scheduler.domain.DeliveryRole;

@Repository
public interface DeliveryRoleRepository extends MongoRepository<DeliveryRole, String>{

}
