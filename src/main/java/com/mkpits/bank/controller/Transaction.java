package com.mkpits.bank.controller;

import com.mkpits.bank.dto.response.TransactionResponse;
import com.mkpits.bank.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Transaction {
@Autowired
    IUserService userService;
    @GetMapping("/admin/transaction")
    public String getTransaction(Model model) {
        List<TransactionResponse> transactionResponseList=userService.getAllTransactions();
        model.addAttribute("transactions",transactionResponseList);
        return "admin/transaction";
    }
}
