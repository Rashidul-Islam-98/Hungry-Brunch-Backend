package com.bss.restaurant.dao;

import com.bss.restaurant.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>{
    Optional<Employee> findByUserEmail(String email);

    Optional<Employee> findByUserId(UUID userId);

    Page<Employee> findByUserFirstNameContainingIgnoreCase(String firstName, Pageable pageable);
}
