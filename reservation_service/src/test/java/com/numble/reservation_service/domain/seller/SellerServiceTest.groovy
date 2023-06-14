package com.numble.reservation_service.domain.seller


import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

class SellerServiceTest extends Specification {

    def mockSellerRepository = Mock(SellerRepository.class)
    def sellerService = new SellerService(mockSellerRepository)

    def "사업자는 회원가입을 할 수 있다."() {
        given:
        def request = new SellerEnrollRequest(email, password, licenseId, name, phone)
        def seller = request.toEntity()
        ReflectionTestUtils.setField(seller, "id", 1L)

        when:
        def enrolledId = sellerService.enroll(request)

        then:
        1 * mockSellerRepository.save(_) >> seller
        enrolledId == seller.id()

        where:
        email                || password    || licenseId      || name  || phone
        "example1@gmail.com" || "example1!" || "123-11-12345" || "홍길동" || "01020765571"
        "example2@gmail.com" || "example2!" || "129-10-12345" || "홍길동" || "01020005571"
        "example3@gmail.com" || "example3!" || "129-10-10000" || "홍길동" || "01020765500"
    }
}
