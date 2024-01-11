package com.bss.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@Builder
@ToString
public class TableResponse {
    private long id;
    private String tableNumber;
    private Integer numberOfSeats;
    private boolean isOccupied;
    private String image;
    private List<EmployeeShortResponse> employees;
}
