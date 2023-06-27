package com.numble.reservation_service.presentation.schedule;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.numble.reservation_service.domain.model.Email;
import com.numble.reservation_service.domain.model.Password;
import com.numble.reservation_service.domain.model.Phone;
import com.numble.reservation_service.domain.schedule.ScheduleEnrollRequests;
import com.numble.reservation_service.domain.seller.Seller;
import com.numble.reservation_service.domain.seller.SellerRepository;
import com.numble.reservation_service.domain.show.ShowRepository;
import com.numble.reservation_service.domain.show.model.Capacity;
import com.numble.reservation_service.domain.show.model.Location;
import com.numble.reservation_service.domain.show.model.Price;
import com.numble.reservation_service.domain.show.model.Show;
import com.numble.reservation_service.presentation.test.ApiTest;

class ScheduleApiTest extends ApiTest {

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private ShowRepository showRepository;

	@DisplayName("사업자는 공연에 스케쥴을 추가할 수 있다.")
	@Test
	void enroll() throws Exception {

		Seller seller = sellerRepository.save(new Seller(
			new Email("example@gmail.com"),
			new Password("qwer1234!"),
			new Phone("01012341234"),
			"110-11-11111",
			"홍길동"
		));
		Show show = showRepository.save(new Show(
			"넘블",
			new Location("넘블로 넘블길 23"),
			new Price(100_000),
			new Capacity(20),
			"좋아요"
		));
		show.registerSeller(seller);

		LocalDateTime requestTime1 = LocalDateTime.now().plusMonths(1L);
		LocalDateTime requestTime2 = LocalDateTime.now().plusMonths(1L).plusHours(2L);
		ScheduleEnrollRequests.ScheduleEnrollRequest request1 = new ScheduleEnrollRequests.ScheduleEnrollRequest(
			requestTime1, requestTime1.plusHours(1L));
		ScheduleEnrollRequests.ScheduleEnrollRequest request2 = new ScheduleEnrollRequests.ScheduleEnrollRequest(
			requestTime2, requestTime2.plusHours(1L));
		ScheduleEnrollRequests requests = new ScheduleEnrollRequests(List.of(request1, request2), show.id());

		mockMvc.perform(post("/api/schedules?" + "seller-id=" + seller.id())
				.content(objectMapper.writeValueAsString(requests))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("schedules-enroll",
				queryParameters(
					parameterWithName("seller-id").description("판매자 식별자")
				),
				requestFields(
					fieldWithPath("schedules[]").description("스케쥴 리스트"),
					fieldWithPath("schedules[].start").description("스케쥴 시작 시간"),
					fieldWithPath("schedules[].start").description("스케쥴 끝 시간"),
					fieldWithPath("sellerId").description("판매자 식별자")
				),
				responseFields(
					fieldWithPath("ids").description("생성된 스케쥴 아이디 리스트")
				)
			));
	}
}