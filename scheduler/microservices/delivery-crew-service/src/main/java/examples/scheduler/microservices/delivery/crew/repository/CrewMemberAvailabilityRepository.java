package examples.scheduler.microservices.delivery.crew.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import examples.scheduler.microservices.delivery.crew.domain.crew.availability.CrewMemberAvailability;

public interface CrewMemberAvailabilityRepository extends MongoRepository<CrewMemberAvailability, String> {

}
