package com.numble.reservation_service.domain.schedule;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public record ScheduleEnrollRequests(

	List<ScheduleEnrollRequest> schedules,

	@NotNull
	Long showId
) {

	public static final String REQUEST_OVERLAPPED_MESSAGE = "Request Schedule is overlapped. [start]: %s, [end]: %s";

	public List<Schedule> toEntities() {
		return schedules.stream()
			.map(ScheduleEnrollRequest::toEntity)
			.toList();
	}

	public void verifyOverlapped() {
		schedules.forEach(schedule -> {
			if (schedule.isOverlappedNotEquals(schedules))
				throw new IllegalArgumentException(REQUEST_OVERLAPPED_MESSAGE.formatted(
					schedule.start(), schedule.end()));
		});
	}

	public record ScheduleEnrollRequest(

		@NotNull
		LocalDateTime start,

		@NotNull
		LocalDateTime end
	) {
		private Schedule toEntity() {
			return new Schedule(start, end);
		}

		private boolean isOverlappedNotEquals(ScheduleEnrollRequest comparedSchedule) {
			return this.start.isBefore(comparedSchedule.end)
				&& this.end.isAfter(comparedSchedule.start)
				&& !this.equals(comparedSchedule);
		}

		public boolean isOverlappedNotEquals(List<ScheduleEnrollRequest> schedules) {
			return schedules.stream()
				.anyMatch(this::isOverlappedNotEquals);
		}
	}
}
