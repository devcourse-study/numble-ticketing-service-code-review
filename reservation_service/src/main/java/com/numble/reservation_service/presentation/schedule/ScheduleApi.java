package com.numble.reservation_service.presentation.schedule;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.numble.reservation_service.domain.schedule.ScheduleEnrollRequests;
import com.numble.reservation_service.domain.schedule.ScheduleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleApi {

	private final ScheduleService scheduleService;

	public ScheduleApi(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@PostMapping
	public ResponseEntity<ScheduleEnrollResponse> enroll(
		@Valid @RequestBody ScheduleEnrollRequests requests,
		@RequestParam("seller-id") Long sellerId
	) {
		List<Long> enrolledIds = scheduleService.enroll(requests, sellerId);

		return ResponseEntity.ok()
			.body(new ScheduleEnrollResponse(enrolledIds));
	}
}
