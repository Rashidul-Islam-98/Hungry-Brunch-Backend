package com.bss.restaurant.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "discount_type")
    private Integer discountType;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "discount_price")
    private Integer discountPrice;

    @Column(name = "image")
    private String image;

    @Column(name = "image_url")
    private String imageUrl;
}
