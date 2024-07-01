package com.mkpits.bank.controller;

import com.mkpits.bank.dto.response.EmployeeCredentialResponse;
import com.mkpits.bank.dto.response.EmployeeResponse;
import com.mkpits.bank.dto.response.UserCredentialResponse;
import com.mkpits.bank.dto.response.UserResponse;
import com.mkpits.bank.model.Employee;
import com.mkpits.bank.service.IEmployeeService;
import com.mkpits.bank.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    IEmployeeService employeeService;
    @GetMapping("/employees")
    public String getUsers(Model model) {
        List<EmployeeResponse> employeeResponseDtoList = employeeService.getAllEmployees();
        List<EmployeeCredentialResponse> employeeCredentialResponses=employeeService.getAllEmployeesCredentials();
        model.addAttribute("employees", employeeResponseDtoList);
        model.addAttribute("employeesCredentials", employeeCredentialResponses);
        return "employees";
    }
}