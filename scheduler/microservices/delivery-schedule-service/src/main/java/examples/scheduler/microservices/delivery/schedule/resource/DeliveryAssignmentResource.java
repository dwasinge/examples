package examples.scheduler.microservices.delivery.schedule.resource;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import examples.scheduler.domain.DeliveryAssignment;
import examples.scheduler.microservices.delivery.schedule.service.DeliveryAssignmentService;

@RestController
@RequestMapping("/api/v1")
public class DeliveryAssignmentResource {

	private DeliveryAssignmentService service;

	@Autowired
	public DeliveryAssignmentResource(DeliveryAssignmentService service) {
		this.service = service;
	}

	@RequestMapping(path = "/delivery/assignment", method = RequestMethod.POST)
	public DeliveryAssignment post(@Valid @RequestBody DeliveryAssignment deliveryAssignment) {
		return service.create(deliveryAssignment);
	}

	@RequestMapping(path = "/delivery/assignment", method = RequestMethod.PUT)
	public DeliveryAssignment put(@Valid @RequestBody DeliveryAssignment deliveryAssignment) {
		return service.update(deliveryAssignment);
	}

	@RequestMapping(path = "/delivery/assignment/{id}", method = RequestMethod.GET)
	public DeliveryAssignment get(@PathVariable String id) {
		return service.get(id);
	}

	@RequestMapping(path = "/delivery/assignment", method = RequestMethod.GET)
	public Collection<DeliveryAssignment> getAll() {
		return service.getAll();
	}

	@RequestMapping(path = "/delivery/assignment/{id}", method = RequestMethod.DELETE)
	public DeliveryAssignment delete(@PathVariable String id) {
		return service.delete(id);
	}

	@RequestMapping(path = "/delivery/assignment", method = RequestMethod.DELETE)
	public Collection<DeliveryAssignment> deleteAll() {
		return service.deleteAll();
	}

}
