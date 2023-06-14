package com.numble.reservation_service.domain.customer;

import com.numble.reservation_service.domain.model.Email;
import com.numble.reservation_service.domain.model.Password;
import com.numble.reservation_service.domain.model.Phone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerEnrollRequest(

	@NotBlank
	String email,

	@NotBlank
	String password,

	@NotBlank
	String nickname,

	@NotNull
	GENDER gender,

	int age,

	@NotBlank
	String phone
) {
	public Customer toEntity() {
		return new Customer(new Email(email), new Password(password), new Phone(phone), nickname, gender, age);
	}
}
