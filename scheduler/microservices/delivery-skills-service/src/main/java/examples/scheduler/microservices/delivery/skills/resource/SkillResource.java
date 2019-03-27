package examples.scheduler.microservices.delivery.skills.resource;

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

import examples.scheduler.domain.skill.Skill;
import examples.scheduler.microservices.delivery.skills.service.SkillService;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillResource {

    private SkillService service;

    @Autowired
    public SkillResource(SkillService service) {
        this.service = service;
    }

    @PostMapping("/")
    public Skill post(@NotNull @Valid @RequestBody Skill skill) {
        return service.create(skill);
    }

    @PutMapping("/")
    public Skill put(@NotNull @Valid @RequestBody Skill skill) {
        return service.update(skill);
    }

    @GetMapping("/{code}")
    public Skill get(@PathVariable String code) {
        return service.getByCode(code);
    }

    @GetMapping("/")
    public Collection<Skill> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{code}")
    public Skill delete(@PathVariable String code) {
        return service.delete(code);
    }

    @DeleteMapping("/")
    public Collection<Skill> deleteAll() {
        return service.deleteAll();
    }

}
