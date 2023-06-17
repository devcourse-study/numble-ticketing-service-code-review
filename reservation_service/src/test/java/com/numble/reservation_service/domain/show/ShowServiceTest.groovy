package com.numble.reservation_service.domain.show

import com.numble.reservation_service.domain.model.Email
import com.numble.reservation_service.domain.model.Password
import com.numble.reservation_service.domain.model.Phone
import com.numble.reservation_service.domain.seller.Seller
import com.numble.reservation_service.domain.seller.SellerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

class ShowServiceTest extends Specification {

    def sellerRepository = Mock(SellerRepository)
    def showRepository = Mock(ShowRepository)
    def showService = new ShowService(showRepository, sellerRepository)

    def "판매자는 공연을 등록할 수 있다."() {
        given:
        def request = new ShowEnrollRequest("넘블 공연", "넘블로 넘블길 12", 100_000, 20, "좋아요")
        def show = request.toEntity()
        def seller = new Seller(new Email("example@gmail.com"), new Password("qwer1234!"), new Phone("01012341234"), "110-11-12345", "홍길동")
        ReflectionTestUtils.setField(show, "id", 1L)
        ReflectionTestUtils.setField(seller, "id", 1L)

        when:
        def enrolledShowId = showService.enroll(request, seller.id())

        then:
        sellerRepository.findById(_) >> Optional.of(seller)
        showRepository.save(_) >> show
        enrolledShowId == show.id()
    }

    def "회원이 아닌 판매자는 공연을 등록할 수 없다."() {
        given:
        def request = new ShowEnrollRequest("넘블 공연", "넘블로 넘블길 12", 100_000, 20, "좋아요")
        def show = request.toEntity()
        def seller = new Seller(new Email("example@gmail.com"), new Password("qwer1234!"), new Phone("01012341234"), "110-11-12345", "홍길동")
        ReflectionTestUtils.setField(show, "id", 1L)
        ReflectionTestUtils.setField(seller, "id", 1L)

        when:
        showService.enroll(request, seller.id())

        then:
        sellerRepository.findById(_) >> Optional.empty()
        showRepository.save(_) >> show
        def e = thrown(EntityNotFoundException)
        e.message == ShowService.SELLER_NOT_MEMBER_MESSAGE
    }
}
