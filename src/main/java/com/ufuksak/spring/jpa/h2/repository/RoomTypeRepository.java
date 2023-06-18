package com.ufuksak.spring.jpa.h2.repository;

import com.ufuksak.spring.jpa.h2.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
  RoomType findRoomTypeByName(String name);
}
