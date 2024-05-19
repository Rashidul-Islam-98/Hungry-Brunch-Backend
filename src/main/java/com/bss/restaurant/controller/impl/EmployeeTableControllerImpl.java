package com.bss.restaurant.controller.impl;

import com.bss.restaurant.controller.EmployeeTableController;
import com.bss.restaurant.dto.request.EmployeeTableRequest;
import com.bss.restaurant.dto.response.EmployeeTableResponse;
import com.bss.restaurant.dto.response.RestaurantBaseResponse;
import com.bss.restaurant.dto.response.PaginationResponse;
import com.bss.restaurant.dto.response.RestaurantListResponse;
import com.bss.restaurant.service.EmployeeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-table/")
public class EmployeeTableControllerImpl implements EmployeeTableController {
    @Autowired
    private EmployeeTableService employeeTableService;

    @Override
    public ResponseEntity<PaginationResponse<EmployeeTableResponse>> getEmployeeTables(Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(employeeTableService.getEmployeesInTable(pageNumber, pageSize));
    }

    @Override
    public ResponseEntity<RestaurantListResponse<EmployeeTableResponse>> getOnlyEmployeeTables() {
        return ResponseEntity.ok(employeeTableService.getOnlyEmployeesInTable());
    }

    @Override
    public ResponseEntity<EmployeeTableResponse> getEmployeeTable(long id) {
        return ResponseEntity.ok(employeeTableService.getEmployeeTable(id));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> saveEmployeeTable(EmployeeTableRequest employeeTableRequest) {
        employeeTableService.saveEmployeeTable(employeeTableRequest);
        return new ResponseEntity<>(RestaurantBaseResponse.builder().message("EmployeeTable Created Successfully.").build(), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> saveEmployeeTables(List<EmployeeTableRequest> employeeTables) {
        for(EmployeeTableRequest employeeTable: employeeTables) {
            employeeTableService.saveEmployeeTable(employeeTable);
        }
        return new ResponseEntity<>(RestaurantBaseResponse.builder().message("EmployeeTable Successfully Created.").build(), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> updateEmployeeTable(long id, EmployeeTableRequest employeeTable) {
        employeeTableService.updateEmployeeTable(id, employeeTable);
        return ResponseEntity.ok(RestaurantBaseResponse.builder().message("EmployeeTable Updated Successfully.").build());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> deleteEmployeeTable(long id) {
        employeeTableService.deleteEmployeeTable(id);
        return ResponseEntity.ok(RestaurantBaseResponse.builder().message("EmployeeTable Deleted SuccessFully").build());
    }
}
