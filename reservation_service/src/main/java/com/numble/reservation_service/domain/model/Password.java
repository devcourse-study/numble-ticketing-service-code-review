package com.numble.reservation_service.domain.model;

import java.util.regex.Pattern;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Password {

	public static final String PASSWORD_NULL_MESSAGE = "Password value can not be null.";
	public static final String PASSWORD_INVALID_FORM_MESSAGE = "Password form is invalid.";

	@Column(name = "password", nullable = false, length = 50)
	private String value;

	private static final Pattern PASSWORD_PATTERN = Pattern.compile(
		"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");

	protected Password() {
	}

	public Password(String value) {
		validate(value);
		this.value = value;
	}

	private void validate(String value) {
		Assert.notNull(value, PASSWORD_NULL_MESSAGE);
		Assert.isTrue(PASSWORD_PATTERN.matcher(value).matches(), PASSWORD_INVALID_FORM_MESSAGE);
	}
}
