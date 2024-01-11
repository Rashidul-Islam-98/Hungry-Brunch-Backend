package com.bss.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class FoodResponse {
    private long id;
    private String name;
    private String description;
    private Integer price;
    private String discountType;
    private Integer discount;
    private Integer discountPrice;
    private String image;
}
