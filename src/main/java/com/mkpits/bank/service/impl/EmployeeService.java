package com.mkpits.bank.service.impl;

import com.mkpits.bank.dto.response.EmployeeAddressResponse;
import com.mkpits.bank.dto.response.EmployeeCredentialResponse;
import com.mkpits.bank.dto.response.EmployeeResponse;
import com.mkpits.bank.model.Employee;
import com.mkpits.bank.model.EmployeeAddress;
import com.mkpits.bank.model.EmployeeCredential;
import com.mkpits.bank.repository.EmployeeAddressRepository;
import com.mkpits.bank.repository.EmployeeCredentialRepository;
import com.mkpits.bank.repository.EmployeeRepository;
import com.mkpits.bank.service.IEmployeeService;
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
    @Autowired
    EmployeeAddressRepository employeeAddressRepository;
    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();

        for (Employee employee : employeeList) {
            EmployeeResponse employeeResponse = convertEmployeeModelToEmployeeDtoGetResponse(employee);
            // Assuming the employee has only one address
            if (employee.getEmployeeAddress() != null && !employee.getEmployeeAddress().isEmpty()) {
                EmployeeAddress address = employee.getEmployeeAddress().get(0); // Get the first address
                employeeResponse.setAddress(address.getAddress());
                employeeResponse.setCity(address.getCity());
                employeeResponse.setState(address.getState());
                employeeResponse.setPinCode(address.getPinCode());
            }
            employeeResponseList.add(employeeResponse);
        }

        return employeeResponseList;
    }

    private EmployeeResponse convertEmployeeModelToEmployeeDtoGetResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .middleName(employee.getMiddleName())
                .lastName(employee.getLastName())
                .mobileNumber(employee.getMobileNumber())
                .email(employee.getEmail())
                .dateOfBirth(employee.getDateOfBirth())
                .aadhaarCard(employee.getAdhaarCard())
                .createdAt(employee.getCreatedAt())
                .build();
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
