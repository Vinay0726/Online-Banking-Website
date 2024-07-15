package com.mkpits.bank.controller;

import com.mkpits.bank.dto.response.*;
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
    @GetMapping("/admin/employees")
    public String getEmployees(Model model) {
        List<EmployeeResponse> employeeResponseDtoList = employeeService.getAllEmployees();
        List<EmployeeCredentialResponse> employeeCredentialResponses=employeeService.getAllEmployeesCredentials();
        model.addAttribute("employees", employeeResponseDtoList);
        model.addAttribute("employeesCredentials", employeeCredentialResponses);
        return "admin/employees";
    }


}