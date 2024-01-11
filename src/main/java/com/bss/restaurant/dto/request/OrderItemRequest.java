package com.bss.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemRequest {
    private Long foodId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
}
