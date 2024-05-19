package com.bss.restaurant.controller;

import com.bss.restaurant.dto.request.EmployeeTableRequest;
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

@Tag(name = "EmployeeTable", description = "Employee and Table related api endpoints")
public interface EmployeeTableController {

    @Operation(
            summary = "Get all employees and tables with pagination",
            description = """
                    all employees and tables will be shown with pagination.
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
    ResponseEntity<PaginationResponse<EmployeeTableResponse>> getEmployeeTables(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                                      @RequestParam(defaultValue = "10") Integer pageSize);

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
    @GetMapping("get")
    ResponseEntity<RestaurantListResponse<EmployeeTableResponse>> getOnlyEmployeeTables();

    @Operation(
            summary = "Get a employee and table",
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
    @GetMapping("get/{id}")
    ResponseEntity<EmployeeTableResponse> getEmployeeTable(@PathVariable long id);

    @Operation(
            summary = "Save an employee to table",
            description = """
                    Provide all information related to employee to save in table
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee saved successfully to table", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
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
    @PostMapping("create")
    ResponseEntity<RestaurantBaseResponse> saveEmployeeTable(@RequestBody EmployeeTableRequest employeeTableRequest);

    @Operation(
            summary = "Save an employees to table",
            description = """
                    Provide all information related to employees to save in table
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employees saved successfully to table", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
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
    @PostMapping("create-range")
    ResponseEntity<RestaurantBaseResponse> saveEmployeeTables(@RequestBody List<EmployeeTableRequest> employeeTables);

    @Operation(
            summary = "Update an employee to table",
            description = """
                    Provide required information that are needed to update
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee updated successfully to table", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
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
    @PutMapping("update/{id}")
    ResponseEntity<RestaurantBaseResponse> updateEmployeeTable(@PathVariable long id, @RequestBody EmployeeTableRequest employeeTable) throws Exception;

    @Operation(
            summary = "Delete an employee from table",
            description = """
                    Provide the id to delete an employee from table
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee deleted successFully from table", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
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
    @DeleteMapping("delete/{id}")
    ResponseEntity<RestaurantBaseResponse> deleteEmployeeTable(@PathVariable long id) throws IOException;
}
