package com.bss.restaurant.controller;

import com.bss.restaurant.dto.request.FoodRequest;
import com.bss.restaurant.dto.response.*;
import com.bss.restaurant.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/food/")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping("datatable")
    public ResponseEntity<PaginationResponse<FoodResponse>> getFoods(@RequestParam(required = false) String search,
                                                       @RequestParam(defaultValue = "1") Integer pageNumber,
                                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                                                     @RequestParam(defaultValue = "name") String sort) {
        if (search != null && !search.isEmpty()) {
             return ResponseEntity.ok(foodService.searchFoods(search, pageNumber, pageSize, sort));
        } else {
             return ResponseEntity.ok(foodService.getFoods(pageNumber, pageSize, sort));
        }
    }

    @GetMapping("get")
    public ResponseEntity<List<FoodShortResponse>> getFoodsName() {
        return ResponseEntity.ok(foodService.getFoodName());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<FoodResponse> getFood(@PathVariable long id) {
        return ResponseEntity.ok(foodService.getFood(id).orElseThrow(null));
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createFood(@RequestBody FoodRequest food) {
        foodService.saveFood(food);
        return new ResponseEntity<>(MessageResponse.builder().message("Food Created Successfully.").build(), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateFood(@PathVariable long id, @RequestBody FoodRequest food) throws IOException {
        foodService.updateFood(id, food);
        return ResponseEntity.ok(MessageResponse.builder().message("Food Updated Successfully.").build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable long id) throws IOException {
        foodService.deleteFood(id);
        return ResponseEntity.ok(MessageResponse.builder().message("Food Deleted SuccessFully").build());
    }
}
