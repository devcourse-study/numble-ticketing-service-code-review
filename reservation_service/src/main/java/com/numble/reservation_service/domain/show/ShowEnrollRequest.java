package com.numble.reservation_service.domain.show;

import com.numble.reservation_service.domain.show.model.Capacity;
import com.numble.reservation_service.domain.show.model.Location;
import com.numble.reservation_service.domain.show.model.Price;
import com.numble.reservation_service.domain.show.model.Show;

import jakarta.validation.constraints.NotBlank;

public record ShowEnrollRequest(

	@NotBlank
	String name,

	@NotBlank
	String location,

	int price,

	int capacity,

	String description
) {
	public Show toEntity() {
		return new Show(name, new Location(location), new Price(price), new Capacity(capacity), description);
	}
}
