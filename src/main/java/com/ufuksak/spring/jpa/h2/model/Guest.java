package com.ufuksak.spring.jpa.h2.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
@Data
public class Guest{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String document;
    @Column
    private LocalDate birthDate;
    @Column
    private String email;
    @Column
    private String phoneNumber;

    public Guest(String name, String document, LocalDate birthDate, String email, String phoneNumber) {
        this.name = name;
        this.document = document;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Guest() {
    }


    @Override
    public String toString() {
        return "Guest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", document='" + document + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
