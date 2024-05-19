package com.bss.restaurant.service;

import com.bss.restaurant.dto.request.EmployeeTableRequest;
import com.bss.restaurant.dto.response.EmployeeTableResponse;
import com.bss.restaurant.dto.response.PaginationResponse;
import com.bss.restaurant.dto.response.RestaurantListResponse;
import com.bss.restaurant.entity.EmployeeTable;

import java.util.List;

public interface EmployeeTableService {
    PaginationResponse<EmployeeTableResponse> getEmployeesInTable(int pageNumber, int pageSize);
    RestaurantListResponse<EmployeeTableResponse> getOnlyEmployeesInTable();

    List<EmployeeTable> getEmployeeTableByTableId(long tableId);
    EmployeeTable getEmployeeTableById(long employeeTableId);
    EmployeeTableResponse getEmployeeTable(long id);
    void saveEmployeeTable(EmployeeTableRequest employeeTableRequest);
    void updateEmployeeTable(long id, EmployeeTableRequest employeeTableRequest);
    void deleteEmployeeTable(long id);
}
