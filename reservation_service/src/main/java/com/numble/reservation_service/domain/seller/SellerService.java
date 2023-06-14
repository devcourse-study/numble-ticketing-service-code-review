package com.numble.reservation_service.domain.seller;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
