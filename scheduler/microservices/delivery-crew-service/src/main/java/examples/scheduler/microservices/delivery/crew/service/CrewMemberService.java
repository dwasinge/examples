package examples.scheduler.microservices.delivery.crew.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import examples.scheduler.microservices.delivery.crew.domain.crew.CrewMember;
import examples.scheduler.microservices.delivery.crew.exception.RepositoryException;
import examples.scheduler.microservices.delivery.crew.exception.ResourceAlreadyExistsException;
import examples.scheduler.microservices.delivery.crew.exception.ResourceNotFoundException;
import examples.scheduler.microservices.delivery.crew.repository.CrewMemberRepository;

@Component
public class CrewMemberService {

    private CrewMemberRepository repository;

    @Autowired
    public CrewMemberService(CrewMemberRepository repository) {
        this.repository = repository;
    }

    public CrewMember create(CrewMember crewMember) {

        boolean alreadyExists = true;

        try {
            getByUniqueIdentifier(crewMember.getUniqueIdentifier());
        } catch (ResourceNotFoundException rnfe) {
            alreadyExists = false;
        }

        if (alreadyExists) {
            throw new ResourceAlreadyExistsException("resource with unique id '" + crewMember.getUniqueIdentifier()
                    + "' already exists.  use PUT to update resource.");
        }

        return repository.save(crewMember);

    }

    public CrewMember update(CrewMember crewMember) {

        // get existing resource, throw not found exception if it doesn't exist
        CrewMember persisted = getByUniqueIdentifier(crewMember.getUniqueIdentifier());

        crewMember.setId(persisted.getId());
        return repository.save(crewMember);

    }

    public CrewMember getByUniqueIdentifier(String uniqueIdentifier) {

        CrewMember crewMember = null;

        try {
            crewMember = repository.findByUniqueIdentifier(uniqueIdentifier);
        } catch (RuntimeException e) {
            throw new RepositoryException("an error occurred during retrieving resource with id '" + uniqueIdentifier
                    + "' from the repository.", e);
        }

        if (null == crewMember) {
            throw new ResourceNotFoundException("no resource found with id '" + uniqueIdentifier + "'");
        }

        return crewMember;

    }

    public Collection<CrewMember> getAll() {
        return repository.findAll();
    }

    public CrewMember delete(String uniqueIdentifier) {

        // get existing resource, throw not found exception if it doesn't exist
        CrewMember persisted = getByUniqueIdentifier(uniqueIdentifier);

        repository.deleteById(persisted.getId());

        return persisted;

    }

    public Collection<CrewMember> deleteAll() {

        Collection<CrewMember> crewMemberCollection = getAll();

        repository.deleteAll();

        return crewMemberCollection;

    }

}