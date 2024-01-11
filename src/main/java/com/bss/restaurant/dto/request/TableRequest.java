package com.bss.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TableRequest {
    private String tableNumber;
    private Integer numberOfSeats;
    private String image;
    private String base64;
}
