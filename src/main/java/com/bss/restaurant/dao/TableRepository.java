package com.bss.restaurant.dao;

import com.bss.restaurant.entity.FoodTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TableRepository extends JpaRepository<FoodTable, Long> {
    FoodTable findByTableNumber(String tableNumber);
    @Query(value = "SELECT t.id, t.table_number FROM tables t", nativeQuery = true)
    List<Object[]> findAllTableNumberAndIds();
}
