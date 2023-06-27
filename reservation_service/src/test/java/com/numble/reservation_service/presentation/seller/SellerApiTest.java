package com.numble.reservation_service.presentation.seller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.numble.reservation_service.domain.seller.SellerEnrollRequest;
import com.numble.reservation_service.presentation.test.ApiTest;

class SellerApiTest extends ApiTest {

	@DisplayName("사업자는 회원가입할 수 있다.")
	@Test
	void enroll() throws Exception {
		SellerEnrollRequest request = new SellerEnrollRequest(
			"example@google.com",
			"qwer1234!",
			"110-11-11111",
			"홍길동",
			"01012341234"
		);

		mockMvc.perform(post("/api/sellers")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andDo(document("seller-enroll",
				requestFields(
					fieldWithPath("email").description("판매자 이메일"),
					fieldWithPath("password").description("판매자 비밀번호"),
					fieldWithPath("licenseId").description("사업자 번호"),
					fieldWithPath("name").description("대표자 이름"),
					fieldWithPath("phone").description("판매자 번호")
				),
				responseFields(
					fieldWithPath("id").description("판매자 식별자")
				)
			));

	}
}