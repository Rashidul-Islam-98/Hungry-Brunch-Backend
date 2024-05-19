package com.bss.restaurant.service;

import com.bss.restaurant.dto.request.TableRequest;
import com.bss.restaurant.dto.response.PaginationResponse;
import com.bss.restaurant.dto.response.TableResponse;
import com.bss.restaurant.dto.response.TableShortResponse;
import com.bss.restaurant.entity.FoodTable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TableService {
    PaginationResponse<TableResponse> getTables(int pageNumber, int pageSize, String sort);
    List<TableShortResponse> getTableNumbers();
    FoodTable getTableById(long tableId);
    FoodTable getTableByTableNumber(String tableNumber);
    TableResponse getTable(long tableId);
    void saveTable(TableRequest table);
    void updateTable(long tableId,TableRequest table) throws IOException;
    void deleteTable(long tableId) throws IOException;
}
