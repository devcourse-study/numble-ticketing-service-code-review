package com.numble.reservation_service.presentation.seller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.numble.reservation_service.domain.seller.SellerEnrollRequest;
import com.numble.reservation_service.domain.seller.SellerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sellers")
public class SellerApi {

	private final SellerService sellerService;

	public SellerApi(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	@PostMapping
	public ResponseEntity<SellerEnrollResponse> enroll(@Valid @RequestBody SellerEnrollRequest request) {
		Long enrolledId = sellerService.enroll(request);
		URI redirectUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
			.path("/{seller_id}")
			.buildAndExpand(enrolledId)
			.toUri();

		return ResponseEntity.created(redirectUri)
			.body(new SellerEnrollResponse(enrolledId));
	}
}
