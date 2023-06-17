package com.numble.reservation_service.presentation.show;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.numble.reservation_service.domain.show.ShowEnrollRequest;
import com.numble.reservation_service.domain.show.ShowService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/shows")
public class ShowApi {

	private final ShowService showService;

	public ShowApi(ShowService showService) {
		this.showService = showService;
	}

	@PostMapping("{seller-id}")
	public ResponseEntity<ShowEnrollResponse> enroll(@Valid @RequestBody ShowEnrollRequest request,
		@PathVariable(value = "seller-id") Long sellerId) {
		Long enrolledId = showService.enroll(request, sellerId);

		URI redirectUri = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/{show_id}")
			.buildAndExpand(enrolledId)
			.toUri();

		return ResponseEntity.created(redirectUri)
			.body(new ShowEnrollResponse(enrolledId));
	}
}
