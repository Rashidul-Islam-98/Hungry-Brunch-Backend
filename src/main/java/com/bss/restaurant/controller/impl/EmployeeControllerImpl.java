package com.bss.restaurant.controller.impl;

import com.bss.restaurant.controller.EmployeeController;
import com.bss.restaurant.dto.request.EmployeeRequest;
import com.bss.restaurant.dto.response.*;
import com.bss.restaurant.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee/")
public class EmployeeControllerImpl implements EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ResponseEntity<PaginationResponse<EmployeeResponse>> getEmployees(String search, Integer pageNumber, Integer pageSize, String sort) {
        return ResponseEntity.ok(employeeService.getEmployees(search, pageNumber, pageSize, sort));
    }

    @Override
    public ResponseEntity<RestaurantListResponse<EmployeeShortResponse>> getEmployeeNames() {
        return ResponseEntity.ok(employeeService.getEmployeesName());
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployee(UUID id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantBaseResponse> createEmployee(EmployeeRequest employee) {
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>(RestaurantBaseResponse.builder().message("Employee Created Successfully.").build(), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> updateEmployee(UUID id, EmployeeRequest employee) {
        employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(RestaurantBaseResponse.builder().message("Employee Updated Successfully.").build());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantBaseResponse> deleteEmployee(UUID id) throws IOException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(RestaurantBaseResponse.builder().message("Employee Deleted SuccessFully").build());
    }
}
