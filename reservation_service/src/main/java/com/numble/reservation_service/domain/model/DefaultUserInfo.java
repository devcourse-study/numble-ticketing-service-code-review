package com.numble.reservation_service.domain.model;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class DefaultUserInfo {

	@Embedded
	private Email email;

	@Embedded
	private Password password;

	@Embedded
	private Phone phone;

	protected DefaultUserInfo() {
	}

	public DefaultUserInfo(Email email, Password password, Phone phone) {
		this.email = Objects.requireNonNull(email);
		this.password = Objects.requireNonNull(password);
		this.phone = Objects.requireNonNull(phone);
	}
}
