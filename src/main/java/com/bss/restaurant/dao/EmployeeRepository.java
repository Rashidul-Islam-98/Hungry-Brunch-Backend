package com.bss.restaurant.dao;

import com.bss.restaurant.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>{
    Employee findByUserEmail(String email);

    Page<Employee> findByUserFirstNameContainingIgnoreCase(String firstName, Pageable pageable);
}
