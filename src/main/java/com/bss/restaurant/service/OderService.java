package com.bss.restaurant.service;

import com.bss.restaurant.dto.request.OrderRequest;
import com.bss.restaurant.dto.response.OrderResponse;
import com.bss.restaurant.dto.response.OrderShortResponse;
import com.bss.restaurant.dto.response.PaginationResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OderService {
    PaginationResponse<OrderResponse> getOrders(int orderStatus, int pageNumber, int pageSize, String sort);
    List<OrderShortResponse> getOrderNumbers();
    Optional<OrderResponse> getOrder(UUID orderId);
    void saveOrder(OrderRequest order);
    void updateOrder(UUID orderId, OrderRequest order);
    void updateOrderStatus(UUID orderId, Integer status);
    void deleteOrder(UUID orderId);
}
