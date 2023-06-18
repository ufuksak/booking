package com.ufuksak.spring.jpa.h2.dto;

import com.ufuksak.spring.jpa.h2.model.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StaffDto {
  private String name;
  private AccessLevel accessLevel;
  private String login;
  private String password;
}
