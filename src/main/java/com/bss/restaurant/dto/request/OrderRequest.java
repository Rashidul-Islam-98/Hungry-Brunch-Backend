package com.bss.restaurant.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {
    private Long tableId;
    private String orderNumber;
    private Integer amount;
    private List<OrderItemRequest> items;
}
