package com.numble.reservation_service.domain.seller;

import com.numble.reservation_service.domain.model.Email;
import com.numble.reservation_service.domain.model.Password;
import com.numble.reservation_service.domain.model.Phone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SellerEnrollRequest(
	@NotBlank
	String email,

	@NotBlank
	String password,

	@NotNull
	String licenseId,

	@NotBlank
	String name,

	@NotBlank
	String phone
) {
	public Seller toEntity() {
		return new Seller(new Email(email), new Password(password), new Phone(phone), licenseId, name);
	}
}
