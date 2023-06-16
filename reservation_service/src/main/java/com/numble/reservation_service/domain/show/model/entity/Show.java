package com.numble.reservation_service.domain.show.model.entity;

import java.util.Objects;

import org.springframework.util.Assert;

import com.numble.reservation_service.domain.seller.Seller;
import com.numble.reservation_service.domain.show.model.vo.Capacity;
import com.numble.reservation_service.domain.show.model.vo.Location;
import com.numble.reservation_service.domain.show.model.vo.Price;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shows")
public class Show {

	public static final String SHOW_DESCRIPTION_TOO_LONG_MESSAGE = "Show description is too long.";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;

	@Embedded
	private Location location;

	@Embedded
	private Price price;

	@Embedded
	private Capacity capacity;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	private Seller seller;

	protected Show() {
	}

	public Show(String name, Location location, Price price, Capacity capacity, String description) {
		validate(description);
		this.name = Objects.requireNonNull(name);
		this.location = Objects.requireNonNull(location);
		this.price = Objects.requireNonNull(price);
		this.capacity = Objects.requireNonNull(capacity);
		this.description = description;
	}

	private void validate(String description) {
		Assert.isTrue(description.length() <= 20_000, SHOW_DESCRIPTION_TOO_LONG_MESSAGE);
	}

	public Long id() {
		return this.id;
	}

	public void registerSeller(Seller seller) {
		this.seller = Objects.requireNonNull(seller);
	}
}
