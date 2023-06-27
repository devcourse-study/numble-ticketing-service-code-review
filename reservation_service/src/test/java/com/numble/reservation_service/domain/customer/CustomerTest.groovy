package com.numble.reservation_service.domain.customer

import com.numble.reservation_service.domain.model.Email
import com.numble.reservation_service.domain.model.Password
import com.numble.reservation_service.domain.model.Phone
import spock.lang.Specification

class CustomerTest extends Specification {

    def "nickname에는 null 값이 들어갈 수 없다."() {
        when:
        new Customer(
                email as Email,
                password as Password, phone as Phone,
                nickname,
                gender as GENDER as GENDER,
                age as int

        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Customer.NICKNAME_NULL_MESSAGE

        where:
        email || password || nickname || gender || age || phone
        new Email("example@google.com") || new Password("qwer1234!") || null || GENDER.WOMAN || 10 || new Phone("01012341234")
    }

    def "nickname은 30글자 이하여야 한다."() {
        when:
        new Customer(
                email as Email,
                password as Password, phone as Phone,
                nickname,
                gender as GENDER as GENDER,
                age as int

        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Customer.NICKNAME_TOO_LONG_MESSAGE

        where:
        email || password || nickname || gender || age || phone
        new Email("example@google.com") || new Password("qwer1234!") || "numblenumblenumblenumblenumblenumble" || GENDER.WOMAN || 10 || new Phone("01012341234")
    }

    def "age는 0이상이어야 한다."() {
        when:
        new Customer(
                email as Email,
                password as Password, phone as Phone,
                nickname,
                gender as GENDER as GENDER,
                age as int

        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Customer.AGE_TOO_LOW_MESSAGE

        where:
        email || password || nickname || gender || age || phone
        new Email("example@google.com") || new Password("qwer1234!") || "numble" || GENDER.WOMAN || -1 || new Phone("01012341234")
        new Email("example@google.com") || new Password("qwer1234!") || "numble" || GENDER.WOMAN || 0 || new Phone("01012341234")
    }
}
