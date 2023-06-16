package com.ticketing.presentation.customer;

import com.ticketing.domain.member.customer.dto.CustomerCreateReq;
import com.ticketing.domain.member.customer.dto.CustomerCreateRes;
import com.ticketing.domain.member.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping
  public ResponseEntity<CustomerCreateRes> signUp(
      @RequestBody CustomerCreateReq createReq
  ) {
    CustomerCreateRes createRes = customerService.create(createReq);
    return ResponseEntity.status(HttpStatus.CREATED).body(createRes);
  }
}
