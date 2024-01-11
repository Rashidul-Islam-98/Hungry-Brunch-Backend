package com.bss.restaurant.controller;

import com.bss.restaurant.dto.request.TableRequest;
import com.bss.restaurant.dto.response.MessageResponse;
import com.bss.restaurant.dto.response.PaginationResponse;
import com.bss.restaurant.dto.response.TableResponse;
import com.bss.restaurant.dto.response.TableShortResponse;
import com.bss.restaurant.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/table/")
public class TableController {
    @Autowired
    private TableService tableService;

    @GetMapping("datatable")
    public ResponseEntity<PaginationResponse<TableResponse>> getTables(@RequestParam(defaultValue = "1") int pageNumber,
                                                                       @RequestParam(defaultValue = "10") int pageSize,
                                                                       @RequestParam(defaultValue = "numberOfSeats") String sort) {
        return ResponseEntity.ok(tableService.getTables(pageNumber, pageSize, sort));
    }

    @GetMapping("get")
    public ResponseEntity<List<TableShortResponse>> getTableNumbers() {
        return ResponseEntity.ok(tableService.getTableNumbers());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<TableResponse> getTable(@PathVariable long id) {
        return ResponseEntity.ok(tableService.getTable(id).orElse(null));
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createTable(@RequestBody  TableRequest tableRequest) {
        tableService.saveTable(tableRequest);
        return new ResponseEntity<>(MessageResponse.builder().message("Table Created Successfully.").build(), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateTable(@PathVariable long id, @RequestBody TableRequest tableRequest) throws IOException {
        tableService.updateTable(id, tableRequest);
        return ResponseEntity.ok(MessageResponse.builder().message("Table Updated Successfully.").build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<MessageResponse> deleteTable(@PathVariable long id) throws IOException {
        tableService.deleteTable(id);
        return ResponseEntity.ok(MessageResponse.builder().message("Employee Deleted SuccessFully").build());
    }
}
