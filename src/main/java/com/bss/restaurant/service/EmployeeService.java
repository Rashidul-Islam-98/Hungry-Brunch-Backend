package com.bss.restaurant.service;

import com.bss.restaurant.dto.request.EmployeeRequest;
import com.bss.restaurant.dto.response.EmployeeResponse;
import com.bss.restaurant.dto.response.EmployeeShortResponse;
import com.bss.restaurant.dto.response.PaginationResponse;
import com.bss.restaurant.entity.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {
    PaginationResponse<EmployeeResponse> getEmployees(String query,int pageNumber, int pageSize, String sort);
    List<EmployeeShortResponse> getEmployeesName();
    Optional<EmployeeResponse> getEmployee(UUID employeeId);
    void saveEmployee(EmployeeRequest employee);
    void updateEmployee(UUID employeeId, String designation);
    void deleteEmployee(UUID employeeId) throws IOException;
}
