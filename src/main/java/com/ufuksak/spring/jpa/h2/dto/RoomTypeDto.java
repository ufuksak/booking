package com.ufuksak.spring.jpa.h2.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RoomTypeDto {
  private String name;
  private String description;
  private BigDecimal dailyPrice;
}
