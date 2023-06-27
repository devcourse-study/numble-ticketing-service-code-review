package com.numble.reservation_service.presentation.schedule;

import java.util.List;

public record ScheduleEnrollResponse(
	List<Long> ids
) {
}
