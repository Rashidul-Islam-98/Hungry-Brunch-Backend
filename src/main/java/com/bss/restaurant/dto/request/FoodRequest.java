package com.bss.restaurant.dto.request;

import lombok.*;

@Getter
public class FoodRequest {
    private String name;
    private String description;
    private Integer price;
    private Integer discountType;
    private Integer discount;
    private Integer discountPrice;
    private String image;
    private String base64;
}
