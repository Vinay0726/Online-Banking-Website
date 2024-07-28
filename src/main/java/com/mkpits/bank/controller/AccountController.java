package com.mkpits.bank.controller;

import com.mkpits.bank.dto.request.TransferRequest;
import com.mkpits.bank.dto.response.AccountResponse;
import com.mkpits.bank.dto.response.TransferResponse;
import com.mkpits.bank.model.Account;
import com.mkpits.bank.service.IEmployeeService;
import com.mkpits.bank.service.ITransactionService;
import com.mkpits.bank.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AccountController {
    @Autowired
    IUserService userService;


    @GetMapping("/admin/accounts")
    public String getAccounts(Model model) {

        List<AccountResponse> accountList= userService.getAllAccounts();
        model.addAttribute("accounts",accountList);
        return "admin/accounts";
    }
    @GetMapping("/admin/accounts/{userId}")
    public String getAccountsByUserId(@PathVariable Integer userId, Model model) {
        List<AccountResponse> accountResponseList = userService.getAccountByUserId(userId);
        model.addAttribute("accounts", accountResponseList);
        return "/admin/total-accounts";
    }


}
