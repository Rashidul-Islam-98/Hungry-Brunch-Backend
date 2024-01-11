package com.bss.restaurant.dao;

import com.bss.restaurant.entity.EmployeeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeTableRepository extends JpaRepository<EmployeeTable, Long> {
}
