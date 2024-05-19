package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.EmployeeRepository;
import com.bss.restaurant.dto.internal.Role;
import com.bss.restaurant.dto.request.EmployeeRequest;
import com.bss.restaurant.dto.response.*;
import com.bss.restaurant.entity.Employee;
import com.bss.restaurant.entity.User;
import com.bss.restaurant.exception.RestaurantBadRequestException;
import com.bss.restaurant.exception.RestaurantNotFoundException;
import com.bss.restaurant.service.EmployeeService;
import com.bss.restaurant.util.CreatePaginationHelper;
import com.bss.restaurant.util.ImageUploader;
import com.bss.restaurant.util.PaginationBuilder;
import com.bss.restaurant.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ImageUploader imageUploader;

    @Autowired
    private CreatePaginationHelper<Employee> createPaginationHelper;

    @Autowired
    private PaginationBuilder<EmployeeResponse> paginationBuilder;

    @Autowired
    private PaginationUtil paginationUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String USER = "user";

    public Employee getEmployeeById(UUID employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(()->
                new RestaurantNotFoundException("Employee Doesn't exist")
        );
    }


    public Employee getEmployeeByUserId(UUID userId) {
        return employeeRepository.findByUserId(userId).orElseThrow(()->
                new RestaurantNotFoundException("User Doesn't exist")
        );
    }


    public Employee getEmployeeByUserEmail(String userEmail) {
        return employeeRepository.findByUserEmail(userEmail).orElseThrow(()->
                new RestaurantNotFoundException("User Doesn't exist")
        );
    }

    @Override
    public PaginationResponse getEmployees(String query,int pageNumber, int pageSize, String sort) {
        var pageRequest = paginationUtil.createPage(pageNumber,pageSize, sort);
        Page<Employee> pagingEmployee;
        if(query.equals("")){
            pagingEmployee = employeeRepository.findAll(pageRequest);
        } else {
            pagingEmployee = employeeRepository.findByUserFirstNameContainingIgnoreCase(query, pageRequest);
        }
        var employees = pagingEmployee.getContent();

        List<EmployeeResponse> data = new ArrayList<>();
        employees.forEach(employee -> {
            var temp = createEmployeeResponse(employee);
            data.add(temp);
        });

        var paginationHelper = createPaginationHelper.paginationHelperCreating(pagingEmployee, pageNumber, pageSize);
        return paginationBuilder.createPagination(paginationHelper, data);
    }

    @Override
    public RestaurantListResponse<EmployeeShortResponse> getEmployeesName() {
        var employees = employeeRepository.findAll();
        List<EmployeeShortResponse> employeeNames = new ArrayList<>();

        employees.forEach(employee ->
            employeeNames.add(createEmployeeShortResponse(employee))
        );
        return RestaurantListResponse.<EmployeeShortResponse>builder().data(employeeNames).build();
    }

    @Override
    public EmployeeResponse getEmployee(UUID employeeId) {
        var employee = getEmployeeById(employeeId);
        return createEmployeeResponse(employee);
    }

    @Override
    public void saveEmployee(EmployeeRequest employee) {
        var findEmployee = employeeRepository.findByUserEmail(employee.getEmail()).orElse(null);
        if (findEmployee != null) {
            throw new RestaurantBadRequestException("Employee Already Exist");
        }
        var imageUrl = imageUploader.uploadImage(employee.getBase64(),employee.getImage(), USER);
        var saveEmployee = createEmployee(employee);
        saveEmployee.getUser().setImageUrl(imageUrl);
        employeeRepository.save(saveEmployee);
    }

    @Override
    public void updateEmployee(UUID employeeId, EmployeeRequest employee) {
        var updateEmployee = getEmployeeById(employeeId);
        String imageUrl = null;
        if(updateEmployee != null && !employee.getImage().equals(updateEmployee.getUser().getImage())) {
            imageUploader.deleteImage(USER, updateEmployee.getUser().getImage());
            imageUrl = imageUploader.uploadImage(employee.getBase64(), employee.getImage(), USER);
        }
        updateEmployee.setDesignation(employee.getDesignation());
        updateEmployee.setJoinDate(employee.getJoinDate());
        updateEmployee.getUser().setFirstName(employee.getFirstName());
        updateEmployee.getUser().setMiddleName(employee.getMiddleName());
        updateEmployee.getUser().setLastName(employee.getLastName());
        updateEmployee.getUser().setFatherName(employee.getFatherName());
        updateEmployee.getUser().setMotherName(employee.getMotherName());
        updateEmployee.getUser().setSpouseName(employee.getSpouseName());
        updateEmployee.getUser().setEmail(employee.getEmail());
        updateEmployee.getUser().setDob(employee.getDob());
        updateEmployee.getUser().setPhoneNumber(employee.getPhoneNumber());
        updateEmployee.getUser().setGenderId(employee.getGenderId());
        updateEmployee.getUser().setNid(employee.getNid());
        if(imageUrl != null) {
            updateEmployee.getUser().setImage(employee.getImage());
            updateEmployee.getUser().setImageUrl(imageUrl);
        }
        employeeRepository.save(updateEmployee);
    }
    @Override
    public void deleteEmployee(UUID employeeId) {
        var employee = getEmployeeById(employeeId);
        imageUploader.deleteImage(USER,employee.getUser().getImage());
        employeeRepository.delete(employee);
    }

    private Employee createEmployee(EmployeeRequest employee) {
        var user = User.builder()
                .username(employee.getEmail())
                .password(passwordEncoder.encode("Hungry Brunch"))
                .firstName(employee.getFirstName())
                .middleName(employee.getMiddleName())
                .lastName(employee.getLastName())
                .fatherName(employee.getFatherName())
                .motherName(employee.getMotherName())
                .spouseName(employee.getSpouseName())
                .image(employee.getImage())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .dob(employee.getDob())
                .genderId(employee.getGenderId())
                .nid(employee.getNid())
                .role(Role.EMPLOYEE)
                .provider("Hungry Brunch")
                .forceChangePassword(true)
                .build();
        return Employee.builder()
                .joinDate(employee.getJoinDate())
                .designation(employee.getDesignation())
                .amountSold(0)
                .user(user)
                .build();
    }

    private EmployeeResponse createEmployeeResponse(Employee employee) {
        String gender;
        var user = employee.getUser();
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
        var userResponse = UserResponse.builder()
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
        return EmployeeResponse.builder()
                .id(employee.getId())
                .joinDate(employee.getJoinDate())
                .designation(employee.getDesignation())
                .amountSold(employee.getAmountSold())
                .user(userResponse)
                .build();
    }

    private EmployeeShortResponse createEmployeeShortResponse(Employee employee) {
        return EmployeeShortResponse.builder()
                .id(employee.getId())
                .name(employee.getUser().getFirstName() +" "+employee.getUser().getLastName())
                .build();
    }
}
