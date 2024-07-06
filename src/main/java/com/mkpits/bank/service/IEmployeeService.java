package com.mkpits.bank.service;

import com.mkpits.bank.dto.response.EmployeeAddressResponse;
import com.mkpits.bank.dto.response.EmployeeCredentialResponse;
import com.mkpits.bank.dto.response.EmployeeResponse;

import java.util.List;

public interface IEmployeeService {

    public List<EmployeeResponse> getAllEmployees();

    public  List<EmployeeCredentialResponse> getAllEmployeesCredentials();

}
