package com.bss.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class EmployeeTableResponse {
    private long id;
    private EmployeeShortResponse employee;
    private TableShortResponse table;
}
