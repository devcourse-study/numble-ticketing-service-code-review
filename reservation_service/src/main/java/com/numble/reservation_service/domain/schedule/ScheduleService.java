package com.numble.reservation_service.domain.schedule;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.numble.reservation_service.domain.show.ShowRepository;
import com.numble.reservation_service.domain.show.model.Show;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final ShowRepository showRepository;

	public ScheduleService(ScheduleRepository scheduleRepository, ShowRepository showRepository) {
		this.scheduleRepository = scheduleRepository;
		this.showRepository = showRepository;
	}

	public List<Long> enroll(ScheduleEnrollRequests requests, long sellerId) {
		Show show = showRepository.findById(requests.showId())
			.orElseThrow(() -> new EntityNotFoundException("Show can not found."));

		Assert.isTrue(show.verifySellerId(sellerId), "Seller doesn't own this show.");

		verifySchedulesOverlapped(requests, show.id());

		List<Schedule> savedSchedules = scheduleRepository.saveAll(requests.toEntities());

		savedSchedules.forEach(schedule -> schedule.registerShow(show));

		return savedSchedules.stream()
			.map(Schedule::id)
			.toList();
	}

	private void verifySchedulesOverlapped(ScheduleEnrollRequests requests, long showId) {
		requests.verifyOverlapped();
		// verifyRequestsOverlappedWithSaved(requests, showId);
	}

	private void verifyRequestsOverlappedWithSaved(ScheduleEnrollRequests requests, long showId) {
		requests.schedules()
			.forEach(schedule -> this.verifyRequestOverlappedWithSaved(showId, schedule));
	}

	private void verifyRequestOverlappedWithSaved(long showId, ScheduleEnrollRequests.ScheduleEnrollRequest schedule) {
		if (scheduleRepository.isOverlapped(schedule.start(), schedule.end(), showId)) {
			throw new IllegalArgumentException(
				"Request schedule is overlapped with enrolled schedules. [start]: %s, [end]: %s".formatted(
					schedule.start(), schedule.end()));
		}
	}
}
