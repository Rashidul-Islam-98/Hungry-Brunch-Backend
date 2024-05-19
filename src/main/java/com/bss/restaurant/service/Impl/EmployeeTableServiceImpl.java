package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.EmployeeRepository;
import com.bss.restaurant.dao.EmployeeTableRepository;
import com.bss.restaurant.dao.TableRepository;
import com.bss.restaurant.dto.internal.PaginationHelper;
import com.bss.restaurant.dto.request.EmployeeTableRequest;
import com.bss.restaurant.dto.response.*;
import com.bss.restaurant.entity.EmployeeTable;
import com.bss.restaurant.exception.RestaurantNotFoundException;
import com.bss.restaurant.service.EmployeeService;
import com.bss.restaurant.service.EmployeeTableService;
import com.bss.restaurant.service.TableService;
import com.bss.restaurant.util.CreatePaginationHelper;
import com.bss.restaurant.util.PaginationBuilder;
import com.bss.restaurant.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public RestaurantListResponse<EmployeeTableResponse> getOnlyEmployeesInTable() {
        var employeeTables = employeeTableRepository.findAll();
        List<EmployeeTableResponse> data = new ArrayList<>();
        for(EmployeeTable employeeTable: employeeTables) {
            var temp = createEmployeeTableResponse(employeeTable);
            data.add(temp);
        }
        return RestaurantListResponse.<EmployeeTableResponse>builder().data(data).build();
    }

    @Override
    public EmployeeTable getEmployeeTableById(long employeeTableId) {
        return employeeTableRepository.findById(employeeTableId).orElseThrow(()->
                new RestaurantNotFoundException("Employee doesn't belongs to this table")
        );
    }

    @Override
    public EmployeeTableResponse getEmployeeTable(long id) {
        var employeeTable = getEmployeeTableById(id);
        return createEmployeeTableResponse(employeeTable);
    }

    @Override
    public void saveEmployeeTable(EmployeeTableRequest employeeTableRequest) {
        var employeeTable = createEmployeeTable(employeeTableRequest);
        employeeTableRepository.save(employeeTable);
    }

    @Override
    public void updateEmployeeTable(long id, EmployeeTableRequest employeeTableRequest) {
        var updateEmployeeTable = getEmployeeTableById(id);
        updateEmployeeTable.setEmployeeId(employeeTableRequest.getEmployeeId());
        updateEmployeeTable.setTableId(employeeTableRequest.getTableId());
        employeeTableRepository.save(updateEmployeeTable);
    }

    @Override
    public void deleteEmployeeTable(long id) {
        employeeTableRepository.deleteById(id);
    }

    public List<EmployeeTable> getEmployeeTableByTableId(long tableId) {
        return employeeTableRepository.findAllByTableId(tableId);
    }

    protected EmployeeTableResponse createEmployeeTableResponse(EmployeeTable employeeTable) {
        var employeeResponse = employeeService.getEmployee(employeeTable.getEmployeeId());
        var tableResponse = tableService.getTable(employeeTable.getTableId());
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

    private EmployeeTable createEmployeeTable(EmployeeTableRequest employeeTableRequest) {
        return EmployeeTable.builder()
                .employeeId(employeeTableRequest.getEmployeeId())
                .tableId(employeeTableRequest.getTableId())
                .build();
    }
}
