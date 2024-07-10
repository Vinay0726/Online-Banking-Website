package com.mkpits.bank.service;

import com.mkpits.bank.dto.request.EmployeeRequest;
import com.mkpits.bank.dto.response.EmployeeAddressResponse;
import com.mkpits.bank.dto.response.EmployeeCredentialResponse;
import com.mkpits.bank.dto.response.EmployeeResponse;

import java.util.List;

public interface IEmployeeService {

    public List<EmployeeResponse> getAllEmployees();

    public  List<EmployeeCredentialResponse> getAllEmployeesCredentials();


    //for register Employee
   public EmployeeRequest registerEmployees(EmployeeRequest employeeRequest);

   //delete employee by id
   public void deleteEmployeeById(Long id);

    void updateEmployeeData(Long employeeId, EmployeeRequest employeeRequest);
//Update Employee

}
