package com.bss.restaurant.dao;

import com.bss.restaurant.entity.EmployeeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeTableRepository extends JpaRepository<EmployeeTable, Long> {
    List<EmployeeTable> findAllByTableId(long tableId);
    EmployeeTable findByTableIdAndEmployeeId(long tableId, UUID employeeId);
}
