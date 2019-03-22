package examples.scheduler.microservices.delivery.crew.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import examples.scheduler.microservices.delivery.crew.domain.crew.CrewMember;

public interface CrewMemberRepository extends MongoRepository<CrewMember, String> {

    CrewMember findByUniqueIdentifier(String uniqueIdentifier);

}