package com.bss.restaurant.controller;

import com.bss.restaurant.dto.request.LoginRequest;
import com.bss.restaurant.dto.request.RegisterRequest;
import com.bss.restaurant.dto.response.RestaurantBadRequestErrorResponse;
import com.bss.restaurant.dto.response.LoginResponse;
import com.bss.restaurant.dto.response.RestaurantBaseResponse;
import com.bss.restaurant.dto.response.RestaurantErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name ="Authentication", description = "Authentication public API endpoints.")
public interface AuthController {

    @Operation(
            description = """
                    Amin can only register admin and employee. Default Registration is user. After Registration success message will be provide.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registration successful", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBaseResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Functional errors", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBadRequestErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Object not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Technical error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    })
            }
    )
    @PostMapping("/register")
    ResponseEntity<RestaurantBaseResponse> register(@RequestBody RegisterRequest registerRequest);

    @Operation(
            description = """
                    Login with username and password, and get jwt and refresh token. If remember me is enabled, jwt and refresh token will be returned.
                    If remember me is disabled only access token will be returned.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Functional errors", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBadRequestErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Object not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Technical error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantErrorResponse.class))
                    })
            }
    )
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest);
}
