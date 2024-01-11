package com.bss.restaurant.service;

import com.bss.restaurant.dto.response.EmployeeTableResponse;
import com.bss.restaurant.dto.response.PaginationResponse;
import com.bss.restaurant.entity.EmployeeTable;

import java.util.List;
import java.util.Optional;

public interface EmployeeTableService {
    PaginationResponse<EmployeeTableResponse> getEmployeesInTable(int pageNumber, int pageSize);
    List<EmployeeTableResponse> getOnlyEmployeesInTable();
    Optional<EmployeeTableResponse> getEmployeeTable(long id);
    void saveEmployeeTable(EmployeeTable employeeTable);
    void updateEmployeeTable(long id, EmployeeTable employeeTable);
    void deleteEmployeeTable(long id);
}
