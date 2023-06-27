package com.numble.reservation_service.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Phone {

	public static final String PHONE_NULL_MESSAGE = "Phone value can not be null.";
	public static final String PHONE_INVALID_FORM_MESSAGE = "Phone form is invalid.";

	@Column(name = "phone", unique = true, nullable = false, length = 11)
	private String value;

	private static final Pattern PHONE_PATTERN = Pattern.compile("^010-?([0-9]{4})-?([0-9]{4})$");

	protected Phone() {
	}

	public Phone(String value) {
		validate(value);
		this.value = Objects.requireNonNull(value);
	}

	private void validate(String value) {
		Assert.notNull(value, PHONE_NULL_MESSAGE);
		Assert.isTrue(PHONE_PATTERN.matcher(value).matches(), PHONE_INVALID_FORM_MESSAGE);
	}
}
