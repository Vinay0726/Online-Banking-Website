package com.mkpits.bank.service.impl;

import com.mkpits.bank.dto.request.EmployeeRequest;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    //Update Employee Details
    @Override
    public void updateEmployeeData(Long employeeId,EmployeeRequest employeeRequest) {

        Optional<Employee> employeeOptional = employeeRepository.findById(Math.toIntExact(employeeId));
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setFirstName(employeeRequest.getFirstName());
            employee.setMiddleName(employeeRequest.getMiddleName());
            employee.setLastName(employeeRequest.getLastName());
            employee.setMobileNumber(employeeRequest.getMobileNumber());
            employee.setDateOfBirth(LocalDate.parse(employeeRequest.getDateOfBirth()));
            employee.setEmail(employeeRequest.getEmail());
            employee.setGender(employeeRequest.getGender());
            employee.setAdhaarCard(employeeRequest.getAdhaarCard());
            employee.setUpdatedAt(LocalDateTime.now());
            employee.setUpdatedBy(1);
            employeeRepository.save(employee);

            EmployeeCredential employeeCredential = employeeCredentialRepository.findByEmployeeId(Math.toIntExact(employeeId))
                    .orElse(new EmployeeCredential());
            employeeCredential.setUserName(employeeRequest.getUserName());
            employeeCredential.setPassword(employeeRequest.getPassword());
            employeeCredential.setUpdatedAt(LocalDateTime.now());
            employeeCredential.setUpdatedBy(1);
            employeeCredentialRepository.save(employeeCredential);

            EmployeeAddress employeeAddress = employeeAddressRepository.findByEmployeeId(Math.toIntExact(employeeId))
                    .orElse(new EmployeeAddress());
            employeeAddress.setAddress(employeeRequest.getAddress());
            employeeAddress.setState(employeeRequest.getState());
            employeeAddress.setCity(employeeRequest.getCity());
            employeeAddress.setPinCode(employeeRequest.getPinCode());
            employeeAddress.setUpdatedAt(LocalDateTime.now());
            employeeAddress.setUpdatedBy(1);
            employeeAddressRepository.save(employeeAddress);
        }

    }
        @Override
    public EmployeeRequest registerEmployees(EmployeeRequest employeeRequest) {
       Employee employee=new Employee();
       employee.setFirstName(employeeRequest.getFirstName());
       employee.setMiddleName(employeeRequest.getMiddleName());
       employee.setLastName(employeeRequest.getLastName());
       employee.setMobileNumber(employeeRequest.getMobileNumber());
       employee.setDateOfBirth(LocalDate.parse(employeeRequest.getDateOfBirth()));
       employee.setEmail(employeeRequest.getEmail());
       employee.setGender(employeeRequest.getGender());
       employee.setAdhaarCard(employeeRequest.getAdhaarCard());
        System.out.println(employeeRequest.getFirstName());
        employee.setCreatedAt(LocalDateTime.now());
        employee.setCreatedBy(1);
        employee.setUpdatedAt(LocalDateTime.now());
        employee.setUpdatedBy(1);
       employeeRepository.save(employee);

       EmployeeCredential employeeCredential=new EmployeeCredential();
       employeeCredential.setUserName(employeeRequest.getUserName());
       employeeCredential.setPassword(employeeRequest.getPassword());
        String uuid = UUID.randomUUID().toString();
       employeeCredential.setEmployeeId(employee.getId());
       employeeCredential.setPasswordSalt(uuid);
       employeeCredential.setLoginDateTime(LocalDateTime.now());
        employeeCredential.setCreatedAt(LocalDateTime.now());
        employeeCredential.setCreatedBy(1);
        employeeCredential.setUpdatedAt(LocalDateTime.now());
        employeeCredential.setUpdatedBy(1);
       employeeCredentialRepository.save(employeeCredential);

       EmployeeAddress employeeAddress=new EmployeeAddress();
       employeeAddress.setAddress(employeeRequest.getAddress());
       employeeAddress.setState(employeeRequest.getState());
       employeeAddress.setCity(employeeRequest.getCity());
       employeeAddress.setPinCode(employeeRequest.getPinCode());
       employeeAddress.setEmployeeId(employee.getId());
        employeeAddress.setCreatedAt(LocalDateTime.now());
        employeeAddress.setCreatedBy(1);
        employeeAddress.setUpdatedAt(LocalDateTime.now());
        employeeAddress.setUpdatedBy(1);
       employeeAddressRepository.save(employeeAddress);
       return employeeRequest;
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(Math.toIntExact(id));
    }

    private EmployeeCredentialResponse convertEmployeeCredentialModelToEmployeeCredentialDtoGetResponse(EmployeeCredential employeeCredential) {
EmployeeCredentialResponse employeeCredentialResponse=new EmployeeCredentialResponse();
        Employee employee= employeeCredential.getEmployee();
employeeCredentialResponse=EmployeeCredentialResponse.builder().fullName(employee.getFirstName()+' '+employee.getLastName()).userName(employeeCredential.getUserName())
        .password(employeeCredential.getPassword()).createdAt(employeeCredential.getCreatedAt()).updatedAt(employeeCredential.getUpdatedAt()).build();
return employeeCredentialResponse;
    }
}
