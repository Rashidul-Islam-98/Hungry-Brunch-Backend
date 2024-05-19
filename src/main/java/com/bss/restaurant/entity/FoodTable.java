package com.bss.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tables")
public class FoodTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "table_number")
    private String tableNumber;

    @Column(name= "number_of_seats")
    private Integer numberOfSeats;

    @Column(name = "is_occupied")
    private boolean isOccupied;

    @Column(name = "image")
    private String image;

    @Column(name = "image_url")
    private String imageUrl;
}
