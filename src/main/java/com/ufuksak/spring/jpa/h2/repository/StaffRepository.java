package com.ufuksak.spring.jpa.h2.repository;

import com.ufuksak.spring.jpa.h2.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
  Staff findStaffByLogin(String login);
}
