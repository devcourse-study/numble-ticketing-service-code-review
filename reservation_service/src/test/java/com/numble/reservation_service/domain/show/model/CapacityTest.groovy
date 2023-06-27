package com.numble.reservation_service.domain.show.model


import spock.lang.Specification

class CapacityTest extends Specification {

    def "공연의 정원은 0 이하일 수 없다."() {
        when:
        new Capacity(-1)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Capacity.CAPACITY_UNDER_ZERO_MESSAGE
    }
}
