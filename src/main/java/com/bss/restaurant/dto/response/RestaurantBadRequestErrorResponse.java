package com.bss.restaurant.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RestaurantBadRequestErrorResponse {

    private List<String> errors;
}
