package com.numble.reservation_service.domain.show.model.vo;

import java.util.regex.Pattern;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Location {

	public static final String SHOW_LOCATION_NULL_MESSAGE = "Show location can not be null.";
	public static final String SHOW_LOCATION_FORM_INVALID_MESSAGE = "Show location form is invalid.";
	private static final Pattern LOCATION_PATTERN = Pattern.compile(
		"(([가-힣A-Za-z·\\d~\\-\\.]{2,}(로|길).[\\d]+)" +
			"|([가-힣A-Za-z·\\d~\\-\\.]+(읍|동|번지)\\s)[\\d]+)" +
			"|([가-힣A-Za-z]+(구)+\\s*[가-힣A-Za-z]+(동))" +
			"|([가-힣a-zA-Z\\d]+(아파트|빌라|빌딩|마을))");

	@Column(name = "location")
	private String value;

	protected Location() {
	}

	public Location(String value) {
		validate(value);
		this.value = value;
	}

	private void validate(String value) {
		Assert.notNull(value, SHOW_LOCATION_NULL_MESSAGE);
		// Assert.isTrue(LOCATION_PATTERN.matcher(value).matches(), SHOW_LOCATION_FORM_INVALID_MESSAGE);
	}
}
