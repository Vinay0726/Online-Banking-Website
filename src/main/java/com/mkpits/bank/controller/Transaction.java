package com.mkpits.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Transaction {

    @GetMapping("/transaction")
    public String getTransaction() {
        return "transaction";
    }
}
