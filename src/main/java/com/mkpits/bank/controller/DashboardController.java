package com.mkpits.bank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkpits.bank.model.Transaction;
import com.mkpits.bank.model.User;
import com.mkpits.bank.service.ITransactionService;
import com.mkpits.bank.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {
@Autowired
    IUserService userService;
@Autowired
    ITransactionService transactionService;
    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) throws JsonProcessingException {

        long totalUser=userService.getTotalUsers();
        long totalAccount=userService.getTotalAccounts();
        long totalTransaction=userService.getTotalTransactions();
        long accountsCreatedToday=userService.getTotalAccountsCreatedToday();

        List<Map<String, Object>> dailyUserData = userService.getDailyUserData();

        ObjectMapper objectMapper = new ObjectMapper();
        String dailyUserDataJson = objectMapper.writeValueAsString(dailyUserData);

        // Fetch the last 5 transactions
        List<Transaction> last5Transactions = transactionService.getLast5Transactions();

        // Fetch the last 5 users
        List<User> last5Users = userService.getLast5Users();

        //total balance of the all account
        BigDecimal totalBalance = userService.getTotalBalance();


        model.addAttribute("totalUsers",totalUser);
        model.addAttribute("totalAccounts",totalAccount);
        model.addAttribute("totalTransactions",totalTransaction);
        model.addAttribute("todayAccounts",accountsCreatedToday);
        model.addAttribute("dailyUserDataJson", dailyUserDataJson);
        model.addAttribute("last5Transactions", last5Transactions);
        model.addAttribute("totalBalance", totalBalance);
        model.addAttribute("last5Users", last5Users);

        return "admin/dashboard";
    }
    @GetMapping("/admin/accounts-setting")
    public String getSetting() {
        return "admin/accounts-setting";
    }
}
