package com.bss.restaurant.dto.internal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaginationHelper {
    private int items;
    private int itemStart;
    private int itemEnd;
    private long totalItems;
    private int currentPage;
    private int pageSize;
    private int totalPages;
}
