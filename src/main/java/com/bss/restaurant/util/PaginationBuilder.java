package com.bss.restaurant.util;

import com.bss.restaurant.dto.internal.PaginationHelper;
import com.bss.restaurant.dto.response.PaginationResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaginationBuilder<T> {
    public PaginationResponse<T> createPagination(PaginationHelper paginationHelper, List<T> data) {
        return PaginationResponse.<T>builder()
                .totalItems(paginationHelper.getTotalItems())
                .items(paginationHelper.getItems())
                .itemPerPage(paginationHelper.getPageSize())
                .from(paginationHelper.getItemStart())
                .to(paginationHelper.getItemEnd())
                .currentPage(paginationHelper.getCurrentPage())
                .totalPages(paginationHelper.getTotalPages())
                .data(data)
                .build();
    }
}
