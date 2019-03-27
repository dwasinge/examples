package examples.scheduler.microservices.delivery.crew.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import examples.scheduler.domain.crew.CrewMemberAvailability;

public interface CrewMemberAvailabilityRepository extends MongoRepository<CrewMemberAvailability, String> {

}
