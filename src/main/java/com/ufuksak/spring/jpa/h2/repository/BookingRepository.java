package com.ufuksak.spring.jpa.h2.repository;

import com.ufuksak.spring.jpa.h2.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

  Optional<Booking> findByUuid(String uuid);
}
