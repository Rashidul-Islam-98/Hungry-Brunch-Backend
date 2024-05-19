package com.bss.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RestaurantBaseResponse {
    private String message;
}
