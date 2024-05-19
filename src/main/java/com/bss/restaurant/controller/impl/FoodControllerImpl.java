package com.bss.restaurant.controller.impl;

import com.bss.restaurant.controller.FoodController;
import com.bss.restaurant.dto.request.FoodRequest;
import com.bss.restaurant.dto.response.*;
import com.bss.restaurant.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/food/")
public class FoodControllerImpl implements FoodController {
    @Autowired
    private FoodService foodService;

    @Override
    public ResponseEntity<PaginationResponse<FoodResponse>> getFoods(String search, Integer pageNumber, Integer pageSize, String sort) {
        return ResponseEntity.ok(foodService.getFoods(search, pageNumber, pageSize, sort));
    }

    @Override
    public ResponseEntity<List<FoodShortResponse>> getFoodsName() {
        return ResponseEntity.ok(foodService.getFoodName());
    }

    @Override
    public ResponseEntity<FoodResponse> getFood(long id) {
        return ResponseEntity.ok(foodService.getFood(id).orElseThrow(null));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> createFood(FoodRequest food) {
        foodService.saveFood(food);
        return new ResponseEntity<>(RestaurantBaseResponse.builder().message("Food Created Successfully.").build(), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> updateFood(long id, FoodRequest food) throws IOException {
        foodService.updateFood(id, food);
        return ResponseEntity.ok(RestaurantBaseResponse.builder().message("Food Updated Successfully.").build());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> deleteFood(long id) throws IOException {
        foodService.deleteFood(id);
        return ResponseEntity.ok(RestaurantBaseResponse.builder().message("Food Deleted SuccessFully").build());
    }
}
