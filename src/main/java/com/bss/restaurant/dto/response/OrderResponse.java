package com.bss.restaurant.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@ToString
public class OrderResponse {
    private UUID id;
    private String orderNumber;
    private Integer amount;
    private String orderStatus;
    private LocalDateTime orderTime;
    private TableResponse table;
    private List<OrderItemResponse> items;
}
