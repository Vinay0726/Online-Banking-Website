package com.mkpits.bank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkpits.bank.dto.request.EmployeeRequest;
import com.mkpits.bank.dto.response.*;
import com.mkpits.bank.model.*;
import com.mkpits.bank.model.Transaction;
import com.mkpits.bank.repository.*;
import com.mkpits.bank.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    IUserService userService;
    @Autowired
    ITransactionService transactionService;
    @Autowired
    IPendingTransactionService pendingTransactionService;
    @Autowired
    IEmployeeService employeeService;
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
    @GetMapping("/admin/employees")
    public String getEmployees(Model model) {
        List<EmployeeResponse> employeeResponseDtoList = employeeService.getAllEmployees();
        List<EmployeeCredentialResponse> employeeCredentialResponses=employeeService.getAllEmployeesCredentials();
        model.addAttribute("employees", employeeResponseDtoList);
        model.addAttribute("employeesCredentials", employeeCredentialResponses);


        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Admin admin = adminService.getAdminDetailsByUsername(username);
        model.addAttribute("admins", admin);
        return "admin/employees";
    }

    @GetMapping("/employee/dashboard")
    public  String getDashboard(Model model) throws JsonProcessingException {

        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Employee employee = employeeService.getEmployeeDetailsByUsername(username);
        model.addAttribute("employees", employee);

        //total users
        long totalUser=userService.getTotalUsers();
        //total accounts
        long totalAccount=userService.getTotalAccounts();
        //total transactions
        long totalTransaction=userService.getTotalTransactions();
        //total balance of the all account
        BigDecimal totalBalance = userService.getTotalBalance();
        // Fetch the last 5 transactions
        List<Transaction> last5Transactions = transactionService.getLast5Transactions();

        // Fetch the last 5 users
        List<User> last5Users = userService.getLast5Users();
       //get the daily users added
        List<Map<String, Object>> dailyUserData = userService.getDailyUserData();


        ObjectMapper objectMapper = new ObjectMapper();
        String dailyUserDataJson = objectMapper.writeValueAsString(dailyUserData);

        model.addAttribute("totalUsers",totalUser);
        model.addAttribute("totalAccounts",totalAccount);
        model.addAttribute("totalTransactions",totalTransaction);
        model.addAttribute("totalBalance", totalBalance);
        model.addAttribute("last5Transactions", last5Transactions);
        model.addAttribute("last5Users", last5Users);
        model.addAttribute("dailyUserDataJson", dailyUserDataJson);

        return "employee/dashboard";
    }

    @GetMapping("/employee/users")
    public  String getUsers(Model model){
        List<UserResponse> userResponseDtoList = userService.getAllUsers();
        List<UserCredentialResponse> userCredentialResponses = userService.getAllUsersCredentials();
        model.addAttribute("users", userResponseDtoList);
        model.addAttribute("usersCredentials", userCredentialResponses);

        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Employee employee = employeeService.getEmployeeDetailsByUsername(username);
        model.addAttribute("employees", employee);
        return "employee/users";
    }

    @GetMapping("/employee/accounts")
    public  String getAccounts(Model model){

        List<AccountResponse> accountList= userService.getAllAccounts();
        model.addAttribute("accounts",accountList);

        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Employee employee = employeeService.getEmployeeDetailsByUsername(username);
        model.addAttribute("employees", employee);
        return "employee/accounts";
    }
//    @GetMapping("/employee/cdrequest")
//    public  String getCDRequest(){
//        return "employee/credit-debit-request";
//    }
@GetMapping("/employee/cdrequest")
public String viewPendingRequests(Model model) {
    model.addAttribute("pendingRequests", pendingTransactionService.getAllPendingRequests());

    //        for getting name and profile with matching by gender wise
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    // Fetching admin details using the service
    Employee employee = employeeService.getEmployeeDetailsByUsername(username);
    model.addAttribute("employees", employee);
    return "employee/credit-debit-request";
}

    @PostMapping("/employee/approveRequest")
    public String approveRequest(@RequestParam("requestId") Long requestId, Model model) {
        try {
            pendingTransactionService.approveRequest(requestId);
            model.addAttribute("message", "Request approved successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Failed to approve request: " + e.getMessage());
        }
        return "employee/credit-debit-request";
    }

    @PostMapping("/employee/rejectRequest")
    public String rejectRequest(@RequestParam("requestId") Long requestId, Model model) {
        try {
            pendingTransactionService.rejectRequest(requestId);
            model.addAttribute("message", "Request rejected successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Failed to reject request: " + e.getMessage());
        }
        return "employee/credit-debit-request";
    }


    @GetMapping("/employee/transferrequest")
    public  String getTransferRequest(){
        return "employee/transfer-request";
    }
    @GetMapping("/employee/transactions")
    public  String getAllTransactions(Model model){

        List<TransactionResponse> transactionResponseList=userService.getAllTransactions();
        List<TransferResponse> transferResponseList=userService.getAllTransferTransactions();
        model.addAttribute("transactions",transactionResponseList);
        model.addAttribute("transfer",transferResponseList);

        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Employee employee = employeeService.getEmployeeDetailsByUsername(username);
        model.addAttribute("employees", employee);
        return "employee/transactions";
    }

    @GetMapping("/employee/profile")
    public  String getProfileSetting(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Fetching admin details using the service
        Employee employee1 = employeeService.getEmployeeDetailsByUsername(username);
        model.addAttribute("employees", employee1);
        EmployeeCredential employeeCredential1 = employeeCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Integer userId = employeeCredential1.getEmployeeId();




        Optional<Employee> employeeOptional = employeeRepository.findById(Math.toIntExact(userId));
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
            model.addAttribute("employeeId", userId);
            return "employee/profile-setting";
        } else {
            // Handle the case where the employee is not found
            return "employee/profile-setting";


    }}
    @PostMapping("/employee/updateemployee")
    public String updateEmployee(@ModelAttribute EmployeeRequest employeeRequest, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        EmployeeCredential employeeCredential1 = employeeCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer employeeId = employeeCredential1.getEmployeeId();
        employeeService.updateEmployeeData(Long.valueOf(employeeId),employeeRequest);

        model.addAttribute("message", "Employee updated successfully");
        return "employee/profile-setting";
    }

    @GetMapping("/employee/error")
    public  String get404Error(Model model){

        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Employee employee = employeeService.getEmployeeDetailsByUsername(username);
        model.addAttribute("employees", employee);
        return "employee/404";
    }

}