package com.mkpits.bank.service.impl;

import com.mkpits.bank.dto.response.EmployeeCredentialResponse;
import com.mkpits.bank.dto.response.EmployeeResponse;
import com.mkpits.bank.dto.response.UserResponse;
import com.mkpits.bank.model.Employee;
import com.mkpits.bank.model.EmployeeCredential;
import com.mkpits.bank.model.User;
import com.mkpits.bank.repository.EmployeeCredentialRepository;
import com.mkpits.bank.repository.EmployeeRepository;
import com.mkpits.bank.service.IEmployeeService;
import com.mkpits.bank.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeCredentialRepository employeeCredentialRepository;
    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employeeList = (List<Employee>) employeeRepository.findAll();

        List<EmployeeResponse> employeeRequestDtoList = new ArrayList<>();

        for(Employee employee : employeeList){
            EmployeeResponse employeeGetResponseDto = convertEmployeeModelToEmployeeDtoGetResponse(employee);
            employeeGetResponseDto.setId(employee.getId());
                    employeeRequestDtoList.add(employeeGetResponseDto);
        }
        return employeeRequestDtoList;
    }


    private EmployeeResponse convertEmployeeModelToEmployeeDtoGetResponse(Employee employee) {
    EmployeeResponse employeeResponse=new EmployeeResponse();
    employeeResponse=EmployeeResponse.builder().firstName(employee.getFirstName()).middleName(employee.getMiddleName())
            .lastName(employee.getLastName()).mobileNumber(employee.getMobileNumber())
            .email(employee.getEmail()).dateOfBirth(employee.getDateOfBirth()).aadhaarCard(employee.getAdhaarCard())
            .createdAt(employee.getCreatedAt()).build();
       return employeeResponse;
    }

    @Override
    public List<EmployeeCredentialResponse> getAllEmployeesCredentials() {
        List<EmployeeCredential> employeeCredentialList = (List<EmployeeCredential>) employeeCredentialRepository.findAll();

        List<EmployeeCredentialResponse> employeeCredentialRequestDtoList = new ArrayList<>();

        for(EmployeeCredential employeeCredential : employeeCredentialList){
            EmployeeCredentialResponse employeeCredentialGetResponseDto = convertEmployeeCredentialModelToEmployeeCredentialDtoGetResponse(employeeCredential);
            employeeCredentialGetResponseDto.setId(employeeCredential.getId());
            employeeCredentialRequestDtoList.add(employeeCredentialGetResponseDto);
        }
        return employeeCredentialRequestDtoList;
    }

    private EmployeeCredentialResponse convertEmployeeCredentialModelToEmployeeCredentialDtoGetResponse(EmployeeCredential employeeCredential) {
EmployeeCredentialResponse employeeCredentialResponse=new EmployeeCredentialResponse();
        Employee employee= employeeCredential.getEmployee();
employeeCredentialResponse=EmployeeCredentialResponse.builder().fullName(employee.getFirstName()+' '+employee.getLastName()).userName(employeeCredential.getUserName())
        .password(employeeCredential.getPassword()).createdAt(employeeCredential.getCreatedAt()).updatedAt(employeeCredential.getUpdatedAt()).build();
return employeeCredentialResponse;
    }
}
