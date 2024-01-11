package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.UserRepository;
import com.bss.restaurant.dto.internal.Role;
import com.bss.restaurant.dto.request.AuthRegisterRequest;
import com.bss.restaurant.dto.request.AuthRequest;
import com.bss.restaurant.dto.response.AuthResponse;
import com.bss.restaurant.dto.response.UserResponse;
import com.bss.restaurant.entity.User;
import com.bss.restaurant.service.AuthService;
import com.bss.restaurant.util.ImageUploader;
import com.bss.restaurant.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public void saveAdmin(AuthRegisterRequest authRegisterRequest) {
        var foundUser = userRepository.findByUsername(authRegisterRequest.getUsername()).orElse(null);
        if (foundUser != null) {
            throw new ResourceAccessException("User Already Exist");
        }
        imageUploader.uploadImage(authRegisterRequest.getBase64(),authRegisterRequest.getImage(), "user");
        var user = createUser(authRegisterRequest);
        userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        var user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow( ()->
         new UsernameNotFoundException("Wrong credentials")
        );
        String token = this.jwtUtil.generateToken(user);
        var userResponse = createUserResponse(user);
        return AuthResponse.builder()
                .user(userResponse)
                .token(token)
                .build();
    }

    @Override
    public void createUsernameAndPassword(UUID userId, AuthRequest request) {
        var foundUser = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (foundUser != null) {
            throw new ResourceAccessException("Username Already taken");
        }
        var updateUser = userRepository.findById(userId).orElse(null);
        if (updateUser == null) {
            throw new ResourceAccessException("User Doesn't Exist");
        }
        updateUser.setUsername(request.getUsername());
        updateUser.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(updateUser);
    }

    private User createUser(AuthRegisterRequest request) {
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
                .role(Role.ADMIN)
                .nid(request.getNid())
                .provider("Hungry Brunch")
                .build();
    }

    private UserResponse createUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .fullName(user.getFirstName()+" "+user.getLastName())
                .username(user.getUsername())
                .spouseName(user.getSpouseName())
                .fatherName(user.getFatherName())
                .motherName(user.getMotherName())
                .email(user.getEmail())
                .image(user.getImage())
                .phoneNumber(user.getPhoneNumber())
                .dob(user.getDob())
                .gender(getGender(user.getGenderId()))
                .nid(user.getNid())
                .build();
    }

    private String getGender(int genderId) {
        if(genderId == 0) {
            return "Male";
        } else if (genderId == 1) {
            return "Female";
        } else {
            return "Other";
        }
    }
}
