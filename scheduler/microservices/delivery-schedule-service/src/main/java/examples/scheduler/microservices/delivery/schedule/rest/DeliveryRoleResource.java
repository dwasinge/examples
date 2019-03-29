package examples.scheduler.microservices.delivery.schedule.rest;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import examples.scheduler.domain.DeliveryRole;
import examples.scheduler.microservices.delivery.schedule.service.DeliveryRoleService;

@RestController
@RequestMapping("/api/v1")
public class DeliveryRoleResource {

	private DeliveryRoleService service;

	@Autowired
	public DeliveryRoleResource(DeliveryRoleService service) {
		this.service = service;
	}

	@RequestMapping(path = "/delivery/roles", method = RequestMethod.POST)
	public DeliveryRole post(@Valid @RequestBody DeliveryRole deliveryRole) {
		return service.create(deliveryRole);
	}

	@RequestMapping(path = "/delivery/roles", method = RequestMethod.PUT)
	public DeliveryRole put(@Valid @RequestBody DeliveryRole deliveryRole) {
		return service.update(deliveryRole);
	}

	@RequestMapping(path = "/delivery/roles/{id}", method = RequestMethod.GET)
	public DeliveryRole get(@PathVariable String id) {
		return service.get(id);
	}

	@RequestMapping(path = "/delivery/roles", method = RequestMethod.GET)
	public Collection<DeliveryRole> getAll() {
		return service.getAll();
	}

	@RequestMapping(path = "/delivery/roles/{id}", method = RequestMethod.DELETE)
	public DeliveryRole delete(@PathVariable String id) {
		return service.delete(id);
	}

	@RequestMapping(path = "/delivery/roles", method = RequestMethod.DELETE)
	public Collection<DeliveryRole> deleteAll() {
		return service.deleteAll();
	}

}
