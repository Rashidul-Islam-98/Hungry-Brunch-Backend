package com.bss.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;
@Getter
@Builder
@ToString
public class EmployeeShortResponseForTable {
    private long employeeTableId;
    private UUID id;
    private String name;
}
