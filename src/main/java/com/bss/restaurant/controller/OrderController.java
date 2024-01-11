package com.bss.restaurant.controller;

import com.bss.restaurant.dto.request.OrderRequest;
import com.bss.restaurant.dto.request.OrderUpdateStatusRequest;
import com.bss.restaurant.dto.response.MessageResponse;
import com.bss.restaurant.dto.response.OrderResponse;
import com.bss.restaurant.dto.response.OrderShortResponse;
import com.bss.restaurant.dto.response.PaginationResponse;
import com.bss.restaurant.service.OderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order/")
public class OrderController {
    @Autowired
    private OderService orderService;

    @GetMapping("datatable")
    public ResponseEntity<PaginationResponse<OrderResponse>> getOrders(@RequestParam(defaultValue = "0") int search,
                                                                       @RequestParam(defaultValue = "1") int pageNumber,
                                                                       @RequestParam(defaultValue = "10") int pageSize,
                                                                       @RequestParam(defaultValue = "orderNumber") String sort) {
        return ResponseEntity.ok(orderService.getOrders(search, pageNumber, pageSize, sort));
    }

    @GetMapping("get")
    public ResponseEntity<List<OrderShortResponse>> getOrderNumbers() {
        return ResponseEntity.ok(orderService.getOrderNumbers());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrder(id).orElse(null));
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.saveOrder(orderRequest);
        return new ResponseEntity<>(MessageResponse.builder().message("Order Created Successfully.").build(), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateOrder(@PathVariable UUID id, @RequestBody OrderRequest orderRequest) {
        orderService.updateOrder(id, orderRequest);
        return ResponseEntity.ok(MessageResponse.builder().message("Order Updated Successfully.").build());
    }

    @PutMapping("update-status/{id}")
    public ResponseEntity<MessageResponse> updateOrderStatus(@PathVariable UUID id, @RequestBody OrderUpdateStatusRequest orderUpdateStatusRequest) {
        Integer status = orderUpdateStatusRequest.getStatus();
        orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(MessageResponse.builder().message("OrderStatus Updated Successfully.").build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<MessageResponse> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(MessageResponse.builder().message("Order Deleted SuccessFully").build());
    }
}
