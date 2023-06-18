package com.ufuksak.spring.jpa.h2.repository;

import com.ufuksak.spring.jpa.h2.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
  Room findOneByNumber(int number);
  @Query(value = "SELECT * FROM ROOM WHERE ID NOT IN (SELECT DISTINCT ROOM_ID FROM BOOKING WHERE (ARRIVAL between :arrival and :departure ) OR (DEPARTURE between :arrival and :departure ) OR (ARRIVAL < :arrival and DEPARTURE > :departure) OR (ARRIVAL > :arrival and DEPARTURE < :departure));", nativeQuery = true)
  List<Room> findAvailableRoomsByDateRange(Date arrival, Date departure);

  @Query(value = "SELECT * FROM ROOM WHERE ID NOT IN (SELECT DISTINCT ROOM_ID FROM BOOKING WHERE BOOKING_TYPE = :bookingType AND ((ARRIVAL between :arrival and :departure ) OR (DEPARTURE between :arrival and :departure ) OR (ARRIVAL < :arrival and DEPARTURE > :departure) OR (ARRIVAL > :arrival and DEPARTURE < :departure)));", nativeQuery = true)
  List<Room> findAvailableRoomsByDateRangeAndBookingType(Date arrival, Date departure, String bookingType);
}
