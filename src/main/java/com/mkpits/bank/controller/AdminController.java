package com.mkpits.bank.controller;

import com.mkpits.bank.dto.request.EmployeeRequest;
import com.mkpits.bank.dto.request.UserRequest;
import com.mkpits.bank.model.*;
import com.mkpits.bank.repository.*;
import com.mkpits.bank.service.IEmployeeService;
import com.mkpits.bank.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
@Autowired
    IUserService userService;
    @Autowired
    IEmployeeService employeeService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserCredentialRepository userCredentialRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserAddressRepo addressRepository;
    @Autowired
    UserStateRepo stateRepository;
    @Autowired
    UserDistrictRepo districtRepository;
    @Autowired
   UserCityRepo cityRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeAddressRepository employeeAddressRepository;

    @Autowired
    EmployeeCredentialRepository employeeCredentialRepository;
//Register User
    @GetMapping("/admin/adduser")
    public String getRegisterUsers(Model model) {
        model.addAttribute("userForm",new UserRequest());
        return "admin/adduser";
    }

    @PostMapping ("/admin/adduser")
   public String registerUsers(@ModelAttribute UserRequest userRequest, Model model)  {

      UserRequest userRequest1= userService.registerUsers(userRequest);
      model.addAttribute("message","user registered successfully");
     return "redirect:/users";
    }




    //Delete user by id
    @GetMapping("/admin/deleteuser/{id}")
      public String deleteUser(@PathVariable("id") Long id){
       userService.deleteUserById(id) ;
       return "redirect:/users";
    }


//    Register Employee

    @GetMapping("/admin/addemployee")
    public String getRegisterEmployees(Model model) {
        model.addAttribute("employeeRequest", new EmployeeRequest());
        return "admin/addemployee";
    }


    @PostMapping("/admin/addemployee")
    public String registerEmployees(@ModelAttribute("employeeRequest") EmployeeRequest employeeRequest, Model model) {
        employeeService.registerEmployees(employeeRequest);
        model.addAttribute("message", "Employee registered successfully");
        return "redirect:/employees";
    }


    //    update employee
    @GetMapping("/admin/updateemployee/{id}")
    public String getUpdateForm(@PathVariable("id") Long id,Model model) {
        Optional<Employee> employeeOptional = employeeRepository.findById(Math.toIntExact(id));
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();

            EmployeeCredential employeeCredential = employeeCredentialRepository.findByEmployeeId(employee.getId()).orElse(new EmployeeCredential());
            EmployeeAddress employeeAddress = employeeAddressRepository.findByEmployeeId(employee.getId()).orElse(new EmployeeAddress());

            EmployeeRequest employeeRequest = EmployeeRequest.builder()
                    .firstName(employee.getFirstName())
                    .middleName(employee.getMiddleName())
                    .lastName(employee.getLastName())
                    .mobileNumber(employee.getMobileNumber())
                    .email(employee.getEmail())
                    .gender(employee.getGender())
                    .dateOfBirth(employee.getDateOfBirth().toString())
                    .adhaarCard(employee.getAdhaarCard())
                    .userName(employeeCredential.getUserName())
                    .password(employeeCredential.getPassword())
                    .address(employeeAddress.getAddress())
                    .state(employeeAddress.getState())
                    .city(employeeAddress.getCity())
                    .pinCode(employeeAddress.getPinCode())
                    .build();

            model.addAttribute("employeeRequest", employeeRequest);
            model.addAttribute("employeeId", id);
            return "admin/editemployee";
        } else {
            // Handle the case where the employee is not found
            return "redirect:/employees";
        }
    }
    @PostMapping("/admin/updateemployee")
    public String updateEmployee(@ModelAttribute EmployeeRequest employeeRequest, @RequestParam Long employeeId, Model model) {

        employeeService.updateEmployeeData(employeeId,employeeRequest);

            model.addAttribute("message", "Employee updated successfully");
        return "redirect:/employees";
    }


    //Delete user by id
    @GetMapping("/admin/deleteemployee/{id}")
    public String deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployeeById(id) ;
        return "redirect:/employees";
    }
}
