package com.numble.reservation_service.domain.schedule

import com.numble.reservation_service.domain.model.Email
import com.numble.reservation_service.domain.model.Password
import com.numble.reservation_service.domain.model.Phone
import com.numble.reservation_service.domain.seller.Seller
import com.numble.reservation_service.domain.seller.SellerRepository
import com.numble.reservation_service.domain.show.ShowRepository
import com.numble.reservation_service.domain.show.model.Capacity
import com.numble.reservation_service.domain.show.model.Location
import com.numble.reservation_service.domain.show.model.Price
import com.numble.reservation_service.domain.show.model.Show
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ScheduleRepositoryTest extends Specification {

    @Autowired
    ScheduleRepository scheduleRepository
    @Autowired
    ShowRepository showRepository
    @Autowired
    SellerRepository sellerRepository

    def "시작시간과 끝 시간이 판매자의 공연과 겹치는지 확인할 수 있다."() {
        given:
        def show = showRepository.save(new Show(
                "넘블",
                new Location("넘블로 넘블길 23"),
                new Price(100_000),
                new Capacity(20),
                "좋아요"
        ))

        def seller = sellerRepository.save(new Seller(
                new Email("example@google.com"),
                new Password("qwer1234!"),
                new Phone("01012341234"),
                "110-11-11111",
                "홍길동"
        ))
        show.registerSeller(seller)

        def requestTime = LocalDateTime.now().plusMonths(1L)

        def schedule = scheduleRepository.save(new Schedule(
                requestTime,
                requestTime.plusHours(1L)
        ))
        schedule.registerShow(show)

        when:
        def isOverlapped =
                scheduleRepository.isOverlapped(requestTime.plusMinutes(30L), requestTime.plusHours(2L), seller.id())

        then:
        isOverlapped == true

    }

    def "시작시간과 끝 시간이 판매자의 공연과 겹치는지 확인할 수 있다.1"() {
        given:
        def show = showRepository.save(new Show(
                "넘블",
                new Location("넘블로 넘블길 23"),
                new Price(100_000),
                new Capacity(20),
                "좋아요"
        ))

        def seller = sellerRepository.save(new Seller(
                new Email("example@google.com"),
                new Password("qwer1234!"),
                new Phone("01012341234"),
                "110-11-11111",
                "홍길동"
        ))
        show.registerSeller(seller)

        def requestTime = LocalDateTime.now().plusMonths(1L)

        def schedule = scheduleRepository.save(new Schedule(
                requestTime,
                requestTime.plusHours(1L)
        ))
        schedule.registerShow(show)

        when:
        def isOverlapped =
                scheduleRepository.isOverlapped(requestTime.plusHours(2L), requestTime.plusHours(3L), seller.id())

        then:
        isOverlapped == false

    }
}
