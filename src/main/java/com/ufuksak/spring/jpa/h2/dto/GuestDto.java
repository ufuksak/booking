package com.ufuksak.spring.jpa.h2.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GuestDto {
  private String name;
  private String document;
  private LocalDate birthDate;
  private String email;
  private String phoneNumber;
}
