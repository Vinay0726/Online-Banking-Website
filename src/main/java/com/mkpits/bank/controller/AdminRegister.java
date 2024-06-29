package com.mkpits.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminRegister {

    @GetMapping("/auth-register-basic")

    public String adminRegister(){
        return "auth-register-basic";
    }
}
