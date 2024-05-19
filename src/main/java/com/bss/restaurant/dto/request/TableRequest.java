package com.bss.restaurant.dto.request;

import lombok.Getter;

@Getter
public class TableRequest {
    private String tableNumber;
    private Integer numberOfSeats;
    private String image;
    private String base64;
}
