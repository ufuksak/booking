package com.ufuksak.spring.jpa.h2;

import com.ufuksak.spring.jpa.h2.controller.BookingController;
import com.ufuksak.spring.jpa.h2.dto.BookingDto;
import com.ufuksak.spring.jpa.h2.dto.GuestDto;
import com.ufuksak.spring.jpa.h2.dto.RoomDto;
import com.ufuksak.spring.jpa.h2.dto.RoomTypeDto;
import com.ufuksak.spring.jpa.h2.dto.StaffDto;
import com.ufuksak.spring.jpa.h2.model.AccessLevel;
import com.ufuksak.spring.jpa.h2.model.Booking;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = SpringBootJpaH2Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootJpaH2CommitResponseTests {
  @Autowired
  private BookingController controller;

  @BeforeAll
  public void initialize() {
    initComponents();
  }

  @BeforeEach
  public void setUp() {

  }

  @AfterEach
  public void tearDown() {
    controller.deleteAllBookings();
  }

  private void initComponents() {
    RoomTypeDto roomType = RoomTypeDto.builder().name("test").description("test").dailyPrice(new BigDecimal(1000)).build();
    controller.saveRoomType(roomType);
    RoomDto roomDto = RoomDto.builder().roomType("test").roomNumber(101).build();
    controller.saveRoom(roomDto);
    StaffDto staffDto = StaffDto.builder().name("ufuksakar").accessLevel(AccessLevel.OWNER).login("abc").password("abc").build();
    controller.saveStaff(staffDto);
    GuestDto guestDto = GuestDto.builder().name("hostui").document("sample_pdf").birthDate(LocalDate.of(1985,6,6))
        .email("ufuksakar@gmail.com").phoneNumber( "00905557035175").build();
    controller.saveGuest(guestDto);
  }

  @Test
  void givenBookingVerifyBooked() {
    // Given
    BookingDto bookingDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-14").departure("2023-05-15").staffLogin("abc").build();

    // When
    ResponseEntity<List<Booking>> bookingList = controller.saveBooking(bookingDto);

    // Then
    assertEquals(1, Objects.requireNonNull(bookingList.getBody()).size());
  }

  @Test
  void givenBookingAndBlockOnTheOverlappingDaysVerifyBlockIsForbidden() {
    // Given
    BookingDto bookingDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-14").departure("2023-05-16").staffLogin("abc").build();
    BookingDto blockDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-15").departure("2023-05-17").staffLogin("abc").build();
    controller.saveBooking(bookingDto);

    // When
    ResponseEntity<List<Booking>> blockList = controller.saveBlock(blockDto);

    // Then
    assertEquals(403, blockList.getStatusCodeValue());
  }

  @Test
  void givenBlockAndBookingOnTheOverlappingDaysVerifyBookingIsForbidden() {
    // Given
    BookingDto bookingDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-14").departure("2023-05-16").staffLogin("abc").build();
    BookingDto blockDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-15").departure("2023-05-17").staffLogin("abc").build();
    controller.saveBlock(blockDto);

    // When
    ResponseEntity<List<Booking>> bookingList = controller.saveBooking(bookingDto);

    // Then
    assertEquals(403, bookingList.getStatusCodeValue());
  }

  @Test
  void givenBookingAndBlockOnTheDifferentDaysVerifyBookingAndBlockSaved() {
    // Given
    BookingDto bookingDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-14").departure("2023-05-15").staffLogin("abc").build();
    BookingDto blockDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-15").departure("2023-05-16").staffLogin("abc").build();
    controller.saveBooking(bookingDto);

    // When
    ResponseEntity<List<Booking>> bookingList = controller.saveBlock(blockDto);

    // Then
    assertEquals(2, Objects.requireNonNull(bookingList.getBody()).size());
  }

  @Test
  void givenBlocksOnTheOverlappingDaysVerifyBlocksSaved() {
    // Given
    BookingDto bookingDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-14").departure("2023-05-15").staffLogin("abc").build();
    BookingDto blockDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-14").departure("2023-05-16").staffLogin("abc").build();
    controller.saveBlock(bookingDto);

    // When
    ResponseEntity<List<Booking>> blockList = controller.saveBlock(blockDto);

    // Then
    assertEquals(2, Objects.requireNonNull(blockList.getBody()).size());
  }

  @Test
  void givenBookingsOnTheOverlappingDaysVerifyBookingIsForbidden() {
    // Given
    BookingDto bookingDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-14").departure("2023-05-16").staffLogin("abc").build();
    BookingDto bookingDto2 = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-15").departure("2023-05-17").staffLogin("abc").build();
    controller.saveBooking(bookingDto);

    // When
    ResponseEntity<List<Booking>> bookingList = controller.saveBooking(bookingDto2);

    // Then
    assertEquals(403, bookingList.getStatusCodeValue());
  }

  @Test
  void givenBookingsOnTheDifferentDaysVerifyBookingsSaved() {
    // Given
    BookingDto bookingDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-14").departure("2023-05-16").staffLogin("abc").build();
    BookingDto bookingDto2 = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-16").departure("2023-05-17").staffLogin("abc").build();
    controller.saveBooking(bookingDto);

    // When
    ResponseEntity<List<Booking>> bookingList = controller.saveBooking(bookingDto2);

    // Then
    assertEquals(2, Objects.requireNonNull(bookingList.getBody()).size());
  }

  @Test
  void givenBookingsOnTheDifferentDaysUpdateOverlappingDaysVerifyBookingUpdateForbidden() {
    // Given
    BookingDto bookingDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-14").departure("2023-05-17").staffLogin("abc").build();
    BookingDto bookingDto2 = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-18").departure("2023-05-20").staffLogin("abc").build();
    ResponseEntity<List<Booking>> bookingList = controller.saveBooking(bookingDto);
    controller.saveBooking(bookingDto2);

    BookingDto bookingDto3 = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-17").departure("2023-05-19")
        .uuid(Objects.requireNonNull(bookingList.getBody()).get(0).getUuid()).staffLogin("abc").build();

    // When
    ResponseEntity<List<Booking>> bookingUpdateList = controller.updateBooking(bookingDto3);

    // Then
    assertEquals(403, bookingUpdateList.getStatusCodeValue());
  }

  @Test
  void givenBookingsOnTheDifferentDaysUpdateNotOverlappingDaysVerifyBookingUpdateSuccess() {
    // Given
    BookingDto bookingDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-14").departure("2023-05-17").staffLogin("abc").build();
    BookingDto bookingDto2 = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-18").departure("2023-05-20").staffLogin("abc").build();
    ResponseEntity<List<Booking>> bookingList = controller.saveBooking(bookingDto);
    controller.saveBooking(bookingDto2);

    BookingDto bookingDto3 = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-21").departure("2023-05-23")
        .uuid(Objects.requireNonNull(bookingList.getBody()).get(0).getUuid()).staffLogin("abc").build();

    // When
    ResponseEntity<List<Booking>> bookingUpdateList = controller.updateBooking(bookingDto3);

    // Then
    assertEquals(2, Objects.requireNonNull(bookingUpdateList.getBody()).size());
  }

  @Test
  void givenBookingsVerifyDeletionIsSuccess() {
    // Given
    BookingDto bookingDto = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-14").departure("2023-05-16").staffLogin("abc").build();
    BookingDto bookingDto2 = BookingDto.builder().guestEmail("ufuksakar@gmail.com")
        .arrival("2023-05-16").departure("2023-05-17").staffLogin("abc").build();
    ResponseEntity<List<Booking>> bookingList = controller.saveBooking(bookingDto);
    controller.saveBooking(bookingDto2);

    // When
    ResponseEntity<List<Booking>> bookingResultList =
        controller.deleteBooking(Objects.requireNonNull(bookingList.getBody()).get(0).getUuid());

    // Then
    assertEquals(1, Objects.requireNonNull(bookingResultList.getBody()).size());
    assertEquals(204, bookingResultList.getStatusCodeValue());
  }
}
