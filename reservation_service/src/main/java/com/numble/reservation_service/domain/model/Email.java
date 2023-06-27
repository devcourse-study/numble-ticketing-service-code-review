package com.numble.reservation_service.domain.model;

import java.util.regex.Pattern;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Email {

	private static final Pattern EMAIL_PATTERN = Pattern.compile(
		"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
	public static final String EMAIL_NULL_MESSAGE = "Email value can not be null.";
	public static final String EMAIL_INVALID_FORM_MESSAGE = "Email form is invalid.";

	@Column(name = "email", unique = true, nullable = false, length = 50)
	private String value;

	protected Email() {
	}

	public Email(String value) {
		validate(value);
		this.value = value;
	}

	private void validate(String value) {
		Assert.notNull(value, EMAIL_NULL_MESSAGE);
		Assert.isTrue(EMAIL_PATTERN.matcher(value).matches(), EMAIL_INVALID_FORM_MESSAGE);
	}
}
