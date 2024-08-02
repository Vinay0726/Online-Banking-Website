package com.mkpits.bank.controller;

import com.mkpits.bank.dto.request.TransferRequest;
import com.mkpits.bank.dto.response.AccountResponse;
import com.mkpits.bank.dto.response.TransferResponse;
import com.mkpits.bank.model.*;
import com.mkpits.bank.repository.AdminCredentialRepository;
import com.mkpits.bank.repository.AdminRepository;
import com.mkpits.bank.service.IEmployeeService;
import com.mkpits.bank.service.ITransactionService;
import com.mkpits.bank.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class AccountController {
    @Autowired
    IUserService userService;
@Autowired
    AdminCredentialRepository adminCredentialRepository;
@Autowired
    AdminRepository adminRepository;

    @GetMapping("/admin/accounts")
    public String getAccounts(Model model) {

        List<AccountResponse> accountList= userService.getAllAccounts();
        model.addAttribute("accounts",accountList);

//        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AdminCredential adminCredential = adminCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer adminId = adminCredential.getAdminId();
        Optional<Admin> adminOptional= adminRepository.findById(adminId);
        Admin admin= adminOptional.get();

        admin=Admin.builder().firstName(admin.getFirstName()).lastName(admin.getLastName()).gender(admin.getGender()).build();
        System.out.println(admin);
        model.addAttribute("admins", admin);

        return "admin/accounts";
    }
    @GetMapping("/admin/accounts/{userId}")
    public String getAccountsByUserId(@PathVariable Integer userId, Model model) {

        List<AccountResponse> accountResponseList = userService.getAccountByUserId(userId);
        model.addAttribute("accounts", accountResponseList);

        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AdminCredential adminCredential = adminCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer adminId = adminCredential.getAdminId();
        Optional<Admin> adminOptional= adminRepository.findById(adminId);
        Admin admin= adminOptional.get();

        admin=Admin.builder().firstName(admin.getFirstName()).lastName(admin.getLastName()).gender(admin.getGender()).build();
        System.out.println(admin);
        model.addAttribute("admins", admin);

        return "/admin/total-accounts";
    }


}
