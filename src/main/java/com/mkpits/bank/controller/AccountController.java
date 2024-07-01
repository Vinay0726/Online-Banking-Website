package com.mkpits.bank.controller;

import com.mkpits.bank.dto.response.AccountResponse;
import com.mkpits.bank.model.Account;
import com.mkpits.bank.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AccountController {
    @Autowired
    IUserService userService;
    @GetMapping("/accounts")
    public String getAccounts(Model model) {

        List<AccountResponse> accountList= userService.getAllAccounts();
        model.addAttribute("accounts",accountList);
        return "accounts";
    }
    @GetMapping("/accounts/{userId}")
    public String getAccountsByUserId(@PathVariable Integer userId, Model model) {
        List<AccountResponse> accountResponseList = userService.getAccountsByUserId(userId);
        model.addAttribute("accounts", accountResponseList);
        return "total-accounts";
    }
}