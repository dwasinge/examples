package examples.scheduler.microservices.delivery.crew.service;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import examples.scheduler.domain.crew.CrewMember;
import examples.scheduler.domain.exception.RepositoryException;
import examples.scheduler.domain.exception.ResourceAlreadyExistsException;
import examples.scheduler.domain.exception.ResourceNotFoundException;
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
            getByUniqueIdentifier(crewMember.getAccountNumber());
        } catch (ResourceNotFoundException rnfe) {
            alreadyExists = false;
        }

        if (alreadyExists) {
            throw new ResourceAlreadyExistsException("resource with unique id '" + crewMember.getAccountNumber()
                    + "' already exists.  use PUT to update resource.");
        }

        // generate account number
        crewMember.setAccountNumber(UUID.randomUUID().toString());

        return repository.save(crewMember);

    }

    public CrewMember update(CrewMember crewMember) {

        // get existing resource, throw not found exception if it doesn't exist
        CrewMember persisted = getByUniqueIdentifier(crewMember.getAccountNumber());

        crewMember.setId(persisted.getId());
        return repository.save(crewMember);

    }

    public CrewMember getByUniqueIdentifier(String accountNumber) {

        CrewMember crewMember = null;

        try {
            crewMember = repository.findByAccountNumber(accountNumber);
        } catch (RuntimeException e) {
            throw new RepositoryException("an error occurred during retrieving resource with id '" + accountNumber
                    + "' from the repository.", e);
        }

        if (null == crewMember) {
            throw new ResourceNotFoundException("no resource found with id '" + accountNumber + "'");
        }

        return crewMember;

    }

    public Collection<CrewMember> getAll() {
        return repository.findAll();
    }

    public CrewMember delete(String accountNumber) {

        // get existing resource, throw not found exception if it doesn't exist
        CrewMember persisted = getByUniqueIdentifier(accountNumber);

        repository.deleteById(persisted.getId());

        return persisted;

    }

    public Collection<CrewMember> deleteAll() {

        Collection<CrewMember> crewMemberCollection = getAll();

        repository.deleteAll();

        return crewMemberCollection;

    }

}