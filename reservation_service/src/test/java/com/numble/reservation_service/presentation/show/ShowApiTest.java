package com.numble.reservation_service.presentation.show;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.numble.reservation_service.domain.model.Email;
import com.numble.reservation_service.domain.model.Password;
import com.numble.reservation_service.domain.model.Phone;
import com.numble.reservation_service.domain.seller.Seller;
import com.numble.reservation_service.domain.seller.SellerRepository;
import com.numble.reservation_service.domain.show.ShowEnrollRequest;
import com.numble.reservation_service.presentation.test.ApiTest;

public class ShowApiTest extends ApiTest {

	@Autowired
	private SellerRepository sellerRepository;

	@DisplayName("사업자는 공연을 등록할 수 있다.")
	@Test
	public void enroll() throws Exception {
		Seller savedSeller = sellerRepository.save(new Seller(
			new Email("example@gmail.com"),
			new Password("qwer1234!"),
			new Phone("01012341234"),
			"110-11-11111",
			"홍길동"
		));

		ShowEnrollRequest request = new ShowEnrollRequest(
			"넘블",
			"넘블로 넘블길 23",
			100_000,
			20,
			"좋아요"
		);

		mockMvc.perform(post("/api/shows/{seller-id}", savedSeller.id())
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andDo(print())
			.andDo(document("show-enroll",
				pathParameters(
					parameterWithName("seller-id").description("판매자 식별자")
				),
				requestFields(
					fieldWithPath("name").description("공연 이름"),
					fieldWithPath("location").description("공연장 장소"),
					fieldWithPath("price").description("공연 가격"),
					fieldWithPath("capacity").description("공연 최대 수용 인원"),
					fieldWithPath("description").description("공연 설명")
				),
				responseFields(
					fieldWithPath("id").description("공연 식별자")
				)
			));
	}
}