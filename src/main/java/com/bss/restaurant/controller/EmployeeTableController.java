package com.bss.restaurant.controller;

import com.bss.restaurant.dto.response.EmployeeTableResponse;
import com.bss.restaurant.dto.response.MessageResponse;
import com.bss.restaurant.dto.response.PaginationResponse;
import com.bss.restaurant.entity.EmployeeTable;
import com.bss.restaurant.service.EmployeeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-table/")
public class EmployeeTableController {
    @Autowired
    private EmployeeTableService employeeTableService;

    @GetMapping("datatable")
    public ResponseEntity<PaginationResponse<EmployeeTableResponse>> getEmployeeTables(@RequestParam(defaultValue = "1") int pageNumber,
                                                                                       @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(employeeTableService.getEmployeesInTable(pageNumber, pageSize));
    }

    @GetMapping("get")
    public ResponseEntity<List<EmployeeTableResponse>> getOnlyEmployeeTables() {
        return ResponseEntity.ok(employeeTableService.getOnlyEmployeesInTable());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<EmployeeTableResponse> getEmployeeTable(@PathVariable long id) {
        return ResponseEntity.ok(employeeTableService.getEmployeeTable(id).orElse(null));
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> saveEmployeeTable(@RequestBody EmployeeTable employeeTable) {
        employeeTableService.saveEmployeeTable(employeeTable);
        return new ResponseEntity<>(MessageResponse.builder().message("EmployeeTable Created Successfully.").build(), HttpStatus.CREATED);
    }

    @PostMapping("create-range")
    public ResponseEntity<MessageResponse> saveEmployeeTables(@RequestBody List<EmployeeTable> employeeTables) {
        for(EmployeeTable employeeTable: employeeTables) {
            employeeTableService.saveEmployeeTable(employeeTable);
        }
        return new ResponseEntity<>(MessageResponse.builder().message("EmployeeTable Successfully Created.").build(), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateEmployeeTable(@PathVariable long id, @RequestBody EmployeeTable employeeTable) {
        employeeTableService.updateEmployeeTable(id, employeeTable);
        return ResponseEntity.ok(MessageResponse.builder().message("EmployeeTable Updated Successfully.").build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<MessageResponse> deleteEmployeeTable(@PathVariable long id) {
        employeeTableService.deleteEmployeeTable(id);
        return ResponseEntity.ok(MessageResponse.builder().message("EmployeeTable Deleted SuccessFully").build());
    }
}
