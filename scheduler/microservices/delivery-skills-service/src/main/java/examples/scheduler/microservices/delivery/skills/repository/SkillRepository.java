package examples.scheduler.microservices.delivery.skills.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import examples.scheduler.microservices.delivery.skills.domain.Skill;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Long> {

    public Skill findByCode(String code);

}
