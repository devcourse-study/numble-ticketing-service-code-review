package com.numble.reservation_service.domain.model

import spock.lang.Specification

class EmailTest extends Specification {

    def "이메일에는 null 값이 들어갈 수 없다."() {
        when:
        new Email(null)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Email.EMAIL_NULL_MESSAGE
    }

    def "형식이 안맞는 이메일는 생성될 수 없다."() {
        when:
        new Email(value)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Email.EMAIL_INVALID_FORM_MESSAGE

        where:
        value << ["example@google", "examplegoogle.com"]
    }
}
