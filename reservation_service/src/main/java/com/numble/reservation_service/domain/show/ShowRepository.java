package com.numble.reservation_service.domain.show;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.reservation_service.domain.show.model.entity.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {
}
