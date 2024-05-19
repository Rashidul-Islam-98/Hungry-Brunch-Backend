package com.bss.restaurant.controller;

import com.bss.restaurant.dto.request.OrderRequest;
import com.bss.restaurant.dto.request.OrderUpdateStatusRequest;
import com.bss.restaurant.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Order", description = "Order related api end points")
public interface OrderController {

    @Operation(
            summary = "Get all Orders with pagination",
            description = """
                    all Orders will be shown with pagination.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Technical error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "JWT")
            }
    )
    @GetMapping("datatable")
    ResponseEntity<PaginationResponse<OrderResponse>> getOrders(@RequestParam(defaultValue = "0") int search,
                                                                @RequestParam(defaultValue = "1") int pageNumber,
                                                                @RequestParam(defaultValue = "10") int pageSize,
                                                                @RequestParam(defaultValue = "orderNumber") String sort);

    @Operation(
            summary = "Get all orders short response",
            description = """
                    Only order number and id will be provide of all orders.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantListResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Technical error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "JWT")
            }
    )
    @GetMapping("get")
    ResponseEntity<List<OrderShortResponse>> getOrderNumbers();

    @Operation(
            summary = "Get a order",
            description = """
                    All information related to order will be given
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Technical error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "JWT")
            }
    )
    @GetMapping("get/{id}")
    ResponseEntity<OrderResponse> getOrder(@PathVariable UUID id);

    @Operation(
            summary = "Save an order",
            description = """
                    Provide all information related to order
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Technical error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "JWT")
            }
    )
    @PostMapping("create")
    ResponseEntity<RestaurantBaseResponse> createOrder(@RequestBody OrderRequest orderRequest);

    @Operation(
            summary = "Update an order",
            description = """
                    Provide required information that are needed to update
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order updated successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Technical error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "JWT")
            }
    )
    @PutMapping("update/{id}")
    ResponseEntity<RestaurantBaseResponse> updateOrder(@PathVariable UUID id, @RequestBody OrderRequest orderRequest);

    @Operation(
            summary = "Update order status",
            description = """
                    Provide required information that are needed to update
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order status updated successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Technical error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "JWT")
            }
    )
    @PutMapping("update-status/{id}")
    ResponseEntity<RestaurantBaseResponse> updateOrderStatus(@PathVariable UUID id, @RequestBody OrderUpdateStatusRequest orderUpdateStatusRequest);

    @Operation(
            summary = "Delete an order",
            description = """
                    Provide the id to delete an order
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order deleted successFully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Technical error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "JWT")
            }
    )
    @DeleteMapping("delete/{id}")
    ResponseEntity<RestaurantBaseResponse> deleteOrder(@PathVariable UUID id);
}
