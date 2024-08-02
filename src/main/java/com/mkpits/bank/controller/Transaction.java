package com.mkpits.bank.controller;

import com.mkpits.bank.dto.request.TransferRequest;
import com.mkpits.bank.dto.response.TransactionResponse;
import com.mkpits.bank.dto.response.TransferResponse;
import com.mkpits.bank.model.Admin;
import com.mkpits.bank.repository.AdminCredentialRepository;
import com.mkpits.bank.repository.AdminRepository;
import com.mkpits.bank.service.IAdminService;
import com.mkpits.bank.service.ITransactionService;
import com.mkpits.bank.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Transaction {
@Autowired
    IUserService userService;
    @Autowired
    ITransactionService transactionService;

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AdminCredentialRepository adminCredentialRepository;
    @Autowired
    IAdminService adminService;
    @GetMapping("/admin/transaction")
    public String getTransaction(Model model) {
        List<TransactionResponse> transactionResponseList=userService.getAllTransactions();
        List<TransferResponse> transferResponseList=userService.getAllTransferTransactions();
        model.addAttribute("transactions",transactionResponseList);
        model.addAttribute("transfer",transferResponseList);

        //        for getting name and profile with matching by gender wise
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Fetching admin details using the service
        Admin admin = adminService.getAdminDetailsByUsername(username);
        model.addAttribute("admins", admin);
        return "admin/transaction";
    }

}
