package com.numble.reservation_service.presentation.cusotmer;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numble.reservation_service.domain.customer.CustomerEnrollRequest;
import com.numble.reservation_service.domain.customer.GENDER;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class CustomerApiTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void enroll() throws Exception {
		CustomerEnrollRequest request = new CustomerEnrollRequest(
			"example@google.com",
			"qwer1234!",
			"numble",
			GENDER.MAN,
			20,
			"01012341234"
		);

		mockMvc.perform(post("/api/customers")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andDo(document("customer-enroll",
				requestFields(
					fieldWithPath("email").description("사용자 이메일"),
					fieldWithPath("password").description("사용자 비밀번호"),
					fieldWithPath("nickname").description("사용자 닉네임"),
					fieldWithPath("gender").description("사용자 성별"),
					fieldWithPath("age").description("사용자 나이"),
					fieldWithPath("phone").description("사용자 번호")
				),
				responseFields(
					fieldWithPath("id").description("사용자 식별자")
				)
			));
	}
}