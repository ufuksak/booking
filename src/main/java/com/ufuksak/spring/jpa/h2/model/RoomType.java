package com.ufuksak.spring.jpa.h2.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table
@Data
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private BigDecimal dailyPrice;

    public RoomType(String name, String description, BigDecimal dailyPrice) {
        this.name = name;
        this.description = description;
        this.dailyPrice = dailyPrice;
    }

    public RoomType() {
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dailyPrice=" + dailyPrice +
                '}';
    }
}
