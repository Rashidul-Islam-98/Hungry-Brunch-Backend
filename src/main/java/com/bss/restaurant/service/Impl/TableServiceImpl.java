package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.TableRepository;
import com.bss.restaurant.dto.request.TableRequest;
import com.bss.restaurant.dto.response.EmployeeShortResponse;
import com.bss.restaurant.dto.response.PaginationResponse;
import com.bss.restaurant.dto.response.TableResponse;
import com.bss.restaurant.dto.response.TableShortResponse;
import com.bss.restaurant.entity.Employee;
import com.bss.restaurant.entity.FoodTable;
import com.bss.restaurant.service.TableService;
import com.bss.restaurant.util.CreatePaginationHelper;
import com.bss.restaurant.util.ImageUploader;
import com.bss.restaurant.util.PaginationBuilder;
import com.bss.restaurant.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TableServiceImpl implements TableService {
    @Autowired
    private TableRepository tableRepository;

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
        for(FoodTable table: tables) {
            var temp = createTableResponse(table);
            data.add(temp);
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
    public Optional<TableResponse> getTable(long tableId) {
        var table = tableRepository.findById(tableId).orElse(null);
        var tableResponse = createTableResponse(table);
        return Optional.of(tableResponse);
    }

    @Override
    public void saveTable(TableRequest table) {
        var foundTable = tableRepository.findByTableNumber(table.getTableNumber());
        if(foundTable != null) {
            throw new ResourceAccessException("Table already exist");
        }
        imageUploader.uploadImage(table.getBase64(), table.getImage(),TABLE);
        var saveTable = createTable(table);
        tableRepository.save(saveTable);
    }

    @Override
    public void updateTable(long tableId, TableRequest table) throws IOException {
        var updateTable = tableRepository.findById(tableId).orElse(null);
        if(updateTable!=null && !table.getImage().equals(updateTable.getImage())) {
            imageUploader.deleteImage(TABLE,updateTable.getImage());
            imageUploader.uploadImage(table.getBase64(),table.getImage(), TABLE);
        }
        updateTable = createTable(table);
        tableRepository.save(updateTable);
    }

    @Override
    public void deleteTable(long tableId) throws IOException {
        var table = tableRepository.findById(tableId).orElseThrow(()-> new ResourceAccessException("Table Doesn't Exist"));
        imageUploader.deleteImage(TABLE,table.getImage());
        tableRepository.delete(table);
    }

    public TableResponse createTableResponse(FoodTable table) {
        List<EmployeeShortResponse> tableEmployees = new ArrayList<>();
        var allEmployees = table.getEmployees();
        for(Employee employee: allEmployees) {
            var temp = EmployeeShortResponse.builder()
                    .id(employee.getId())
                    .name(employee.getUser().getFirstName()+employee.getUser().getMiddleName()+employee.getUser().getLastName())
                    .build();
            tableEmployees.add(temp);
        }
        return TableResponse.builder()
                .id(table.getId())
                .tableNumber(table.getTableNumber())
                .numberOfSeats(table.getNumberOfSeats())
                .isOccupied(false)
                .image(table.getImage())
                .employees(tableEmployees)
                .build();
    }

    public FoodTable createTable(TableRequest tableRequest) {
        return FoodTable.builder()
                .tableNumber(tableRequest.getTableNumber())
                .numberOfSeats(tableRequest.getNumberOfSeats())
                .isOccupied(false)
                .image(tableRequest.getImage())
                .employees(null)
                .build();
    }
}
