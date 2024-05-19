package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.UserRepository;
import com.bss.restaurant.dto.internal.Role;
import com.bss.restaurant.dto.request.RegisterRequest;
import com.bss.restaurant.dto.request.LoginRequest;
import com.bss.restaurant.dto.response.LoginResponse;
import com.bss.restaurant.dto.response.UserResponse;
import com.bss.restaurant.entity.User;
import com.bss.restaurant.exception.RestaurantBadRequestException;
import com.bss.restaurant.exception.RestaurantNotFoundException;
import com.bss.restaurant.security.UserDetailsImpl;
import com.bss.restaurant.service.AuthService;
import com.bss.restaurant.util.ImageUploader;
import com.bss.restaurant.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ImageUploader imageUploader;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void saveUser(RegisterRequest authRegisterRequest) {
        var foundUser = userRepository.findByUsername(authRegisterRequest.getUsername()).orElse(null);
        if (foundUser != null) {
            throw new RestaurantBadRequestException("User Already Exist");
        }
        var imageUrl = imageUploader.uploadImage(authRegisterRequest.getBase64(),authRegisterRequest.getImage(), "user");
        var user = createUser(authRegisterRequest);
        user.setImageUrl(imageUrl);
        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest authRequest) {
        var user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow( ()->
         new RestaurantNotFoundException("Wrong credentials")
        );
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = this.jwtUtil.generateToken(new UserDetailsImpl(user), user.getId());
        return LoginResponse.builder()
                .token(token)
                .forceChangePassword(user.isForceChangePassword())
                .build();
    }

    private User createUser(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .spouseName(request.getSpouseName())
                .fatherName(request.getFatherName())
                .motherName(request.getMotherName())
                .email(request.getEmail())
                .image(request.getImage())
                .phoneNumber(request.getPhoneNumber())
                .dob(request.getDob())
                .genderId(request.getGenderId())
                .role(Role.CUSTOMER)
                .nid(request.getNid())
                .forceChangePassword(false)
                .provider("Hungry Brunch")
                .build();
    }
}
