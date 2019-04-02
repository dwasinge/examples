package examples.scheduler.microservices.delivery.schedule.resource;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import examples.scheduler.domain.DeliveryRole;
import examples.scheduler.microservices.delivery.schedule.service.DeliveryRoleService;

@RestController
@RequestMapping("/api/v1/delivery/roles")
public class DeliveryRoleResource {

	private DeliveryRoleService service;

	@Autowired
	public DeliveryRoleResource(DeliveryRoleService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DeliveryRole post(@Valid @RequestBody DeliveryRole deliveryRole) {
		return service.create(deliveryRole);
	}

	@PutMapping
	public DeliveryRole put(@Valid @RequestBody DeliveryRole deliveryRole) {
		return service.update(deliveryRole);
	}

	@GetMapping("/{id}")
	public DeliveryRole get(@PathVariable String id) {
		return service.get(id);
	}

	@GetMapping
	public Collection<DeliveryRole> getAll() {
		return service.getAll();
	}

	@DeleteMapping("/{id}")
	public DeliveryRole delete(@PathVariable String id) {
		return service.delete(id);
	}

	@DeleteMapping
	public Collection<DeliveryRole> deleteAll() {
		return service.deleteAll();
	}

}
