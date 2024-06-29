package com.mkpits.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLogin {


    @GetMapping("/admin")
    public String loginAdmin() {
        return "auth-login-basic";
    }
    @GetMapping("/auth-forget-password-basic")
    public String forgetPassword() {
        return "auth-forget-password-basic";
    }
}
