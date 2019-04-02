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

import examples.scheduler.domain.DeliveryAssignment;
import examples.scheduler.microservices.delivery.schedule.service.DeliveryAssignmentService;

@RestController
@RequestMapping("/api/v1/delivery/assignments")
public class DeliveryAssignmentResource {

	private DeliveryAssignmentService service;

	@Autowired
	public DeliveryAssignmentResource(DeliveryAssignmentService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DeliveryAssignment post(@Valid @RequestBody DeliveryAssignment deliveryAssignment) {
		return service.create(deliveryAssignment);
	}

	@PutMapping
	public DeliveryAssignment put(@Valid @RequestBody DeliveryAssignment deliveryAssignment) {
		return service.update(deliveryAssignment);
	}

	@GetMapping("/{id}")
	public DeliveryAssignment get(@PathVariable String id) {
		return service.get(id);
	}

	@GetMapping
	public Collection<DeliveryAssignment> getAll() {
		return service.getAll();
	}

	@DeleteMapping("/{id}")
	public DeliveryAssignment delete(@PathVariable String id) {
		return service.delete(id);
	}

	@DeleteMapping
	public Collection<DeliveryAssignment> deleteAll() {
		return service.deleteAll();
	}

}
