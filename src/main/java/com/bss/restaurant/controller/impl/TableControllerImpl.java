package com.bss.restaurant.controller.impl;

import com.bss.restaurant.controller.TableController;
import com.bss.restaurant.dto.request.TableRequest;
import com.bss.restaurant.dto.response.RestaurantBaseResponse;
import com.bss.restaurant.dto.response.PaginationResponse;
import com.bss.restaurant.dto.response.TableResponse;
import com.bss.restaurant.dto.response.TableShortResponse;
import com.bss.restaurant.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/table/")
public class TableControllerImpl implements TableController {
    @Autowired
    private TableService tableService;

    @Override
    public ResponseEntity<PaginationResponse<TableResponse>> getTables(int pageNumber, int pageSize, String sort) {
        return ResponseEntity.ok(tableService.getTables(pageNumber, pageSize, sort));
    }

    @Override
    public ResponseEntity<List<TableShortResponse>> getTableNumbers() {
        return ResponseEntity.ok(tableService.getTableNumbers());
    }

    @Override
    public ResponseEntity<TableResponse> getTable(@PathVariable long id) {
        return ResponseEntity.ok(tableService.getTable(id));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> createTable(@RequestBody  TableRequest tableRequest) {
        tableService.saveTable(tableRequest);
        return new ResponseEntity<>(RestaurantBaseResponse.builder().message("Table Created Successfully.").build(), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> updateTable(@PathVariable long id, @RequestBody TableRequest tableRequest) throws IOException {
        tableService.updateTable(id, tableRequest);
        return ResponseEntity.ok(RestaurantBaseResponse.builder().message("Table Updated Successfully.").build());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> deleteTable(@PathVariable long id) throws IOException {
        tableService.deleteTable(id);
        return ResponseEntity.ok(RestaurantBaseResponse.builder().message("Employee Deleted SuccessFully").build());
    }
}
