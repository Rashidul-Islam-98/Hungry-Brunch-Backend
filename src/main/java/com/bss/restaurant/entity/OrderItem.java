package com.bss.restaurant.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    @Column(name = "unit_price")
    @NotNull
    private Integer unitPrice;

    @Column(name = "total_price")
    @NotNull
    private Integer totalPrice;

    @ManyToOne
    @JoinColumn(name= "food_id")
    private Food food;
}
