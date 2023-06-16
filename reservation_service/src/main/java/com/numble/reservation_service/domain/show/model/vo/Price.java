package com.numble.reservation_service.domain.show.model.vo;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Price {

	public static final String PRICE_UNDER_ZERO_MESSAGE = "Price can not be under zero.";

	@Column(name = "price")
	private int value;

	protected Price() {
	}

	public Price(int value) {
		Assert.isTrue(value > 0, PRICE_UNDER_ZERO_MESSAGE);
		this.value = value;
	}
}
