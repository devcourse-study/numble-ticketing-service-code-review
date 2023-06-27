package com.numble.reservation_service.domain.schedule

import com.numble.reservation_service.domain.model.Email
import com.numble.reservation_service.domain.model.Password
import com.numble.reservation_service.domain.model.Phone
import com.numble.reservation_service.domain.seller.Seller
import com.numble.reservation_service.domain.show.ShowRepository
import com.numble.reservation_service.domain.show.model.Capacity
import com.numble.reservation_service.domain.show.model.Location
import com.numble.reservation_service.domain.show.model.Price
import com.numble.reservation_service.domain.show.model.Show
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

import java.time.LocalDateTime

import static com.numble.reservation_service.domain.schedule.ScheduleEnrollRequests.ScheduleEnrollRequest

class ScheduleServiceTest extends Specification {

    def scheduleRepository = Mock(ScheduleRepository)
    def showRepository = Mock(ShowRepository)
    def scheduleService = new ScheduleService(scheduleRepository, showRepository)

    def "판매자는 공연의 시간을 등록할 수 있다."() {
        given:
        def show = new Show(
                "넘블",
                new Location("넘블로 넘블길 23"),
                new Price(100_000),
                new Capacity(20),
                "좋아요"
        )
        ReflectionTestUtils.setField(show, "id", 1L)

        def requestTime1 = LocalDateTime.now().plusMonths(1L)
        def requestTime2 = LocalDateTime.now().plusMonths(1L).plusHours(2L)
        def request1 = new ScheduleEnrollRequest(requestTime1, requestTime1.plusHours(1L))
        def request2 = new ScheduleEnrollRequest(requestTime2, requestTime2.plusHours(1L))
        def requests = new ScheduleEnrollRequests(List.of(request1, request2), show.id())

        def schedules = requests.toEntities()
        def scheduleId = 1L
        schedules.forEach(schedule -> ReflectionTestUtils.setField(schedule, "id", scheduleId++))

        def seller = new Seller(
                new Email("example@google.com"),
                new Password("qwer1234!"),
                new Phone("01012341234"),
                "110-11-11111",
                "홍길동"
        )
        ReflectionTestUtils.setField(seller, "id", 1L)
        show.registerSeller(seller)

        def expected = schedules.stream()
                .map(Schedule::id)
                .toList()


        when:
        def actual = scheduleService.enroll(requests, seller.id())

        then:
        showRepository.findById(_) >> Optional.of(show)
        scheduleRepository.isOverlapped(_, _) >> true
        scheduleRepository.saveAll(_) >> schedules
        actual == expected
    }

    def "소유하지 않은 공연의 스케쥴은 등록할 수 없다."() {
        given:
        def show = new Show(
                "넘블",
                new Location("넘블로 넘블길 23"),
                new Price(100_000),
                new Capacity(20),
                "좋아요"
        )
        ReflectionTestUtils.setField(show, "id", 1L)

        def requestTime = LocalDateTime.now().plusMonths(1L)
        def duplicatedTime = LocalDateTime.now().plusMonths(1L).plusMinutes(30L)
        def request = new ScheduleEnrollRequest(requestTime, requestTime.plusHours(1L))
        def duplicatedRequest = new ScheduleEnrollRequest(duplicatedTime, duplicatedTime.plusHours(1L))
        def requests = new ScheduleEnrollRequests(List.of(request, duplicatedRequest), show.id())

        def schedules = requests.toEntities()
        def scheduleId = 1L
        schedules.forEach(schedule -> ReflectionTestUtils.setField(schedule, "id", scheduleId++))

        def seller = new Seller(
                new Email("example@google.com"),
                new Password("qwer1234!"),
                new Phone("01012341234"),
                "110-11-11111",
                "홍길동"
        )
        ReflectionTestUtils.setField(seller, "id", 1L)
        def otherSeller = new Seller(
                new Email("example2@google.com"),
                new Password("qwer1234!"),
                new Phone("01056751234"),
                "110-11-22222",
                "넘블"
        )
        ReflectionTestUtils.setField(otherSeller, "id", 2L)
        show.registerSeller(otherSeller)

        when:
        scheduleService.enroll(requests, seller.id())

        then:
        showRepository.findById(_) >> Optional.of(show)

        thrown(IllegalArgumentException)
    }

    def "판매자는 시간이 겹치게 스케쥴을 등록할 수 없다."() {
        given:
        def show = new Show(
                "넘블",
                new Location("넘블로 넘블길 23"),
                new Price(100_000),
                new Capacity(20),
                "좋아요"
        )
        ReflectionTestUtils.setField(show, "id", 1L)

        def requestTime = LocalDateTime.now().plusMonths(1L)
        def duplicatedTime = LocalDateTime.now().plusMonths(1L).plusMinutes(30L)
        def request = new ScheduleEnrollRequest(requestTime, requestTime.plusHours(1L))
        def duplicatedRequest = new ScheduleEnrollRequest(duplicatedTime, duplicatedTime.plusHours(1L))
        def requests = new ScheduleEnrollRequests(List.of(request, duplicatedRequest), show.id())

        def schedules = requests.toEntities()
        def scheduleId = 1L
        schedules.forEach(schedule -> ReflectionTestUtils.setField(schedule, "id", scheduleId++))

        def seller = new Seller(
                new Email("example@google.com"),
                new Password("qwer1234!"),
                new Phone("01012341234"),
                "110-11-11111",
                "홍길동"
        )
        ReflectionTestUtils.setField(seller, "id", 1L)
        show.registerSeller(seller)

        when:
        scheduleService.enroll(requests, seller.id())

        then:
        showRepository.findById(_) >> Optional.of(show)
        scheduleRepository.isOverlapped(_, _) >> true

        thrown(IllegalArgumentException)
    }

    def "판매자는 이미 저장된 스케쥴과 겹치게 등록할 수 없다."() {
        given:
        def show = new Show(
                "넘블",
                new Location("넘블로 넘블길 23"),
                new Price(100_000),
                new Capacity(20),
                "좋아요"
        )
        ReflectionTestUtils.setField(show, "id", 1L)

        def requestTime = LocalDateTime.now().plusMonths(1L)
        def duplicatedTime = LocalDateTime.now().plusMonths(1L).plusMinutes(30L)
        def request = new ScheduleEnrollRequest(requestTime, requestTime.plusHours(1L))
        def duplicatedRequest = new ScheduleEnrollRequest(duplicatedTime, duplicatedTime.plusHours(1L))
        def requests = new ScheduleEnrollRequests(List.of(request, duplicatedRequest), show.id())

        def schedules = requests.toEntities()
        def scheduleId = 1L
        schedules.forEach(schedule -> ReflectionTestUtils.setField(schedule, "id", scheduleId++))

        def seller = new Seller(
                new Email("example@google.com"),
                new Password("qwer1234!"),
                new Phone("01012341234"),
                "110-11-11111",
                "홍길동"
        )
        ReflectionTestUtils.setField(seller, "id", 1L)
        show.registerSeller(seller)

        when:
        scheduleService.enroll(requests, seller.id())

        then:
        showRepository.findById(_) >> Optional.of(show)
        scheduleRepository.isOverlapped(_, _) >> false

        thrown(IllegalArgumentException)
    }
}
