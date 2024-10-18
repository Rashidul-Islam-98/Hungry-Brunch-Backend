package com.bss.restaurant.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface OrderNumberAndIdProjection {
    UUID getId();

    @Value("#{target.order_number}")
    String getOrderNumber();
}
