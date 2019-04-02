package examples.scheduler.microservices.delivery.crew.resource;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import examples.scheduler.domain.crew.CrewMemberAvailability;
import examples.scheduler.microservices.delivery.crew.service.CrewMemberAvailabilityService;

@RestController
@RequestMapping("/api/v1/crew/members/availability")
public class CrewMemberAvailabilityResource {

    private CrewMemberAvailabilityService service;

    @Autowired
    public CrewMemberAvailabilityResource(CrewMemberAvailabilityService service) {
        this.service = service;
    }

    @PostMapping
    public CrewMemberAvailability post(@NotNull @Valid @RequestBody CrewMemberAvailability crewMemberAvailability) {
        return service.create(crewMemberAvailability);
    }

    @PutMapping
    public CrewMemberAvailability put(@NotNull @Valid @RequestBody CrewMemberAvailability crewMemberAvailability) {
        return service.update(crewMemberAvailability);
    }

    @GetMapping("/{id}")
    public CrewMemberAvailability get(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping
    public Collection<CrewMemberAvailability> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public CrewMemberAvailability delete(@PathVariable String id) {
        return service.delete(id);
    }

    @DeleteMapping
    public Collection<CrewMemberAvailability> deleteAll() {
        return service.deleteAll();
    }

}
