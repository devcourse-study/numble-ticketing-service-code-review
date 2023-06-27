package com.numble.reservation_service.domain.show.model


import spock.lang.Specification

class PriceTest extends Specification {

    def "공연의 가격은 0 이하일 수 없다."() {
        when:
        new Price(-1)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Price.PRICE_UNDER_ZERO_MESSAGE
    }
}
