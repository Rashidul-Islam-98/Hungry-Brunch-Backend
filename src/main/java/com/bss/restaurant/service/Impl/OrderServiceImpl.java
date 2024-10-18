package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.FoodRepository;
import com.bss.restaurant.dao.OrderItemRepository;
import com.bss.restaurant.dao.OrderRepository;
import com.bss.restaurant.dao.TableRepository;
import com.bss.restaurant.dto.request.OrderItemRequest;
import com.bss.restaurant.dto.request.OrderRequest;
import com.bss.restaurant.dto.response.*;
import com.bss.restaurant.entity.Order;
import com.bss.restaurant.entity.OrderItem;
import com.bss.restaurant.exception.RestaurantBadRequestException;
import com.bss.restaurant.exception.RestaurantNotFoundException;
import com.bss.restaurant.projection.OrderNumberAndIdProjection;
import com.bss.restaurant.service.OderService;
import com.bss.restaurant.util.CreatePaginationHelper;
import com.bss.restaurant.util.PaginationBuilder;
import com.bss.restaurant.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CreatePaginationHelper<Order> createPaginationHelper;

    @Autowired
    private PaginationBuilder<OrderResponse> paginationBuilder;

    @Autowired
    private TableServiceImpl tableService;

    @Autowired
    private FoodServiceImpl foodService;

    @Autowired
    private PaginationUtil paginationUtil;

    @Override
    public PaginationResponse getOrders(int orderStatus, int pageNumber, int pageSize, String sort) {
        var pageRequest = paginationUtil.createPage(pageNumber, pageSize, sort);
        Page<Order> pagingOrder;
        if(orderStatus == 0){
            pagingOrder = orderRepository.findAll(pageRequest);
        } else {
            pagingOrder = orderRepository.findByOrderStatus(orderStatus, pageRequest);
        }
        var orders = pagingOrder.getContent();
        List<OrderResponse> data = new ArrayList<>();
        for(Order order: orders) {
            var temp = createOrderResponse(order);
            data.add(temp);
        }
        var paginationHelper = createPaginationHelper.paginationHelperCreating(pagingOrder, pageNumber, pageSize);
        return paginationBuilder.createPagination(paginationHelper, data);
    }

    @Override
    public List<OrderShortResponse> getOrderNumbers() {
        var results = orderRepository.findAllOrderNumberAndIds();
        List<OrderShortResponse> orderShortResponses = new ArrayList<>();
        for (OrderNumberAndIdProjection result : results) {

            var orderShortResponse = OrderShortResponse.builder()
                    .orderId(result.getId())
                    .orderNumber(result.getOrderNumber())
                    .build();

            orderShortResponses.add(orderShortResponse);
        }
        return orderShortResponses;
    }

    @Override
    public Optional<OrderResponse> getOrder(UUID orderId) {
        var order = orderRepository.findById(orderId).orElse(null);
        var orderResponse = createOrderResponse(order);
        return Optional.of(orderResponse);
    }

    @Override
    public void saveOrder(OrderRequest order) {
        var foundOrder = orderRepository.findByOrderNumber(order.getOrderNumber());
        if(foundOrder != null) {
            throw new RestaurantBadRequestException("Order already exist");
        }
        var saveOrder = createOrder(order);
        orderRepository.save(saveOrder);
    }

    @Override
    public void updateOrder(UUID orderId, OrderRequest order) {
        var updateOrder = orderRepository.findById(orderId).orElseThrow(()->
                new RestaurantNotFoundException("Order not found")
                );
       updateOrder = createOrder(order);
       orderRepository.save(updateOrder);
    }

    @Override
    public void updateOrderStatus(UUID orderId, Integer status) {
        var order = orderRepository.findById(orderId).orElse(null);
        if(order == null){
            throw new RestaurantNotFoundException("Order doesn't exist");
        }
        order.setOrderStatus(status);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }

    protected OrderResponse createOrderResponse(Order order) {
        var tableResponse = tableService.createTableResponse(order.getTable());
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        var orderItems = order.getItems();
        for(OrderItem orderItem: orderItems) {
            var temp = createOrderItemResponse(orderItem);
            orderItemResponses.add(temp);
        }
        String status;
        switch (order.getOrderStatus()){
            case 1:
                status = "Pending";
                break;
            case 2:
                status = "Confirmed";
                break;
            case 3:
                status = "Preparing";
                break;
            case 4:
                status = "PreparedToServed";
                break;
            case 5:
                status = "Served";
                break;
            case 6:
                status = "Paid";
                break;
            default:
                status = "All";
                break;
        }
        var millis = Long.parseLong(order.getOrderNumber());
        var orderTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(millis),
                ZoneOffset.UTC
        );
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .amount(order.getAmount())
                .orderStatus(status)
                .orderTime(orderTime)
                .table(tableResponse)
                .items(orderItemResponses)
                .build();
    }

    protected OrderItemResponse createOrderItemResponse(OrderItem orderItem) {
        var foodResponse = foodService.createFoodResponse(orderItem.getFood());
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .food(foodResponse)
                .build();
    }

    protected Order createOrder(OrderRequest orderRequest) {
        var table = tableRepository.findById(orderRequest.getTableId()).orElse(null);
        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest orderItemRequest: orderRequest.getItems()) {
            var orderItem = createOrderItem(orderItemRequest);
            orderItems.add(orderItem);
        }
        return Order.builder()
                .orderNumber(orderRequest.getOrderNumber())
                .amount(orderRequest.getAmount())
                .orderStatus(1)
                .table(table)
                .items(orderItems)
                .build();
    }

    protected OrderItem createOrderItem(OrderItemRequest orderItemRequest) {
        var food = foodRepository.findById(orderItemRequest.getFoodId()).orElse(null);
        return OrderItem.builder()
                .quantity(orderItemRequest.getQuantity())
                .unitPrice(orderItemRequest.getUnitPrice())
                .totalPrice(orderItemRequest.getTotalPrice())
                .food(food)
                .build();
    }
}
