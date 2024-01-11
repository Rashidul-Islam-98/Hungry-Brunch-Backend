package com.bss.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderRequest {
    private Long tableId;
    private String orderNumber;
    private Integer amount;
    private List<OrderItemRequest> items;
}
