package com.numble.reservation_service.domain.schedule;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	@Query(value = """
		SELECT IF(EXISTS(
			select *
			FROM schedule s
			WHERE s.end >= STR_TO_DATE(:start, '%Y-%m-%d %h:%i:%s') and s.start <= STR_TO_DATE(:end, '%Y-%m-%d %h:%i:%s') and s.show_id = :sellerId
			) > 1, 'true', 'false')
		""", nativeQuery = true)
	Boolean isOverlapped(
		@Param("start") LocalDateTime start,
		@Param("end") LocalDateTime end,
		@Param("sellerId") Long sellerId);

}
