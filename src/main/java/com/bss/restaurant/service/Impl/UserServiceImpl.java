package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.UserRepository;
import com.bss.restaurant.dto.request.SetPasswordRequest;
import com.bss.restaurant.dto.response.UserResponse;
import com.bss.restaurant.entity.User;
import com.bss.restaurant.exception.RestaurantBadRequestException;
import com.bss.restaurant.exception.RestaurantNotFoundException;
import com.bss.restaurant.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        log.info("Retrieving Artist by Username: {}", username);
        return userRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new RestaurantNotFoundException("User not found"));
    }

    @Override
    public User getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            var username = userDetails.getUsername();
            return getUserByUsername(username);
        }
        throw new RestaurantBadRequestException("Authentication principal is not a UserDetails");
    }

    @Override
    public UserResponse getUser(UUID id) {
        var user = userRepository.findById(id).orElseThrow(() ->
                new RestaurantNotFoundException("User not found")
        );
        return createUserResponse(user);
    }

    @Override
    public void createUsernameAndPassword(SetPasswordRequest request) {
        var updateUser = getAuthenticatedUser();
        if(request.getPassword().equals(request.getConfirmPassword())) {
            updateUser.setPassword(passwordEncoder.encode(request.getPassword()));
            updateUser.setForceChangePassword(false);
            userRepository.save(updateUser);
        } else {
            throw new RestaurantBadRequestException("Password and confirm password doesn't match");
        }
    }

    private UserResponse createUserResponse(User user) {
        String gender;
        switch (user.getGenderId()){
            case 1:
                gender = "Female";
                break;
            case 2:
                gender = "Other";
                break;
            default:
                gender = "Male";
                break;
        }
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .fullName(user.getFirstName()+" "+user.getLastName())
                .fatherName(user.getFatherName())
                .motherName(user.getMotherName())
                .spouseName(user.getSpouseName())
                .email(user.getEmail())
                .image(user.getImageUrl())
                .dob(user.getDob())
                .gender(gender)
                .phoneNumber(user.getPhoneNumber())
                .nid(user.getNid())
                .build();
    }
}
