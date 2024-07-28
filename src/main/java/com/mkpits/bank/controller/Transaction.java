package com.mkpits.bank.controller;

import com.mkpits.bank.dto.request.TransferRequest;
import com.mkpits.bank.dto.response.TransactionResponse;
import com.mkpits.bank.dto.response.TransferResponse;
import com.mkpits.bank.service.ITransactionService;
import com.mkpits.bank.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/admin/transaction")
    public String getTransaction(Model model) {
        List<TransactionResponse> transactionResponseList=userService.getAllTransactions();
        List<TransferResponse> transferResponseList=userService.getAllTransferTransactions();
        model.addAttribute("transactions",transactionResponseList);
        model.addAttribute("transfer",transferResponseList);
        return "admin/transaction";
    }

}
