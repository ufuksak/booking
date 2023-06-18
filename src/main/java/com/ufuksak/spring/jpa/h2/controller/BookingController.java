package com.ufuksak.spring.jpa.h2.controller;

import com.ufuksak.spring.jpa.h2.dto.BookingDto;
import com.ufuksak.spring.jpa.h2.dto.GuestDto;
import com.ufuksak.spring.jpa.h2.dto.RoomDto;
import com.ufuksak.spring.jpa.h2.dto.RoomTypeDto;
import com.ufuksak.spring.jpa.h2.dto.StaffDto;
import com.ufuksak.spring.jpa.h2.model.Booking;
import com.ufuksak.spring.jpa.h2.model.BookingType;
import com.ufuksak.spring.jpa.h2.model.Guest;
import com.ufuksak.spring.jpa.h2.model.Room;
import com.ufuksak.spring.jpa.h2.model.RoomType;
import com.ufuksak.spring.jpa.h2.model.Staff;
import com.ufuksak.spring.jpa.h2.repository.BookingRepository;
import com.ufuksak.spring.jpa.h2.repository.GuestRepository;
import com.ufuksak.spring.jpa.h2.repository.RoomRepository;
import com.ufuksak.spring.jpa.h2.repository.RoomTypeRepository;
import com.ufuksak.spring.jpa.h2.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

  @Autowired
  BookingRepository bookingRepository;

  @Autowired
  RoomRepository roomRepository;
  @Autowired
  GuestRepository guestRepository;

  @Autowired
  StaffRepository staffRepository;

  @Autowired
  RoomTypeRepository roomTypeRepository;

  private Booking getBooking(BookingDto bookingDto, int roomNumber, BookingType bookingType) {
    Room room = roomRepository.findOneByNumber(roomNumber);
    Guest guest = guestRepository.findGuestByEmail(bookingDto.getGuestEmail());
    Staff staff = staffRepository.findStaffByLogin(bookingDto.getStaffLogin());
    return new Booking(room, guest, Date.valueOf(bookingDto.getArrival()),
        Date.valueOf(bookingDto.getDeparture()), staff, bookingType, UUID.randomUUID().toString());
  }

  @PostMapping("")
  public ResponseEntity<List<Booking>> saveBooking(@RequestBody BookingDto bookingDto) {
    if (!bookingDto.validate()) {
      return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.FORBIDDEN);
    }
    List<Room> rooms = roomRepository
        .findAvailableRoomsByDateRange(Date.valueOf(bookingDto.getDeparture()), Date.valueOf(bookingDto.getArrival()));
    if (!rooms.isEmpty()) {
      Booking booking = getBooking(bookingDto, rooms.get(0).getNumber(), BookingType.RESERVATION);
      bookingRepository.save(booking);
    } else {
      return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.CREATED);
  }

  @PostMapping("/block")
  public ResponseEntity<List<Booking>> saveBlock(@RequestBody BookingDto bookingDto) {
    if (!bookingDto.validate()) {
      return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.FORBIDDEN);
    }
    List<Room> rooms = roomRepository
        .findAvailableRoomsByDateRangeAndBookingType(Date.valueOf(bookingDto.getDeparture()),
            Date.valueOf(bookingDto.getArrival()), BookingType.RESERVATION.name());
    if (!rooms.isEmpty()) {
      Booking booking = getBooking(bookingDto, rooms.get(0).getNumber(), BookingType.BLOCK);
      bookingRepository.save(booking);
    } else {
      return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.CREATED);
  }

  @PostMapping("/room")
  public ResponseEntity<List<Booking>> saveRoom(@RequestBody RoomDto roomDto) {
    RoomType roomType = roomTypeRepository.findRoomTypeByName(roomDto.getRoomType());
    Room room = new Room(roomType, roomDto.getRoomNumber());
    roomRepository.save(room);
    return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.CREATED);
  }

  @PostMapping("/roomType")
  public ResponseEntity<List<Booking>> saveRoomType(@RequestBody RoomTypeDto roomTypeDto) {
    RoomType roomType = new RoomType(roomTypeDto.getName(), roomTypeDto.getDescription(), roomTypeDto.getDailyPrice());
    roomTypeRepository.save(roomType);
    return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.CREATED);
  }

  @PostMapping("/guest")
  public ResponseEntity<List<Booking>> saveGuest(@RequestBody GuestDto guestDto) {
    Guest guest = new Guest(guestDto.getName(), guestDto.getDocument(), guestDto.getBirthDate(), guestDto.getEmail(), guestDto.getPhoneNumber());
    guestRepository.save(guest);
    return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.CREATED);
  }

  @PostMapping("/staff")
  public ResponseEntity<List<Booking>> saveStaff(@RequestBody StaffDto staffDto) {
    Staff staff = new Staff(staffDto.getName(), staffDto.getAccessLevel(), staffDto.getLogin(), staffDto.getPassword());
    staffRepository.save(staff);
    return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.CREATED);
  }

  @GetMapping("")
  public ResponseEntity<List<Booking>> getBookings() {
    try {
      List<Booking> allBookings = bookingRepository.findAll();
      return new ResponseEntity<>(allBookings, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Booking> getBookingById(@PathVariable("id") String uuid) {
    Optional<Booking> booking = bookingRepository.findByUuid(uuid);

    return booking.map(githubRepoCommit ->
        new ResponseEntity<>(githubRepoCommit, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("")
  public ResponseEntity<List<Booking>> updateBooking(@RequestBody BookingDto bookingDto) {
    if (!bookingDto.validate()) {
      return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.FORBIDDEN);
    }
    Optional<Booking> booking = bookingRepository.findByUuid(bookingDto.getUuid());
    if (booking.isPresent()) {
      Booking existingBooking = booking.get();
      List<Room> rooms;
      if (existingBooking.getBookingType().equals(BookingType.RESERVATION)) {
        rooms = roomRepository
            .findAvailableRoomsByDateRange(Date.valueOf(bookingDto.getDeparture()), Date.valueOf(bookingDto.getArrival()));
      } else {
        rooms = roomRepository
            .findAvailableRoomsByDateRangeAndBookingType(Date.valueOf(bookingDto.getDeparture()),
                Date.valueOf(bookingDto.getArrival()), BookingType.RESERVATION.name());
      }
      if (!rooms.isEmpty()) {
        existingBooking.setArrival(Date.valueOf(bookingDto.getArrival()));
        existingBooking.setDeparture(Date.valueOf(bookingDto.getDeparture()));
        bookingRepository.save(existingBooking);
      } else {
        return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.FORBIDDEN);
      }
    }
    return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<List<Booking>> deleteBooking(@PathVariable("id") String uuid) {
    Optional<Booking> booking = bookingRepository.findByUuid(uuid);
    booking.ifPresent(existingBooking -> bookingRepository.delete(existingBooking));
    return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("")
  public ResponseEntity<List<Booking>> deleteAllBookings() {
    bookingRepository.deleteAll();
    return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.NO_CONTENT);
  }
}
