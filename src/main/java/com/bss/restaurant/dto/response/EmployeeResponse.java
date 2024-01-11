package com.bss.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Builder
@ToString
public class EmployeeResponse {
    private UUID id;
    private LocalDate joinDate;
    private String designation;
    private Integer amountSold;
    private UserResponse user;
}
