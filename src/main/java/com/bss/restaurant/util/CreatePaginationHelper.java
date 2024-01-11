package com.bss.restaurant.util;

import com.bss.restaurant.dto.internal.PaginationHelper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CreatePaginationHelper<T> {
    public PaginationHelper paginationHelperCreating(Page<T> paginatedData, int pageNumber, int pageSize){
        int items = paginatedData.getNumberOfElements();
        int itemStart = items!=0 ? (pageNumber-1)*pageSize +1: 0;
        int itemEnd = items!=0 ? itemStart+items-1 : 0;
        return PaginationHelper.builder()
                .items(items)
                .itemStart(itemStart)
                .itemEnd(itemEnd)
                .totalItems(paginatedData.getTotalElements())
                .currentPage(paginatedData.getNumber()+1)
                .totalPages(paginatedData.getTotalPages())
                .pageSize(pageSize)
                .build();
    }
}
