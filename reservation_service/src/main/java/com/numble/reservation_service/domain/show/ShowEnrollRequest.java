package com.numble.reservation_service.domain.show;

import com.numble.reservation_service.domain.show.model.entity.Show;
import com.numble.reservation_service.domain.show.model.vo.Capacity;
import com.numble.reservation_service.domain.show.model.vo.Location;
import com.numble.reservation_service.domain.show.model.vo.Price;

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
