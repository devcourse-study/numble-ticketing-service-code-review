package com.numble.reservation_service.domain.schedule;

import java.time.LocalDateTime;

import org.springframework.util.Assert;

import com.numble.reservation_service.domain.show.model.Show;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Schedule {

	public static final String SHOW_START_TIME_NULL_MESSAGE = "show start time can not be null.";
	public static final String SHOW_END_TIME_NULL_MESSAGE = "show end time can not be null.";
	public static final String START_LATER_THAN_END_MESSAGE = "Start time can not be later than end time.";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime start;

	@Column(nullable = false)
	private LocalDateTime end;

	@ManyToOne
	private Show show;

	protected Schedule() {
	}

	public Schedule(LocalDateTime start, LocalDateTime end) {
		validate(start, end);
		this.start = start;
		this.end = end;
	}

	private void validate(LocalDateTime start, LocalDateTime end) {
		Assert.notNull(start, SHOW_START_TIME_NULL_MESSAGE);
		Assert.notNull(end, SHOW_END_TIME_NULL_MESSAGE);
		Assert.isTrue(start.isBefore(end), START_LATER_THAN_END_MESSAGE);
	}

	public Show show() {
		return show;
	}
}
