package com.bss.restaurant.dto.response;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@ToString
public class PaginationResponse<T> {
    private Long totalItems;
    private int items;
    private int from;
    private int to;
    private int totalPages;
    private int currentPage;
    private int itemPerPage;
    private List<T> data;
}
