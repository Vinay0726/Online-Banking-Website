package com.mkpits.bank.controller;


import com.mkpits.bank.dto.response.UserCredentialResponse;
import com.mkpits.bank.dto.response.UserResponse;
import com.mkpits.bank.model.UserCredential;
import com.mkpits.bank.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<UserResponse> userResponseDtoList = userService.getAllUsers();
        List<UserCredentialResponse> userCredentialResponses = userService.getAllUsersCredentials();
        model.addAttribute("users", userResponseDtoList);
        model.addAttribute("usersCredentials", userCredentialResponses);
        return "users";
    }


}


