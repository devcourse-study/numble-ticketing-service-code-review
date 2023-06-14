package com.numble.reservation_service.domain.model;

import java.util.Objects;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User {

	@Embedded
	private Email email;

	@Embedded
	private Password password;

	@Embedded
	private Phone phone;

	protected User() {
	}

	public User(Email email, Password password, Phone phone) {
		this.email = Objects.requireNonNull(email);
		this.password = Objects.requireNonNull(password);
		this.phone = Objects.requireNonNull(phone);
	}
}
