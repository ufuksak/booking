package com.ufuksak.spring.jpa.h2.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Room room;
    @ManyToOne
    private Guest guest;
    @Column
    private Date arrival;
    @Column
    private Date departure;
    @Column
    private String uuid;
    @ManyToOne
    private Staff staff;

    @Column
    @Enumerated(EnumType.STRING)
    private BookingType bookingType;

    public Booking() {

    }

    public Booking(Room room, Guest guest, Date arrival, Date departure, Staff staff, BookingType bookingType, String uuid) {
        this.room = room;
        this.guest = guest;
        this.arrival = arrival;
        this.departure = departure;
        this.uuid = uuid;
        this.staff = staff;
        this.bookingType = bookingType;
    }
}
