package com.ufuksak.spring.jpa.h2.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private RoomType roomType;
    @Column
    private Integer number;
    public Room(RoomType roomType, Integer number) {
        this.roomType = roomType;
        this.number = number;
    }

    public Room() {
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType=" + roomType +
                ", number=" + number +
                '}';
    }
}
