package com.bss.restaurant.dto.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class EmployeeTableRequest {
    private UUID employeeId;
    private long tableId;
}
