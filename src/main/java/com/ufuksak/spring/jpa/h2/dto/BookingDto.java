package com.ufuksak.spring.jpa.h2.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
public class BookingDto {
  private String uuid;
  private String guestEmail;
  private String arrival;
  private String departure;
  private String staffLogin;

  public Boolean validate(){
    if(Date.valueOf(this.arrival).toLocalDate().isAfter(Date.valueOf(this.departure).toLocalDate())
        || Date.valueOf(this.arrival).toLocalDate().isEqual(Date.valueOf(this.departure).toLocalDate())){
      return !Date.valueOf(this.arrival).toLocalDate().isBefore(LocalDate.now());
    }
    return true;
  }
}
