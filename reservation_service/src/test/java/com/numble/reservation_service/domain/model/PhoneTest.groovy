package com.numble.reservation_service.domain.model

import spock.lang.Specification

class PhoneTest extends Specification {

    def "핸드폰 번호에는 null 값이 들어갈 수 없다."() {
        when:
        new Phone(null)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Phone.PHONE_NULL_MESSAGE
    }

    def "형식이 안맞는 핸드폰 번호는 생성될 수 없다."() {
        when:
        new Phone(value)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Phone.PHONE_INVALID_FORM_MESSAGE

        where:
        value << ["0112341234", "01112341234", "010123k1234", "0101234123"]
    }
}
