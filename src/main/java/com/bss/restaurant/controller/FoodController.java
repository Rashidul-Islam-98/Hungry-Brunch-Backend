package com.bss.restaurant.controller;

import com.bss.restaurant.dto.request.FoodRequest;
import com.bss.restaurant.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "Food", description = "Food related api endpoints")
public interface FoodController {

    @Operation(
            summary = "Get all Foods with pagination",
            description = """
                    all foods will be shown with pagination.
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
                    @ApiResponse(responseCode = "404", description = "Food not found", content = {
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
    ResponseEntity<PaginationResponse<FoodResponse>> getFoods(@RequestParam(defaultValue = "") String search,
                                                                      @RequestParam(defaultValue = "1") Integer pageNumber,
                                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                                      @RequestParam(defaultValue = "id") String sort);

    @Operation(
            summary = "Get all foods and id",
            description = """
                    Only name and id will be provide of all foods.
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
                    @ApiResponse(responseCode = "404", description = "Food not found", content = {
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
    ResponseEntity<List<FoodShortResponse>> getFoodsName();

    @Operation(
            summary = "Get a food",
            description = """
                    All information related to food will be given
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
                    @ApiResponse(responseCode = "404", description = "Food not found", content = {
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
    ResponseEntity<FoodResponse> getFood(@PathVariable long id);

    @Operation(
            summary = "Save a food",
            description = """
                    Provide all information related to food
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "Food created successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Food not found", content = {
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
    ResponseEntity<RestaurantBaseResponse> createFood(@RequestBody FoodRequest food);

    @Operation(
            summary = "Update an food",
            description = """
                    Provide required information that are needed to update
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Food updated successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Food not found", content = {
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
    ResponseEntity<RestaurantBaseResponse> updateFood(@PathVariable long id, @RequestBody FoodRequest food) throws IOException;

    @Operation(
            summary = "Delete a food",
            description = """
                    Provide the id to delete a food
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Food deleted successFully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Food not found", content = {
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
    public ResponseEntity<RestaurantBaseResponse> deleteFood(@PathVariable long id) throws IOException;
}
