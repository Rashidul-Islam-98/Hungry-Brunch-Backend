package com.bss.restaurant.util;

import com.bss.restaurant.exception.RestaurantInvalidPageSizeException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {
    public PageRequest createPage(int pageNumber, int pageSize, String sort){
        int page = Math.max(0, pageNumber);
        if(pageSize<1) {
            throw new RestaurantInvalidPageSizeException("Page number should be greater than 0");
        }
        return PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sort)));
    }
}
