package com.bss.restaurant.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RestaurantErrorResponse {

    private String code;
    private String message;
}
