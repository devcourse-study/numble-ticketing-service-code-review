package com.numble.reservation_service.domain.seller;

import org.springframework.stereotype.Service;

@Service
public class SellerService {

	private final SellerRepository sellerRepository;

	public SellerService(SellerRepository sellerRepository) {
		this.sellerRepository = sellerRepository;
	}

	public Long enroll(SellerEnrollRequest request) {
		Seller savedSeller = sellerRepository.save(request.toEntity());
		return savedSeller.id();
	}
}
