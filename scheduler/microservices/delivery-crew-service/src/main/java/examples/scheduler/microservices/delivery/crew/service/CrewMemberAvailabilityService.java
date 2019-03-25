package examples.scheduler.microservices.delivery.crew.service;

import java.util.Collection;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import examples.scheduler.microservices.delivery.crew.domain.crew.availability.CrewMemberAvailability;
import examples.scheduler.microservices.delivery.crew.exception.RepositoryException;
import examples.scheduler.microservices.delivery.crew.exception.ResourceAlreadyExistsException;
import examples.scheduler.microservices.delivery.crew.exception.ResourceNotFoundException;
import examples.scheduler.microservices.delivery.crew.repository.CrewMemberAvailabilityRepository;

@Component
public class CrewMemberAvailabilityService {

    private CrewMemberAvailabilityRepository repository;

    @Autowired
    public CrewMemberAvailabilityService(CrewMemberAvailabilityRepository repository) {
        this.repository = repository;
    }

    public CrewMemberAvailability create(CrewMemberAvailability crewMemberAvailability) {

        boolean alreadyExists = true;

        if (!StringUtils.isBlank(crewMemberAvailability.getId())) {
            try {
                getById(crewMemberAvailability.getId());
            } catch (ResourceNotFoundException rnfe) {
                alreadyExists = false;
            }
        } else {
            alreadyExists = false;
        }

        if (alreadyExists) {
            throw new ResourceAlreadyExistsException("resource with unique id '" + crewMemberAvailability.getId()
                    + "' already exists.  use PUT to update resource.");
        }

        return repository.save(crewMemberAvailability);

    }

    public CrewMemberAvailability update(CrewMemberAvailability crewMemberAvailability) {

        // get existing resource, throw not found exception if it doesn't exist
        CrewMemberAvailability persisted = getById(crewMemberAvailability.getId());

        crewMemberAvailability.setId(persisted.getId());
        return repository.save(crewMemberAvailability);

    }

    public CrewMemberAvailability getById(String id) {

        Optional<CrewMemberAvailability> optional = Optional.empty();

        try {
            optional = repository.findById(id);
        } catch (RuntimeException e) {
            throw new RepositoryException(
                    "an error occurred during retrieving resource with id '" + id + "' from the repository.", e);
        }

        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("no resource found with id '" + id + "'");
        }

        return optional.get();

    }

    public Collection<CrewMemberAvailability> getAll() {
        return repository.findAll();
    }

    public CrewMemberAvailability delete(String id) {

        // get existing resource, throw not found exception if it doesn't exist
        CrewMemberAvailability persisted = getById(id);

        repository.deleteById(id);

        return persisted;

    }

    public Collection<CrewMemberAvailability> deleteAll() {

        Collection<CrewMemberAvailability> crewMemberCollection = getAll();

        repository.deleteAll();

        return crewMemberCollection;

    }

}
