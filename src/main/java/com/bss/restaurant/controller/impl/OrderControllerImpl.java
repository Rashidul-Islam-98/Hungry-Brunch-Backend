package com.bss.restaurant.controller.impl;

import com.bss.restaurant.controller.OrderController;
import com.bss.restaurant.dto.request.OrderRequest;
import com.bss.restaurant.dto.request.OrderUpdateStatusRequest;
import com.bss.restaurant.dto.response.RestaurantBaseResponse;
import com.bss.restaurant.dto.response.OrderResponse;
import com.bss.restaurant.dto.response.OrderShortResponse;
import com.bss.restaurant.dto.response.PaginationResponse;
import com.bss.restaurant.service.OderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order/")
public class OrderControllerImpl implements OrderController {
    @Autowired
    private OderService orderService;

    @Override
    public ResponseEntity<PaginationResponse<OrderResponse>> getOrders(@RequestParam(defaultValue = "0") int search,
                                                                       @RequestParam(defaultValue = "1") int pageNumber,
                                                                       @RequestParam(defaultValue = "10") int pageSize,
                                                                       @RequestParam(defaultValue = "orderNumber") String sort) {
        return ResponseEntity.ok(orderService.getOrders(search, pageNumber, pageSize, sort));
    }

    @Override
    public ResponseEntity<List<OrderShortResponse>> getOrderNumbers() {
        return ResponseEntity.ok(orderService.getOrderNumbers());
    }

    @Override
    public ResponseEntity<OrderResponse> getOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrder(id).orElse(null));
    }

    @Override
    public ResponseEntity<RestaurantBaseResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.saveOrder(orderRequest);
        return new ResponseEntity<>(RestaurantBaseResponse.builder().message("Order Created Successfully.").build(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<RestaurantBaseResponse> updateOrder(@PathVariable UUID id, @RequestBody OrderRequest orderRequest) {
        orderService.updateOrder(id, orderRequest);
        return ResponseEntity.ok(RestaurantBaseResponse.builder().message("Order Updated Successfully.").build());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> updateOrderStatus(@PathVariable UUID id, @RequestBody OrderUpdateStatusRequest orderUpdateStatusRequest) {
        Integer status = orderUpdateStatusRequest.getStatus();
        orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(RestaurantBaseResponse.builder().message("OrderStatus Updated Successfully.").build());
    }

    @Override
    @PreAuthorize("hasAnyRole('ARTIST', 'OWNER')")
    public ResponseEntity<RestaurantBaseResponse> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(RestaurantBaseResponse.builder().message("Order Deleted SuccessFully").build());
    }
}
