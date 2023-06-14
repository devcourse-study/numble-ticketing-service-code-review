package com.numble.reservation_service.domain.seller;

import java.util.regex.Pattern;

import org.springframework.util.Assert;

import com.numble.reservation_service.domain.model.DefaultUserInfo;
import com.numble.reservation_service.domain.model.Email;
import com.numble.reservation_service.domain.model.Password;
import com.numble.reservation_service.domain.model.Phone;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Seller {

	private static final Pattern LICENSE_ID_PATTERN = Pattern.compile(
		"^(\\d{3})+-+(\\d{2})+-+(\\d{5})$");
	private static final Pattern NAME_PATTERN = Pattern.compile(
		"^[가-힣]{2,10}$");

	public static final String LICENSE_ID_NULL_MESSAGE = "License ID can not be null.";
	public static final String LICENSE_ID_INVALID_FORM_MESSAGE = "License ID form is invalid.";
	public static final String NAME_NULL_MESSAGE = "Name can not be null.";
	public static final String NAME_INVALID_FORM_MESSAGE = "Name form is invalid.";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private DefaultUserInfo userInfo;

	@Column(name = "license_id", unique = true, nullable = false, length = 12)
	private String LicenseId;

	@Column(length = 10)
	private String name;

	protected Seller() {
	}

	public Seller(Email email, Password password, Phone phone, String licenseId, String name) {
		this.userInfo = new DefaultUserInfo(email, password, phone);

		validate(licenseId, name);
		this.LicenseId = licenseId;
		this.name = name;
	}

	private void validate(String licenseId, String name) {
		validateLicenseId(licenseId);
		validateName(name);
	}

	private void validateName(String name) {
		Assert.notNull(name, NAME_NULL_MESSAGE);
		Assert.isTrue(NAME_PATTERN.matcher(name).matches(), NAME_INVALID_FORM_MESSAGE);
	}

	private void validateLicenseId(String licenseId) {
		Assert.notNull(licenseId, LICENSE_ID_NULL_MESSAGE);
		Assert.isTrue(LICENSE_ID_PATTERN.matcher(licenseId).matches(), LICENSE_ID_INVALID_FORM_MESSAGE);
	}

	public Long id() {
		return id;
	}
}
