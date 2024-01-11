package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.EmployeeRepository;
import com.bss.restaurant.dto.internal.Role;
import com.bss.restaurant.dto.request.EmployeeRequest;
import com.bss.restaurant.dto.response.EmployeeResponse;
import com.bss.restaurant.dto.response.EmployeeShortResponse;
import com.bss.restaurant.dto.response.PaginationResponse;
import com.bss.restaurant.dto.response.UserResponse;
import com.bss.restaurant.entity.Employee;
import com.bss.restaurant.entity.User;
import com.bss.restaurant.service.EmployeeService;
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

    private static final String USER = "user";

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
    public List<EmployeeShortResponse> getEmployeesName() {
        var employees = employeeRepository.findAll();
        List<EmployeeShortResponse> employeeNames = new ArrayList<>();

        employees.forEach(employee -> {
            String fullName = employee.getUser().getFirstName() +
                    employee.getUser().getMiddleName() +
                    employee.getUser().getLastName();

            var temp = EmployeeShortResponse.builder()
                    .id(employee.getId())
                    .name(fullName)
                    .build();

            employeeNames.add(temp);
        });
        return employeeNames;
    }

    @Override
    public Optional<EmployeeResponse> getEmployee(UUID employeeId) {
        var employee = employeeRepository.findById(employeeId).orElse(null);
        var employeeResponse = createEmployeeResponse(employee);
        return Optional.of(employeeResponse);
    }

    @Override
    public void saveEmployee(EmployeeRequest employee) {
        var findEmployee = employeeRepository.findByUserEmail(employee.getEmail());
        if(findEmployee != null) {
            throw new ResourceAccessException("Employee Already exist");
        }
        imageUploader.uploadImage(employee.getBase64(),employee.getImage(), USER);
        var saveEmployee = createEmployee(employee);
        employeeRepository.save(saveEmployee);
    }

    @Override
    public void updateEmployee(UUID employeeId, String designation) {
        var updateEmployee = employeeRepository.findById(employeeId).orElse(null);
        if(updateEmployee != null) {
            updateEmployee.setDesignation(designation);
            employeeRepository.save(updateEmployee);
        }
    }
    @Override
    public void deleteEmployee(UUID employeeId) throws IOException {
        var employee = employeeRepository.findById(employeeId).orElseThrow(()->
                new ResourceAccessException("Employee Doesn't Exist")
                );
        imageUploader.deleteImage(USER,employee.getUser().getImage());
        employeeRepository.delete(employee);
    }

    protected Employee createEmployee(EmployeeRequest employee) {
        var user = User.builder()
                .username(null)
                .password(null)
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
                .build();
        return Employee.builder()
                .joinDate(employee.getJoinDate())
                .designation(employee.getDesignation())
                .amountSold(0)
                .user(user)
                .build();
    }

    protected EmployeeResponse createEmployeeResponse(Employee employee) {
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
                .image(user.getImage())
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
}
