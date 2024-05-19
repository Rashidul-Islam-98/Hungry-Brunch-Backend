package com.bss.restaurant.controller;

import com.bss.restaurant.dto.request.EmployeeRequest;
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
import java.util.UUID;

@Tag(name = "Employee", description = "Employee API endpoints")
public interface EmployeeController {

    @Operation(
            summary = "Get all employees with pagination",
            description = """
                    all employees will be shown with pagination.
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
                    @ApiResponse(responseCode = "404", description = "User not found", content = {
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
    ResponseEntity<PaginationResponse<EmployeeResponse>> getEmployees(@RequestParam(defaultValue = "") String search,
                                                                             @RequestParam(defaultValue = "1") Integer pageNumber,
                                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                                             @RequestParam(defaultValue = "designation") String sort);

    @Operation(
            summary = "Get all employees name and id",
            description = """
                    Only name and id will be provide of all employees.
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
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = {
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
    ResponseEntity<RestaurantListResponse<EmployeeShortResponse>> getEmployeeNames();

    @Operation(
            summary = "Get a employee",
            description = """
                    All information related to employee will be given
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
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = {
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
    ResponseEntity<EmployeeResponse> getEmployee(@PathVariable UUID id);

    @Operation(
            summary = "Save an employee",
            description = """
                    Provide all information related to employee
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee Created Successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = {
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
    ResponseEntity<RestaurantBaseResponse> createEmployee(@RequestBody EmployeeRequest employee);

    @Operation(
            summary = "Update an employee",
            description = """
                    Provide required information that are needed to update
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee Designation Updated Successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = {
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
    ResponseEntity<RestaurantBaseResponse> updateEmployee(@PathVariable UUID id, @RequestBody EmployeeRequest employee);

    @Operation(
            summary = "Delete an employee",
            description = """
                    Provide the id to delete an employee
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee Deleted SuccessFully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Not authorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = {
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
    ResponseEntity<RestaurantBaseResponse> deleteEmployee(@PathVariable  UUID id) throws IOException;
}
