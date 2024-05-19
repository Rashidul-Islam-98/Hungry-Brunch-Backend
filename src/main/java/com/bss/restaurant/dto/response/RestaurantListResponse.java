package com.bss.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@ToString
public class RestaurantListResponse <T>{
    private List<T> data;
}
