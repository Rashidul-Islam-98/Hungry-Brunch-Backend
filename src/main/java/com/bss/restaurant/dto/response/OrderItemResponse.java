package com.bss.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrderItemResponse {
    private long id;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private FoodResponse food;
}
