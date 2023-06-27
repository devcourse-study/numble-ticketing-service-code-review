package com.numble.reservation_service.domain.show.model;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Capacity {

	public static final String CAPACITY_UNDER_ZERO_MESSAGE = "Capacity can not be under zero.";

	@Column(name = "capacity")
	private int value;

	protected Capacity() {
	}

	public Capacity(int value) {
		Assert.isTrue(value > 0, CAPACITY_UNDER_ZERO_MESSAGE);
		this.value = value;
	}
}
