package com.mkpits.bank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkpits.bank.dto.request.AdminRequest;
import com.mkpits.bank.dto.request.EmployeeRequest;
import com.mkpits.bank.model.*;
import com.mkpits.bank.model.Transaction;
import com.mkpits.bank.repository.AdminAddressRepository;
import com.mkpits.bank.repository.AdminCredentialRepository;
import com.mkpits.bank.repository.AdminRepository;
import com.mkpits.bank.service.IAdminService;
import com.mkpits.bank.service.ITransactionService;
import com.mkpits.bank.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class DashboardController {
    @Autowired
    IUserService userService;
    @Autowired
    ITransactionService transactionService;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AdminCredentialRepository adminCredentialRepository;

    @Autowired
    AdminAddressRepository adminAddressRepository;
    @Autowired
    IAdminService adminService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) throws JsonProcessingException {

        long totalUser = userService.getTotalUsers();
        long totalAccount = userService.getTotalAccounts();
        long totalTransaction = userService.getTotalTransactions();
        long accountsCreatedToday = userService.getTotalAccountsCreatedToday();

        List<Map<String, Object>> dailyUserData = userService.getDailyUserData();

        ObjectMapper objectMapper = new ObjectMapper();
        String dailyUserDataJson = objectMapper.writeValueAsString(dailyUserData);

        // Fetch the last 5 transactions
        List<Transaction> last5Transactions = transactionService.getLast5Transactions();

        // Fetch the last 5 users
        List<User> last5Users = userService.getLast5Users();

        //total balance of the all account
        BigDecimal totalBalance = userService.getTotalBalance();


        model.addAttribute("totalUsers", totalUser);
        model.addAttribute("totalAccounts", totalAccount);
        model.addAttribute("totalTransactions", totalTransaction);
        model.addAttribute("todayAccounts", accountsCreatedToday);
        model.addAttribute("dailyUserDataJson", dailyUserDataJson);
        model.addAttribute("last5Transactions", last5Transactions);
        model.addAttribute("totalBalance", totalBalance);
        model.addAttribute("last5Users", last5Users);

        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Admin admin = adminService.getAdminDetailsByUsername(username);
        model.addAttribute("admins", admin);
        return "admin/dashboard";
    }

    @GetMapping("/admin/accounts-setting")
    public String getSetting(Model model) {
        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Admin admin1 = adminService.getAdminDetailsByUsername(username);
        model.addAttribute("admins", admin1);
        //for getting old value
        AdminCredential adminCredential1 = adminCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer userId = adminCredential1.getAdminId();
        Optional<Admin> adminOptional = adminRepository.findById(Math.toIntExact(userId));
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();

            AdminCredential adminCredential = adminCredentialRepository.findByAdminId(admin.getId()).orElse(new AdminCredential());
            AdminAddress adminAddress = adminAddressRepository.findByAdminId(admin.getId()).orElse(new AdminAddress());

            AdminRequest adminRequest = AdminRequest.builder()
                    .firstName(admin.getFirstName())
                    .middleName(admin.getMiddleName())
                    .lastName(admin.getLastName())
                    .mobileNumber(admin.getMobileNumber())
                    .email(admin.getEmail())
                    .gender(admin.getGender())
                    .dateOfBirth(admin.getDateOfBirth().toString())
                    .adhaarCard(admin.getAdhaarCard())
                    .userName(adminCredential.getUserName())
                    .password(adminCredential.getPassword())
                    .address(adminAddress.getAddress())
                    .state(adminAddress.getState())
                    .city(adminAddress.getCity())
                    .pinCode(adminAddress.getPincode())
                    .build();

            model.addAttribute("adminRequest", adminRequest);
            model.addAttribute("adminId", userId);
            return "admin/accounts-setting";
        } else {
            // Handle the case where the employee is not found
            return "admin/accounts-setting";
        }

    }

    @PostMapping("/admin/updateadmin")
    public String updateEmployee(@ModelAttribute AdminRequest adminRequest, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AdminCredential adminCredential1 = adminCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer adminId = adminCredential1.getAdminId();
        adminService.updateAdminData(Long.valueOf(adminId),adminRequest);

        model.addAttribute("message", "Admin updated successfully");
        return "redirect:/admin/accounts-setting";
    }

    }

