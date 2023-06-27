package com.numble.reservation_service.domain.seller

import com.numble.reservation_service.domain.customer.Customer
import com.numble.reservation_service.domain.customer.GENDER
import com.numble.reservation_service.domain.model.Email
import com.numble.reservation_service.domain.model.Password
import com.numble.reservation_service.domain.model.Phone
import spock.lang.Specification

class SellerTest extends Specification {

    def "사업자번호에는 null 값이 들어갈 수 없다."() {
        when:
        new Seller(
                email as Email,
                password as Password,
                phone as Phone,
                licenseId,
                name as String
        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Seller.LICENSE_ID_NULL_MESSAGE

        where:
        email || password || phone || licenseId || name
        new Email("example@google.com") || new Password("qwer1234!") || new Phone("01012341234") || null || "홍길동"
    }

    def "형식이 맞지 않는 사업자번호는 생성될 수 없다."() {
        when:
        new Seller(
                email as Email,
                password as Password,
                phone as Phone,
                licenseId,
                name as String
        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Seller.LICENSE_ID_INVALID_FORM_MESSAGE

        where:
        email || password || phone || licenseId || name
        new Email("example@google.com") || new Password("qwer1234!") || new Phone("01012341234") || "1234-12-12345" || "홍길동"
        new Email("example@google.com") || new Password("qwer1234!") || new Phone("01012341234") || "12312-12345" || "홍길동"
        new Email("example@google.com") || new Password("qwer1234!") || new Phone("01012341234") || "123-12-1234-5" || "홍길동"
    }

    def "이름에는 null 값이 들어갈 수 없다."() {
        when:
        new Seller(
                email as Email,
                password as Password,
                phone as Phone,
                licenseId,
                name as String
        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Seller.NAME_NULL_MESSAGE

        where:
        email || password || phone || licenseId || name
        new Email("example@google.com") || new Password("qwer1234!") || new Phone("01012341234") || "110-11-11111" || null
    }

    def "형식이 맞지 않는 이름은 생성될 수 없다."() {
        when:
        new Seller(
                email as Email,
                password as Password,
                phone as Phone,
                licenseId,
                name as String
        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Seller.NAME_INVALID_FORM_MESSAGE

        where:
        email || password || phone || licenseId || name
        new Email("example@google.com") || new Password("qwer1234!") || new Phone("01012341234") || "110-11-11111" || "김"
        new Email("example@google.com") || new Password("qwer1234!") || new Phone("01012341234") || "110-11-11111" || "김김김김김김김김김김김"
        new Email("example@google.com") || new Password("qwer1234!") || new Phone("01012341234") || "110-11-11111" || "Numble"
        new Email("example@google.com") || new Password("qwer1234!") || new Phone("01012341234") || "110-11-11111" || "1234"
    }
}
