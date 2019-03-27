package examples.scheduler.microservices.delivery.crew.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import examples.scheduler.domain.crew.CrewMember;

public interface CrewMemberRepository extends MongoRepository<CrewMember, String> {

    CrewMember findByAccountNumber(String accountNumber);

}