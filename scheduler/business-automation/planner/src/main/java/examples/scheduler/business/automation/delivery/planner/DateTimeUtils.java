package examples.scheduler.business.automation.delivery.planner;

import java.time.OffsetDateTime;

public class DateTimeUtils {

	public static boolean doTimeslotsIntersect(OffsetDateTime start1, OffsetDateTime end1, OffsetDateTime start2,
			OffsetDateTime end2) {
		boolean answer = !start1.isAfter(end2) && !end1.isBefore(start2);
		return answer;
	}

}
