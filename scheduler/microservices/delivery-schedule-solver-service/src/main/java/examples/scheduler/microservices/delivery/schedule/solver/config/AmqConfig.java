package examples.scheduler.microservices.delivery.schedule.solver.config;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@Configuration
public class AmqConfig {

	public static final String SOLVER_QUEUE = "solver-queue";
	public static final String TERMINATE_EARLY_QUEUE = "terminate-early-queue";
	public static final String PERSIST_SCHEDULE_QUEUE = "persist-schedule-queue";
	public static final String ERROR_QUEUE = "error-queue";

	@Bean(SOLVER_QUEUE)
	public Queue solveQueue() {
		return new ActiveMQQueue(SOLVER_QUEUE);
	}

	@Bean(TERMINATE_EARLY_QUEUE)
	public Queue terminateEarlyQueue() {
		return new ActiveMQQueue(TERMINATE_EARLY_QUEUE);
	}

	@Bean(PERSIST_SCHEDULE_QUEUE)
	public Queue persisteScheduleQueue() {
		return new ActiveMQQueue(PERSIST_SCHEDULE_QUEUE);
	}

	@Bean(ERROR_QUEUE)
	public Queue errorQueue() {
		return new ActiveMQQueue(ERROR_QUEUE);
	}

}
