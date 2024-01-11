package com.bss.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderUpdateStatusRequest {
    private Integer status;
}
