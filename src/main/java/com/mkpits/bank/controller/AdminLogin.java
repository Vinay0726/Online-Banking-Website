package com.mkpits.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLogin {


    @GetMapping("/admin")
    public String loginAdmin() {
        return "admin/auth-login-basic";
    }
    @GetMapping("/admin/auth-forget-password-basic")
    public String forgetPassword() {
        return "admin/auth-forget-password-basic";
    }
}
