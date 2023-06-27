package com.numble.reservation_service.domain.model

import spock.lang.Specification

class PasswordTest extends Specification {

    def "비밀번호에는 null 값이 들어갈 수 없다."() {
        when:
        new Password(null)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Password.PASSWORD_NULL_MESSAGE
    }

    def "형식이 안맞는 비밀번호는 생성될 수 없다."() {
        when:
        new Password(value)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Password.PASSWORD_INVALID_FORM_MESSAGE

        where:
        value << ["qwer", "123", "qwer123", "qwer123("]
    }
}
