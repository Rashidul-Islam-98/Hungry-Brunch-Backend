package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.TableRepository;
import com.bss.restaurant.dto.request.TableRequest;
import com.bss.restaurant.dto.response.*;
import com.bss.restaurant.entity.EmployeeTable;
import com.bss.restaurant.entity.FoodTable;
import com.bss.restaurant.exception.RestaurantBadRequestException;
import com.bss.restaurant.exception.RestaurantNotFoundException;
import com.bss.restaurant.service.EmployeeService;
import com.bss.restaurant.service.EmployeeTableService;
import com.bss.restaurant.service.TableService;
import com.bss.restaurant.util.CreatePaginationHelper;
import com.bss.restaurant.util.ImageUploader;
import com.bss.restaurant.util.PaginationBuilder;
import com.bss.restaurant.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class  TableServiceImpl implements TableService {
    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    @Lazy
    private EmployeeTableService employeeTableService;

    @Autowired
    private ImageUploader imageUploader;

    @Autowired
    private CreatePaginationHelper<FoodTable> createPaginationHelper;

    @Autowired
    private PaginationBuilder<TableResponse> paginationBuilder;

    @Autowired
    private PaginationUtil paginationUtil;

    private static final String TABLE = "table";

    @Override
    public PaginationResponse getTables(int pageNumber, int pageSize, String sort) {
        var pageRequest = paginationUtil.createPage(pageNumber, pageSize, sort);
        var pagingTable = tableRepository.findAll(pageRequest);
        var tables = pagingTable.getContent();
        List<TableResponse> data = new ArrayList<>();
        for(FoodTable table:tables) {
            List<EmployeeShortResponseForTable> employees = new ArrayList<>();
            var employeeTables = employeeTableService.getEmployeeTableByTableId(table.getId());
            for(EmployeeTable employeeTable:employeeTables ) {
                var employee = employeeService.getEmployee(employeeTable.getEmployeeId());
                    employees.add(EmployeeShortResponseForTable.builder()
                                    .employeeTableId(employeeTable.getId())
                                    .id(employee.getId())
                                    .name(employee.getUser().getFullName())
                            .build());
            }
            var tableResponse = createTableResponse(table);
            tableResponse.setEmployees(employees);
            data.add(tableResponse);
        }
        var paginationHelper = createPaginationHelper.paginationHelperCreating(pagingTable, pageNumber, pageSize);
        return paginationBuilder.createPagination(paginationHelper, data);
    }

    @Override
    public List<TableShortResponse> getTableNumbers() {
        var results = tableRepository.findAllTableNumberAndIds();
        List<TableShortResponse> tableShortResponses = new ArrayList<>();
        for (Object[] result : results) {
            Long id = (Long) result[0];
            String tableNumber = (String) result[1];

            var tableShortResponse = TableShortResponse.builder()
                    .id(id)
                    .tableNumber(tableNumber)
                    .build();

            tableShortResponses.add(tableShortResponse);
        }
        return tableShortResponses;
    }

    @Override
    public FoodTable getTableById(long tableId) {
        return tableRepository.findById(tableId).orElseThrow(()->
                new RestaurantNotFoundException("Table not found.")
                );
    }

    @Override
    public FoodTable getTableByTableNumber(String tableNumber) {
        return tableRepository.findByTableNumber(tableNumber).orElseThrow(()->
                new RestaurantNotFoundException("Table doesn't Exist.")
                );
    }

    @Override
    public TableResponse getTable(long tableId) {
        var table = getTableById(tableId);
        return createTableResponse(table);
    }

    @Override
    public void saveTable(TableRequest table) {
        var foundTable = tableRepository.findByTableNumber(table.getTableNumber()).orElse(null);
        if(foundTable != null) {
            throw new RestaurantBadRequestException("Table already exist");
        }
        var imageUrl = imageUploader.uploadImage(table.getBase64(), table.getImage(),TABLE);
        var saveTable = createTable(table);
        saveTable.setImageUrl(imageUrl);
        tableRepository.save(saveTable);
    }

    @Override
    public void updateTable(long tableId, TableRequest table) throws IOException {
        var updateTable = getTableById(tableId);
        String imageUrl = null;
        if(updateTable!=null && !table.getImage().equals(updateTable.getImage())) {
            imageUploader.deleteImage(TABLE,updateTable.getImage());
            imageUrl = imageUploader.uploadImage(table.getBase64(),table.getImage(), TABLE);
        }
        updateTable.setTableNumber(table.getTableNumber());
        updateTable.setNumberOfSeats(table.getNumberOfSeats());
        if(imageUrl != null) {
            updateTable.setImage(table.getImage());
            updateTable.setImageUrl(imageUrl);
        }
        tableRepository.save(updateTable);
    }

    @Override
    public void deleteTable(long tableId) throws IOException {
        var table = getTableById(tableId);
        employeeTableService.deleteEmployeeTable(tableId);
        imageUploader.deleteImage(TABLE,table.getImage());
        tableRepository.delete(table);
    }

    public TableResponse createTableResponse(FoodTable table) {
        return TableResponse.builder()
                .id(table.getId())
                .tableNumber(table.getTableNumber())
                .numberOfSeats(table.getNumberOfSeats())
                .isOccupied(false)
                .image(table.getImageUrl())
                .build();
    }

    public FoodTable createTable(TableRequest tableRequest) {
        return FoodTable.builder()
                .tableNumber(tableRequest.getTableNumber())
                .numberOfSeats(tableRequest.getNumberOfSeats())
                .isOccupied(false)
                .image(tableRequest.getImage())
                .build();
    }
}
