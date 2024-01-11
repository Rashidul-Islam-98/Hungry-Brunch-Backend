package com.bss.restaurant.controller;

import com.bss.restaurant.dto.request.EmployeeRequest;
import com.bss.restaurant.dto.response.EmployeeResponse;
import com.bss.restaurant.dto.response.EmployeeShortResponse;
import com.bss.restaurant.dto.response.MessageResponse;
import com.bss.restaurant.dto.response.PaginationResponse;
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
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("datatable")
    public ResponseEntity<PaginationResponse<EmployeeResponse>> getEmployees(@RequestParam(defaultValue = "") String search,
                                                                             @RequestParam(defaultValue = "1") Integer pageNumber,
                                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                                             @RequestParam(defaultValue = "designation") String sort) {
        return ResponseEntity.ok(employeeService.getEmployees(search, pageNumber, pageSize, sort));
    }

    @GetMapping("get")
    public ResponseEntity<List<EmployeeShortResponse>> getEmployeeNames() {
        return ResponseEntity.ok(employeeService.getEmployeesName());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.getEmployee(id).orElseThrow(null));
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createEmployee(@RequestBody EmployeeRequest employee) {
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>(MessageResponse.builder().message("Employee Created Successfully.").build(), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateEmployee(@PathVariable UUID id, @RequestBody String designation) {
        employeeService.updateEmployee(id, designation);
        return ResponseEntity.ok(MessageResponse.builder().message("Employee Designation Updated Successfully.").build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<MessageResponse> deleteEmployee(@PathVariable UUID id) throws IOException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(MessageResponse.builder().message("Employee Deleted SuccessFully").build());
    }
}
