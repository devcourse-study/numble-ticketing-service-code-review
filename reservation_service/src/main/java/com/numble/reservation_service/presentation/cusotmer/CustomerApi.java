package com.numble.reservation_service.presentation.cusotmer;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.numble.reservation_service.domain.customer.CustomerEnrollRequest;
import com.numble.reservation_service.domain.customer.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerApi {

	private static final Logger log = LoggerFactory.getLogger(CustomerApi.class);

	private final CustomerService customerService;

	public CustomerApi(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping
	public ResponseEntity<CustomerEnrollResponse> enroll(@Valid @RequestBody CustomerEnrollRequest request) {
		Long enrolledId = customerService.enroll(request);
		URI redirectUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
			.path("/{customer_id}")
			.buildAndExpand(enrolledId)
			.toUri();

		return ResponseEntity.created(redirectUri)
			.body(new CustomerEnrollResponse(enrolledId));
	}
}
