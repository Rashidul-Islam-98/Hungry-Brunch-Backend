package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.EmployeeRepository;
import com.bss.restaurant.dao.EmployeeTableRepository;
import com.bss.restaurant.dao.TableRepository;
import com.bss.restaurant.dto.internal.PaginationHelper;
import com.bss.restaurant.dto.response.*;
import com.bss.restaurant.entity.EmployeeTable;
import com.bss.restaurant.exception.InvalidPageSizeException;
import com.bss.restaurant.exception.NotFoundException;
import com.bss.restaurant.service.EmployeeService;
import com.bss.restaurant.service.EmployeeTableService;
import com.bss.restaurant.service.TableService;
import com.bss.restaurant.util.CreatePaginationHelper;
import com.bss.restaurant.util.PaginationBuilder;
import com.bss.restaurant.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeTableServiceImpl implements EmployeeTableService {
    @Autowired
    private EmployeeTableRepository employeeTableRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PaginationBuilder<EmployeeTableResponse> paginationBuilder;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CreatePaginationHelper<EmployeeTable> createPaginationHelper;

    @Autowired
    private TableService tableService;

    @Autowired
    private PaginationUtil paginationUtil;

    @Override
    public PaginationResponse getEmployeesInTable(int pageNumber, int pageSize) {
        var pageRequest = paginationUtil.createPage(pageNumber,pageSize, "tableId");
        var pagingEmployeeTable = employeeTableRepository.findAll(pageRequest);
        var employeeTables = pagingEmployeeTable.getContent();
        List<EmployeeTableResponse> data = new ArrayList<>();
        for(EmployeeTable employeeTable: employeeTables) {
            var temp = createEmployeeTableResponse(employeeTable);
            data.add(temp);
        }
        PaginationHelper paginationHelper = createPaginationHelper.paginationHelperCreating(pagingEmployeeTable, pageNumber, pageSize);
        return paginationBuilder.createPagination(paginationHelper, data);
    }

    @Override
    public List<EmployeeTableResponse> getOnlyEmployeesInTable() {
        var employeeTables = employeeTableRepository.findAll();
        List<EmployeeTableResponse> data = new ArrayList<>();
        for(EmployeeTable employeeTable: employeeTables) {
            var temp = createEmployeeTableResponse(employeeTable);
            data.add(temp);
        }
        return data;
    }

    @Override
    public Optional<EmployeeTableResponse> getEmployeeTable(long id) {
        var employeeTable = employeeTableRepository.findById(id).orElse(null);
        var employeeTableResponse = createEmployeeTableResponse(employeeTable);
        return Optional.of(employeeTableResponse);
    }

    @Override
    public void saveEmployeeTable(EmployeeTable employeeTable) {
        try {
            var table = tableRepository.findById(employeeTable.getTableId()).orElse(null);
            var employee = employeeRepository.findById(employeeTable.getEmployeeId()).orElse(null);
            var employees = table.getEmployees();
            employees.add(employee);
            table.setEmployees(employees);
            tableRepository.save(table);
            employeeTableRepository.save(employeeTable);
        }catch (NullPointerException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public void updateEmployeeTable(long id, EmployeeTable employeeTable) {
        try {
            var updateEmployeeTable = employeeTableRepository.findById(id).orElse(null);
            updateEmployeeTable.setEmployeeId(employeeTable.getEmployeeId());
            updateEmployeeTable.setTableId(employeeTable.getId());
            saveEmployeeTable(updateEmployeeTable);
        }catch (NullPointerException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteEmployeeTable(long id) {
        try {
            var deleteEmployeeTable = employeeTableRepository.findById(id).orElse(null);
            var table = tableRepository.findById(deleteEmployeeTable.getTableId()).orElse(null);
            var employee = employeeRepository.findById(deleteEmployeeTable.getEmployeeId()).orElse(null);
            var employees = table.getEmployees();
            employees.remove(employee);
            table.setEmployees(employees);
            tableRepository.save(table);
            employeeTableRepository.deleteById(id);
        }catch (NullPointerException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    protected EmployeeTableResponse createEmployeeTableResponse(EmployeeTable employeeTable) {
        var employeeResponse = employeeService.getEmployee(employeeTable.getEmployeeId()).orElse(null);
        var tableResponse = tableService.getTable(employeeTable.getTableId()).orElse(null);
        var employee = EmployeeShortResponse.builder()
                .id(employeeResponse.getId())
                .name(employeeResponse.getUser().getFirstName()+employeeResponse.getUser().getMiddleName()+employeeResponse.getUser().getLastName())
                .build();
        var table = TableShortResponse.builder()
                .id(tableResponse.getId())
                .tableNumber(tableResponse.getTableNumber())
                .build();
        return EmployeeTableResponse.builder()
                .id(employeeTable.getId())
                .employee(employee)
                .table(table)
                .build();
    }
}
