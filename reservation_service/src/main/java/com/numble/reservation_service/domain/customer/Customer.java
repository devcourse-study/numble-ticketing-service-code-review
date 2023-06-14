package com.numble.reservation_service.domain.customer;

import java.util.Objects;

import org.springframework.util.Assert;

import com.numble.reservation_service.domain.model.Email;
import com.numble.reservation_service.domain.model.Password;
import com.numble.reservation_service.domain.model.Phone;
import com.numble.reservation_service.domain.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer extends User {

	public static final int NICKNAME_LENGTH = 30;
	public static final String NICKNAME_NULL_MESSAGE = "nickname can not be null.";
	public static final String NICKNAME_TOO_LONG_MESSAGE = "nickname must be no more than 30 characters.";
	public static final String AGE_TOO_LOW_MESSAGE = "age must be over zero.";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(unique = true, nullable = false, length = NICKNAME_LENGTH)
	private String nickname;

	@Enumerated(EnumType.STRING)
	private GENDER gender;

	private int age;

	protected Customer() {
		super();
	}

	public Customer(Email email, Password password, Phone phone, String nickname, GENDER gender, int age) {
		super(email, password, phone);
		validate(nickname, age);
		this.nickname = nickname;
		this.gender = Objects.requireNonNull(gender);
		this.age = age;
	}

	private void validate(String nickname, int age) {
		validateNickname(nickname);
		validateAge(age);
	}

	private void validateNickname(String nickname) {
		Assert.notNull(nickname, NICKNAME_NULL_MESSAGE);
		Assert.isTrue(nickname.length() <= NICKNAME_LENGTH, NICKNAME_TOO_LONG_MESSAGE);
	}

	private void validateAge(int age) {
		Assert.isTrue(age > 0, AGE_TOO_LOW_MESSAGE);
	}

	public Long id() {
		return id;
	}
}
