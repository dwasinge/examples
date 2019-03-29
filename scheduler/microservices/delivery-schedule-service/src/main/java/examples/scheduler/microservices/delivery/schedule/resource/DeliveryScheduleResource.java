package examples.scheduler.microservices.delivery.schedule.resource;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import examples.scheduler.domain.DeliverySchedule;
import examples.scheduler.microservices.delivery.schedule.service.DeliveryScheduleService;

@RestController
@RequestMapping("/api/v1")
public class DeliveryScheduleResource {

	private DeliveryScheduleService service;

	@Autowired
	public DeliveryScheduleResource(DeliveryScheduleService service) {
		this.service = service;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/delivery/schedule", method = RequestMethod.POST)
	public DeliverySchedule post() {
		return service.create(new DeliverySchedule());
	}

	@RequestMapping(path = "/delivery/schedule", method = RequestMethod.PUT)
	public DeliverySchedule put(@Valid @RequestBody DeliverySchedule deliverySchedule) {
		return service.update(deliverySchedule);
	}

	@RequestMapping(path = "/delivery/schedule/{id}", method = RequestMethod.GET)
	public DeliverySchedule get(@PathVariable String id) {
		return service.get(id);
	}

	@RequestMapping(path = "/delivery/schedule", method = RequestMethod.GET)
	public Collection<DeliverySchedule> getAll() {
		return service.getAll();
	}

	@RequestMapping(path = "/delivery/schedule/{id}", method = RequestMethod.DELETE)
	public DeliverySchedule delete(@PathVariable String id) {
		return service.delete(id);
	}

	@RequestMapping(path = "/delivery/schedule", method = RequestMethod.DELETE)
	public Collection<DeliverySchedule> deleteAll() {
		return service.deleteAll();
	}

	@RequestMapping(path = "/delivery/schedule/{id}/solve", method = RequestMethod.PUT)
	public void solve(@PathVariable String id) {
		service.solve(id);
	}

	@RequestMapping(path = "/delivery/schedule/{id}/best/working/solution", method = RequestMethod.GET)
	public DeliverySchedule getBestSolution(@PathVariable String id) {
		return service.getWorkingSolution(id);
	}

}
