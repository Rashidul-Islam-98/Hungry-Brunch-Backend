package com.bss.restaurant.projection;

import org.springframework.beans.factory.annotation.Value;

public interface TableNumberAndIdProjection {
    long getId();

    @Value("#{target.table_number}")
    String getTableNumber();
}
