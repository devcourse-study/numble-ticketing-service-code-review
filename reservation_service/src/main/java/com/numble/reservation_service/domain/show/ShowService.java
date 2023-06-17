package com.numble.reservation_service.domain.show;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation_service.domain.seller.Seller;
import com.numble.reservation_service.domain.seller.SellerRepository;
import com.numble.reservation_service.domain.show.model.entity.Show;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class ShowService {

	public static final String SELLER_NOT_MEMBER_MESSAGE = "Seller id don't enrolled.";
	private final ShowRepository showRepository;
	private final SellerRepository sellerRepository;

	public ShowService(ShowRepository showRepository, SellerRepository sellerRepository) {
		this.showRepository = showRepository;
		this.sellerRepository = sellerRepository;
	}

	public Long enroll(ShowEnrollRequest request, Long sellerId) {
		Seller seller = sellerRepository.findById(sellerId)
			.orElseThrow(() -> new EntityNotFoundException(SELLER_NOT_MEMBER_MESSAGE));

		Show show = request.toEntity();
		show.registerSeller(seller);
		Show savedShow = showRepository.save(show);
		return savedShow.id();
	}
}
