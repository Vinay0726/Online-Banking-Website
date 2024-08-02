package com.mkpits.bank.controller;

import com.mkpits.bank.dto.request.AccountRequest;
import com.mkpits.bank.dto.request.EmployeeRequest;
import com.mkpits.bank.dto.request.UserRequest;
import com.mkpits.bank.dto.response.UserCredentialResponse;
import com.mkpits.bank.dto.response.UserResponse;
import com.mkpits.bank.model.*;
import com.mkpits.bank.repository.*;
import com.mkpits.bank.service.IAdminService;
import com.mkpits.bank.service.IEmployeeService;
import com.mkpits.bank.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AdminCredentialRepository adminCredentialRepository;
    @Autowired
    IAdminService adminService;
//Register User
    @GetMapping("/admin/adduser")
    public String getRegisterUsers(Model model) {
        model.addAttribute("userForm",new UserRequest());


        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Admin admin = adminService.getAdminDetailsByUsername(username);
        model.addAttribute("admins", admin);
        return "admin/adduser";
    }
    //get all users & credentials
    @GetMapping("/admin/users")
    public String getUsers(Model model) {
        List<UserResponse> userResponseDtoList = userService.getAllUsers();
        List<UserCredentialResponse> userCredentialResponses = userService.getAllUsersCredentials();
        model.addAttribute("users", userResponseDtoList);
        model.addAttribute("usersCredentials", userCredentialResponses);

        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Admin admin = adminService.getAdminDetailsByUsername(username);
        model.addAttribute("admins", admin);
        return "admin/users";
    }

    @PostMapping ("/admin/adduser")
   public String registerUsers(@ModelAttribute UserRequest userRequest, Model model)  {


        try {
            UserRequest registeredUser = userService.registerUsers(userRequest);
            model.addAttribute("message", "User registered successfully");
            return "redirect:/admin/users";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to register user: " + e.getMessage());
            // You might want to add userRequest or other necessary attributes back to the model
            return "admin/adduser"; // Assuming "adduser" is your form view name
        }

    }


//    Update User

    @GetMapping("/admin/updateuser/{id}")
    public String getUpdateUserForm(@PathVariable("id") Long id, Model model) {
        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Admin admin = adminService.getAdminDetailsByUsername(username);
        model.addAttribute("admins", admin);

        //for update
        Optional<User> userOptional = userRepository.findById(Math.toIntExact(id));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserCredential userCredential = userCredentialRepository.findByUserId(user.getId()).orElse(new UserCredential());
//            Account account = accountRepository.findByUserId(user.getId()).orElse(new Account());
            UserAddress userAddress = addressRepository.findByUserId(user.getId()).orElse(new UserAddress());
            UserState userState = stateRepository.findById(userAddress.getStateId()).orElse(new UserState());
            UserDistrict userDistrict = districtRepository.findById(userAddress.getDistrictId()).orElse(new UserDistrict());
            UserCity userCity = cityRepository.findById(userAddress.getCityId()).orElse(new UserCity());

            UserRequest userRequest = new UserRequest();
            userRequest.setUser(user);
            userRequest.setUserCredential(userCredential);
//            userRequest.setAccount(account);
            userRequest.setAddress(userAddress);
            userRequest.setState(userState);
            userRequest.setDistrict(userDistrict);
            userRequest.setCity(userCity);
            userRequest.setUserId(id); // Set userId in userRequest

            model.addAttribute("userRequest", userRequest);
            return "admin/edituser";
        } else {
            // Handle the case where the user is not found
            return "redirect:/admin/users";
        }
    }

    @PostMapping("/admin/updateuser")
    public String updateUser(@ModelAttribute UserRequest userRequest, Model model) {
        userService.updateUserData(userRequest.getUserId(), userRequest);
        model.addAttribute("message", "User updated successfully");
        return "redirect:/admin/users";
    }


    //Delete user by id
    @GetMapping("/admin/deleteuser/{id}")
      public String deleteUser(@PathVariable("id") Long id){
       userService.deleteUserById(id) ;
       return "redirect:/admin/users";
    }
//for account add
@GetMapping("/admin/addaccount/{userId}")
public String getAddAccount(@PathVariable Integer userId, Model model) {
    model.addAttribute("accountForm", new AccountRequest());
    model.addAttribute("userId", userId);

    //        for getting name and profile with matching by gender wise
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    // Fetching admin details using the service
    Admin admin = adminService.getAdminDetailsByUsername(username);
    model.addAttribute("admins", admin);
    return "admin/addaccount";
}

    @PostMapping("/admin/account")
    public String addAccount(@RequestParam Integer userId, @ModelAttribute AccountRequest accountRequest, Model model) {
        try {
            userService.addAccount(accountRequest, userId);
            model.addAttribute("message", "Account added successfully");
            return "redirect:/admin/accounts";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add account: " + e.getMessage());
            model.addAttribute("accountForm", accountRequest);
            model.addAttribute("userId", userId);
            return "admin/addaccount";
        }
    }
//    Register Employee

    @GetMapping("/admin/addemployee")
    public String getRegisterEmployees(Model model) {
        model.addAttribute("employeeRequest", new EmployeeRequest());

        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Admin admin = adminService.getAdminDetailsByUsername(username);
        model.addAttribute("admins", admin);
        return "admin/addemployee";
    }


    @PostMapping("/admin/addemployee")
    public String registerEmployees(@ModelAttribute("employeeRequest") EmployeeRequest employeeRequest, Model model) {
        employeeService.registerEmployees(employeeRequest);
        model.addAttribute("message", "Employee registered successfully");
        return "redirect:/admin/employees";
    }


    //    update employee
    @GetMapping("/admin/updateemployee/{id}")
    public String getUpdateForm(@PathVariable("id") Long id,Model model) {
        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Admin admin = adminService.getAdminDetailsByUsername(username);
        model.addAttribute("admins", admin);

        //for update
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
            return "redirect:/admin/employees";
        }
    }
    @PostMapping("/admin/updateemployee")
    public String updateEmployee(@ModelAttribute EmployeeRequest employeeRequest, @RequestParam Long employeeId, Model model) {

        employeeService.updateEmployeeData(employeeId,employeeRequest);

            model.addAttribute("message", "Employee updated successfully");
        return "redirect:/admin/employees";
    }


    //Delete user by id
    @GetMapping("/admin/deleteemployee/{id}")
    public String deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployeeById(id) ;
        return "redirect:admin/employees";
    }
}
