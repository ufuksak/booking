package com.ufuksak.spring.jpa.h2.repository;

import com.ufuksak.spring.jpa.h2.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
  Guest findGuestByEmail(String email);
}
