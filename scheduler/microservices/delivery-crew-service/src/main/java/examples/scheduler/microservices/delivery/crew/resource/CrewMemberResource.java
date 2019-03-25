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

import examples.scheduler.microservices.delivery.crew.domain.crew.CrewMember;
import examples.scheduler.microservices.delivery.crew.service.CrewMemberService;

@RestController
@RequestMapping("/api/v1/crew/members")
public class CrewMemberResource {

    private CrewMemberService service;

    @Autowired
    public CrewMemberResource(CrewMemberService service) {
        this.service = service;
    }

    @PostMapping("/")
    public CrewMember post(@NotNull @Valid @RequestBody CrewMember crewMember) {
        return service.create(crewMember);
    }

    @PutMapping("/")
    public CrewMember put(@NotNull @Valid @RequestBody CrewMember crewMember) {
        return service.update(crewMember);
    }

    @GetMapping("/{accountNumber}")
    public CrewMember get(@PathVariable String accountNumber) {
        return service.getByUniqueIdentifier(accountNumber);
    }

    @GetMapping("/")
    public Collection<CrewMember> getAll() {
        return service.getAll();
    }

    @DeleteMapping("{accountNumber}")
    public CrewMember delete(@PathVariable String accountNumber) {
        return service.delete(accountNumber);
    }

    @DeleteMapping("/")
    public Collection<CrewMember> deleteAll() {
        return service.deleteAll();
    }

}
