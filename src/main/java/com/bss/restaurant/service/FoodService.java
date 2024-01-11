package com.bss.restaurant.service;

import com.bss.restaurant.dto.request.FoodRequest;
import com.bss.restaurant.dto.response.FoodResponse;
import com.bss.restaurant.dto.response.FoodShortResponse;
import com.bss.restaurant.dto.response.PaginationResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FoodService {
    PaginationResponse<FoodResponse> getFoods(int pageNumber, int pageSize, String sort);

    PaginationResponse<FoodResponse> searchFoods(String query, int pageNumber, int pageSize, String sort);
    List<FoodShortResponse> getFoodName();
    Optional<FoodResponse> getFood(long foodId);
    void saveFood(FoodRequest food);
    void updateFood(long foodId, FoodRequest food) throws IOException;
    void deleteFood(long foodId) throws IOException;
}
