package com.numble.reservation_service.domain.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Long enroll(CustomerEnrollRequest request) {
		Customer savedCustomer = customerRepository.save(request.toEntity());
		return savedCustomer.id();
	}
}
