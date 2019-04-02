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

import examples.scheduler.domain.DeliverySchedule;
import examples.scheduler.microservices.delivery.schedule.service.DeliveryScheduleService;

@RestController
@RequestMapping("/api/v1/delivery/schedule")
public class DeliveryScheduleResource {

	private DeliveryScheduleService service;

	@Autowired
	public DeliveryScheduleResource(DeliveryScheduleService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DeliverySchedule post() {
		return service.create(new DeliverySchedule());
	}

	@PutMapping
	public DeliverySchedule put(@Valid @RequestBody DeliverySchedule deliverySchedule) {
		return service.update(deliverySchedule);
	}

	@GetMapping("/{id}")
	public DeliverySchedule get(@PathVariable String id) {
		return service.get(id);
	}

	@GetMapping
	public Collection<DeliverySchedule> getAll() {
		return service.getAll();
	}

	@DeleteMapping("/{id}")
	public DeliverySchedule delete(@PathVariable String id) {
		return service.delete(id);
	}

	@DeleteMapping
	public Collection<DeliverySchedule> deleteAll() {
		return service.deleteAll();
	}

	@PutMapping("/{id}/solve")
	public void solve(@PathVariable String id) {
		service.solve(id);
	}

	@PutMapping("/{id}/terminate")
	public void terminate(@PathVariable String id) {
		service.terminateEarly(id);
	}

}
