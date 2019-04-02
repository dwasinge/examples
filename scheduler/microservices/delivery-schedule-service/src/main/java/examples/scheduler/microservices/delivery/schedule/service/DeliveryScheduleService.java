package examples.scheduler.microservices.delivery.schedule.service;

import java.io.IOException;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import examples.scheduler.domain.DeliverySchedule;
import examples.scheduler.domain.exception.ResourceNotFoundException;
import examples.scheduler.microservices.delivery.schedule.config.AmqConfig;

@Component
public class DeliveryScheduleService extends AbstractMongoService<DeliverySchedule> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryScheduleService.class);

	@Autowired
	private JmsMessagingTemplate template;

	@Autowired
	@Qualifier(AmqConfig.SOLVER_QUEUE)
	private Queue solverQueue;

	@Autowired
	@Qualifier(AmqConfig.TERMINATE_EARLY_QUEUE)
	private Queue terminateEarlyQueue;

	@Autowired
	@Qualifier(AmqConfig.PERSIST_SCHEDULE_QUEUE)
	private Queue persistScheduleQueue;

	@Autowired
	private ObjectMapper objectMapper;

	public void solve(String scheduleId) {

		// Get Schedule By Id
		DeliverySchedule schedule = get(scheduleId);

		// marshal to json
		String json = null;
		try {
			json = objectMapper.writeValueAsString(schedule);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("failed to marshal schedule to json.");
		}

		// place schedule on the solve queue
		template.convertAndSend(solverQueue, json);

		LOGGER.debug("schedule with id {} sent to solving queue.", scheduleId);

	}

	public void terminateEarly(String scheduleId) {

		// place shedule id on the terminate queue
		template.convertAndSend(terminateEarlyQueue, scheduleId);

		LOGGER.debug("schedule id {} sent to terminate early queue.", scheduleId);

	}

	@JmsListener(destination = AmqConfig.PERSIST_SCHEDULE_QUEUE)
	public void persistScheduleFromQueue(String jsonSchedule) {

		LOGGER.debug("processing {} from persist schedule queue. ", jsonSchedule);

		DeliverySchedule schedule = null;

		try {
			// unmarshal json to DeliverySchedule
			schedule = objectMapper.readValue(jsonSchedule, DeliverySchedule.class);
		} catch (IOException e) {
			LOGGER.error("failed to unmarshal json to delivery schedule {}", jsonSchedule);
			return;
		}

		try {
			// persist the schedule 
			update(schedule);
			LOGGER.debug("updated schedule with id {}", schedule.getId());
		} catch (ResourceNotFoundException e) {
			create(schedule);
			LOGGER.debug("no schedule found to update so created schedule with id {}", schedule.getId());
		}

	}

}
