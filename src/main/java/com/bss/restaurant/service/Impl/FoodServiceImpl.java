package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.FoodRepository;
import com.bss.restaurant.dto.request.FoodRequest;
import com.bss.restaurant.dto.response.*;
import com.bss.restaurant.entity.Food;
import com.bss.restaurant.exception.RestaurantBadRequestException;
import com.bss.restaurant.exception.RestaurantNotFoundException;
import com.bss.restaurant.projection.FoodNameAndIdProjection;
import com.bss.restaurant.service.FoodService;
import com.bss.restaurant.util.CreatePaginationHelper;
import com.bss.restaurant.util.ImageUploader;
import com.bss.restaurant.util.PaginationBuilder;
import com.bss.restaurant.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public PaginationResponse getFoods(String query, int pageNumber, int pageSize, String sort) {
        var pageRequest = paginationUtil.createPage(pageNumber, pageSize, sort);
        Page<Food> pagingFood ;
        if(query == null || query.equals("")) {
            pagingFood = foodRepository.findAll(pageRequest);
        } else {
            System.out.println("searching food.");
            pagingFood = foodRepository.findByNameContainingIgnoreCase(query, pageRequest);
        }
        var foods = pagingFood.getContent();
        List<FoodResponse> data = new ArrayList<>();
        for(Food food: foods) {
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
        for (FoodNameAndIdProjection result : results) {

            var foodShortResponse = FoodShortResponse.builder()
                    .id(result.getId())
                    .name(result.getName())
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
            throw new RestaurantBadRequestException("Food Already exist");
        }
        var imageUrl = imageUploader.uploadImage(food.getBase64(),food.getImage(), FOOD);
        var saveFood = createFood(food);
        saveFood.setImageUrl(imageUrl);
        foodRepository.save(saveFood);
    }

    @Override
    public void updateFood(long foodId, FoodRequest food) throws IOException {
        var updateFood = foodRepository.findById(foodId).orElseThrow(()->
                new RestaurantNotFoundException("Food not found")
                );
        String imageUrl = null;
        if(updateFood != null && !food.getImage().equals(updateFood.getImage())) {
            imageUploader.deleteImage(FOOD, updateFood.getImage());
            imageUrl = imageUploader.uploadImage(food.getBase64(),food.getImage(), FOOD);
        }
        updateFood.setName(food.getName());
        updateFood.setDescription(food.getDescription());
        updateFood.setPrice(food.getPrice());
        updateFood.setDiscountType(food.getDiscountType());
        updateFood.setDiscount(food.getDiscount());
        updateFood.setDiscountPrice(food.getDiscountPrice());
        if(imageUrl != null) {
            updateFood.setImage(food.getImage());
            updateFood.setImageUrl(imageUrl);
        }
        foodRepository.save(updateFood);
    }

    @Override
    public void deleteFood(long foodId) throws IOException {
        var food = foodRepository.findById(foodId).orElseThrow(()-> new RestaurantNotFoundException("Food doesn't Exist"));
        imageUploader.deleteImage(FOOD,food.getImage());
        foodRepository.delete(food);
    }

    public FoodResponse createFoodResponse(Food food) {
        String foodDiscountType;
        switch (food.getDiscountType()) {
            case 1:
                foodDiscountType = "Flat";
                break;
            case 2:
                foodDiscountType = "Percentage";
                break;
            default:
                foodDiscountType = "No Discount";
                break;
        }
        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .description(food.getDescription())
                .price(food.getPrice())
                .discountType(foodDiscountType)
                .discount(food.getDiscount())
                .discountPrice(food.getDiscountPrice())
                .image(food.getImageUrl())
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
