package com.bss.restaurant.dao;

import com.bss.restaurant.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Food findByName(String name);

    @Query(value = "SELECT f.id, f.name FROM foods f", nativeQuery = true)
    List<Object[]> findAllNamesAndIds();

    Page<Food> findByNameContainingIgnoreCase(String name, PageRequest pageRequest);
}
