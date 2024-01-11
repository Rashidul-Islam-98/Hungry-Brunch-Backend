package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.FoodRepository;
import com.bss.restaurant.dto.request.FoodRequest;
import com.bss.restaurant.dto.response.*;
import com.bss.restaurant.entity.Food;
import com.bss.restaurant.exception.InvalidPageSizeException;
import com.bss.restaurant.service.FoodService;
import com.bss.restaurant.util.CreatePaginationHelper;
import com.bss.restaurant.util.ImageUploader;
import com.bss.restaurant.util.PaginationBuilder;
import com.bss.restaurant.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ImageUploader imageUploader;

    @Autowired
    private CreatePaginationHelper<Food> createPaginationHelper;

    @Autowired
    private PaginationBuilder<FoodResponse> paginationBuilder;

    @Autowired
    private PaginationUtil paginationUtil;

    private static final String FOOD = "food";

    @Override
    public PaginationResponse getFoods(int pageNumber, int pageSize, String sort) {
        var pageRequest = paginationUtil.createPage(pageNumber, pageSize, sort);
        var pagingFood = foodRepository.findAll(pageRequest);
        var foods = pagingFood.getContent();
        List<FoodResponse> data = new ArrayList<>();
        for(Food food: foods) {
            var temp = createFoodResponse(food);
            data.add(temp);
        }
        var paginationHelper = createPaginationHelper.paginationHelperCreating(pagingFood, pageNumber, pageSize);
        return paginationBuilder.createPagination(paginationHelper, data);
    }

    public PaginationResponse<FoodResponse> searchFoods(String query, int pageNumber, int pageSize, String sort) {
        int page = Math.max(0, pageNumber);
        if(pageSize<1) {
            throw new InvalidPageSizeException("Page number should be greater than 0");
        }

        var pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.asc(sort)));
        var pagingFood = foodRepository.findByNameContainingIgnoreCase(query, pageRequest);
        var foods = pagingFood.getContent();
        List<FoodResponse> data = new ArrayList<>();
        for (Food food : foods) {
            var temp = createFoodResponse(food);
            data.add(temp);
        }
        var paginationHelper = createPaginationHelper.paginationHelperCreating(pagingFood, pageNumber, pageSize);
        return paginationBuilder.createPagination(paginationHelper, data);
    }

    @Override
    public List<FoodShortResponse> getFoodName() {
        var results = foodRepository.findAllNamesAndIds();
        List<FoodShortResponse> foodShortResponses = new ArrayList<>();
        for (Object[] result : results) {
            long id = (long) result[0];
            String name = (String) result[1];

            var foodShortResponse = FoodShortResponse.builder()
                    .id(id)
                    .name(name)
                    .build();

            foodShortResponses.add(foodShortResponse);
        }
        return foodShortResponses;
    }

    @Override
    public Optional<FoodResponse> getFood(long foodId) {
        var food = foodRepository.findById(foodId).orElse(null);
        var foodResponse = createFoodResponse(food);
        return Optional.of(foodResponse);
    }

    @Override
    public void saveFood(FoodRequest food) {
        var foundFood = foodRepository.findByName(food.getName());
        if(foundFood != null) {
            throw new ResourceAccessException("Food Already exist");
        }
        imageUploader.uploadImage(food.getBase64(),food.getImage(), FOOD);
        var saveFood = createFood(food);
        foodRepository.save(saveFood);
    }

    @Override
    public void updateFood(long foodId, FoodRequest food) throws IOException {
        var updateFood = foodRepository.findById(foodId).orElse(null);
        if(updateFood != null && !food.getImage().equals(updateFood.getImage())) {
            imageUploader.deleteImage(FOOD, updateFood.getImage());
            imageUploader.uploadImage(food.getBase64(),food.getImage(), FOOD);
        }
        updateFood = createFood(food);
        foodRepository.save(updateFood);
    }

    @Override
    public void deleteFood(long foodId) throws IOException {
        var food = foodRepository.findById(foodId).orElseThrow(()-> new ResourceAccessException("Food doesn't Exist"));
        imageUploader.deleteImage(FOOD,food.getImage());
        foodRepository.delete(food);
    }

    public FoodResponse createFoodResponse(Food food) {
        String foodType = food.getDiscountType() == 1 ? "Flat": "Percentage";
        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .description(food.getDescription())
                .price(food.getPrice())
                .discountType(foodType)
                .discount(food.getDiscount())
                .discountPrice(food.getDiscountPrice())
                .image(food.getImage())
                .build();
    }

    public Food createFood(FoodRequest food) {
        return Food.builder()
                .name(food.getName())
                .description(food.getDescription())
                .price(food.getPrice())
                .discountType(food.getDiscountType())
                .discount(food.getDiscount())
                .discountPrice(food.getDiscountPrice())
                .image(food.getImage())
                .build();
    }
}
