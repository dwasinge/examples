package examples.scheduler.microservices.delivery.skills.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import examples.scheduler.domain.exception.RepositoryException;
import examples.scheduler.domain.exception.ResourceAlreadyExistsException;
import examples.scheduler.domain.exception.ResourceNotFoundException;
import examples.scheduler.domain.skill.Skill;
import examples.scheduler.microservices.delivery.skills.repository.SkillRepository;

@Component
public class SkillService {

    private SkillRepository repository;

    @Autowired
    public SkillService(SkillRepository repository) {
        this.repository = repository;
    }

    public Skill create(Skill skill) {

        boolean alreadyExists = true;

        try {
            getByCode(skill.getCode());
        } catch (ResourceNotFoundException rnfe) {
            alreadyExists = false;
        }

        if (alreadyExists) {
            throw new ResourceAlreadyExistsException("resource with unique code '" + skill.getCode()
                    + "' already exists.  use PUT to update resource.");
        }

        return repository.save(skill);

    }

    public Skill update(Skill skill) {

        // get existing resource, throw not found exception if it doesn't exist
        Skill persisted = getByCode(skill.getCode());

        skill.setId(persisted.getId());
        return repository.save(skill);

    }

    public Skill getByCode(String code) {

        Skill skill = null;

        try {
            skill = repository.findByCode(code);
        } catch (RuntimeException e) {
            throw new RepositoryException(
                    "an error occurred during retrieving resource with code '" + code + "' from the repository.", e);
        }

        if (null == skill) {
            throw new ResourceNotFoundException("no resource found with code '" + code + "'");
        }

        return skill;

    }

    public Collection<Skill> getAll() {

        Iterable<Skill> iterable = repository.findAll();
        List<Skill> skillList = new ArrayList<>();

        iterable.forEach(skillList::add);

        return skillList;

    }

    public Skill delete(String code) {

        Skill skill = getByCode(code);

        repository.deleteById(skill.getId());

        return skill;

    }

    public Collection<Skill> deleteAll() {

        Collection<Skill> skillList = getAll();

        repository.deleteAll();

        return skillList;

    }

}
