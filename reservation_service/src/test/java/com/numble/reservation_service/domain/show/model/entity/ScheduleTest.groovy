package com.numble.reservation_service.domain.show.model.entity


import spock.lang.Specification

import java.time.LocalDateTime

class ScheduleTest extends Specification {

    def "시작 시간에는 null 값이 들어갈 수 없다."() {
        when:
        new Schedule(
                start,
                end
        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Schedule.SHOW_START_TIME_NULL_MESSAGE

        where:
        start || end
        null  || LocalDateTime.now().plusMonths(1L)
    }

    def "종료 시간에는 null 값이 들어갈 수 없다."() {
        when:
        new Schedule(
                start,
                end
        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Schedule.SHOW_END_TIME_NULL_MESSAGE

        where:
        start                              || end
        LocalDateTime.now().plusMonths(1L) || null
    }

    def "시작 시간이 종료시간 이후일 수 없다."() {
        when:
        new Schedule(
                start,
                end
        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == Schedule.START_LATER_THAN_END_MESSAGE

        where:
        start                              || end
        LocalDateTime.now().plusMonths(1L) || LocalDateTime.now().plusMonths(1L).minusHours(1L)
    }
}
