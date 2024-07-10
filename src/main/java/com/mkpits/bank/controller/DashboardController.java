package com.mkpits.bank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkpits.bank.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {
@Autowired
    IUserService userService;
    @GetMapping("/dashboard")
    public String dashboard(Model model) throws JsonProcessingException {

        long totalUser=userService.getTotalUsers();
        long totalAccount=userService.getTotalAccounts();
        long totalTransaction=userService.getTotalTransactions();
        long accountsCreatedToday=userService.getTotalAccountsCreatedToday();

        List<Map<String, Object>> dailyUserData = userService.getDailyUserData();

        ObjectMapper objectMapper = new ObjectMapper();
        String dailyUserDataJson = objectMapper.writeValueAsString(dailyUserData);


        model.addAttribute("totalUsers",totalUser);
        model.addAttribute("totalAccounts",totalAccount);
        model.addAttribute("totalTransactions",totalTransaction);
        model.addAttribute("todayAccounts",accountsCreatedToday);
        model.addAttribute("dailyUserDataJson", dailyUserDataJson);

        return "dashboard";
    }
    @GetMapping("/accounts-setting")
    public String getSetting() {
        return "accounts-setting";
    }
}
