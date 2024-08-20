package com.mkpits.bank.controller;


import com.mkpits.bank.dto.request.TransactionRequest;
import com.mkpits.bank.dto.request.TransferRequest;
import com.mkpits.bank.dto.request.UserRequest;
import com.mkpits.bank.dto.response.AccountResponse;
import com.mkpits.bank.dto.response.TransferResponse;
import com.mkpits.bank.dto.response.UserCredentialResponse;
import com.mkpits.bank.dto.response.UserResponse;
import com.mkpits.bank.model.*;
import com.mkpits.bank.model.Transaction;
import com.mkpits.bank.repository.*;
import com.mkpits.bank.service.IPendingTransactionService;
import com.mkpits.bank.service.ITransactionService;
import com.mkpits.bank.service.IUserService;
import com.mkpits.bank.service.impl.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    ITransactionService transactionService;
    @Autowired
    IPendingTransactionService pendingTransactionService;
    @Autowired
    IUserService userService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAddressRepo addressRepository;
    @Autowired
    UserStateRepo stateRepository;
    @Autowired
    UserDistrictRepo districtRepository;
    @Autowired
    UserCityRepo cityRepository;

    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    TransferTransactionRepository transferTransactionRepository;
    @GetMapping("/user/dashboard")
    public String getUserDashboard(@RequestParam(required = false) String accountNumber,Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserCredential userCredential = userCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer userId = userCredential.getUserId();
        List<Transaction> transactions = transactionService.getTransactionsForUser(username);
        model.addAttribute("transactions", transactions);
        List<AccountResponse> accounts = userService.getAccountDetailsByUserId(userId);
        model.addAttribute("accounts", accounts);
        //get total balance of all accounts
        BigDecimal totalBalance = userService.getTotalBalanceByUserId(userId);
        model.addAttribute("totalBalance", totalBalance);

        // Fetch total accounts and transactions
        long totalAccounts = userService.getTotalAccountsByUserId(userId);
        long totalTransactions = userService.getTotalTransactionsByUserId(userId);
        model.addAttribute("totalAccounts", totalAccounts);
        model.addAttribute("totalTransactions", totalTransactions);
        //for current date
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formattedDate = now.format(formatter);
        model.addAttribute("formattedDate", formattedDate);
        //for user name
        Optional<User> userOptional= userRepository.findById(userId);
        User user= userOptional.get();
        user=User.builder().firstName(user.getFirstName()).lastName(user.getLastName()).gender(user.getGender()).build();
        model.addAttribute("users", user);


        // Handle the case where accounts might be empty
        if (accountNumber != null) {
            model.addAttribute("selectedAccount", accountNumber);

            BigDecimal totalCredit = transactionService.getTotalCreditByAccountNumber(accountNumber);
            BigDecimal totalDebit = transactionService.getTotalDebitByAccountNumber(accountNumber);
            int totalTransaction = transactionService.getTotalTransactionsByAccountNumber(accountNumber);
            BigDecimal balance = transactionService.getBalanceByAccountNumber(accountNumber);

            model.addAttribute("totalCredit", totalCredit);
            model.addAttribute("totalDebit", totalDebit);
            model.addAttribute("totalTransactions", totalTransaction);
            model.addAttribute("balance", balance);
        } else {
            model.addAttribute("selectedAccount", "No accounts available");
            model.addAttribute("totalCredit", BigDecimal.ZERO);
            model.addAttribute("totalDebit", BigDecimal.ZERO);
            model.addAttribute("totalTransactions", 0);
            model.addAttribute("balance", BigDecimal.ZERO);
        }





        return "user/dashboard";
    }


    @GetMapping("/user/balance")
    public String getUserBalance(@RequestParam(required = false) String accountNumber,Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserCredential userCredential = userCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer userId = userCredential.getUserId();
        List<AccountResponse> accounts = userService.getAccountDetailsByUserId(userId);
        model.addAttribute("accounts", accounts);
        //getting user details
        Optional<User> userOptional= userRepository.findById(userId);
        User user= userOptional.get();
        user=User.builder().firstName(user.getFirstName()).lastName(user.getLastName()).gender(user.getGender()).build();
        model.addAttribute("users", user);
        // Set default account to the first one

        // Handle the case where accounts might be empty
        if (accountNumber != null) {
            model.addAttribute("selectedAccount", accountNumber);

            BigDecimal totalCredit = transactionService.getTotalCreditByAccountNumber(accountNumber);
            BigDecimal totalDebit = transactionService.getTotalDebitByAccountNumber(accountNumber);
            int totalTransactions = transactionService.getTotalTransactionsByAccountNumber(accountNumber);
            BigDecimal balance = transactionService.getBalanceByAccountNumber(accountNumber);

            model.addAttribute("totalCredit", totalCredit);
            model.addAttribute("totalDebit", totalDebit);
            model.addAttribute("totalTransactions", totalTransactions);
            model.addAttribute("balance", balance);
        } else {
            model.addAttribute("selectedAccount", "No accounts available");
            model.addAttribute("totalCredit", BigDecimal.ZERO);
            model.addAttribute("totalDebit", BigDecimal.ZERO);
            model.addAttribute("totalTransactions", 0);
            model.addAttribute("balance", BigDecimal.ZERO);
        }


        return "user/account-balance";
    }
    @GetMapping("/user/accountinfo")
    public String getAccountInfo(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserCredential userCredential = userCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer userId = userCredential.getUserId();
        List<AccountResponse> accounts = userService.getAccountDetailsByUserId(userId);
        model.addAttribute("accounts", accounts);
       //for getting user data
       Optional<User> userOptional= userRepository.findById(userId);
       User user= userOptional.get();
       user=User.builder().firstName(user.getFirstName()).lastName(user.getLastName()).cin(user.getCin()).gender(user.getGender()).build();
       model.addAttribute("users", user);

        BigDecimal totalCredit = transactionService.getTotalCreditByUserId(userId);
        BigDecimal totalDebit = transactionService.getTotalDebitByUserId(userId);
        int totalTransactions = transactionService.getTotalTransactionsByUserId(userId);

        model.addAttribute("totalCredit", totalCredit);
        model.addAttribute("totalDebit", totalDebit);
        model.addAttribute("totalTransactions", totalTransactions);
        return "user/account-info";
    }

//    @GetMapping("/user/credit")
//    public String getCredit(){
//        return "user/credit";
//    }
//    @GetMapping("/user/debit")
//    public String getDebit(){
//        return "user/debit";
//    }
    //update Profile
    @GetMapping("/user/profile")
    public String getProfileSetting(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserCredential userCredential1 = userCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer userId = userCredential1.getUserId();

        //for getting user data name and gender
        Optional<User> userOptional1= userRepository.findById(userId);
        User user1= userOptional1.get();
        user1=User.builder().firstName(user1.getFirstName()).lastName(user1.getLastName()).cin(user1.getCin()).gender(user1.getGender()).build();
        model.addAttribute("users", user1);


        Optional<User> userOptional = userRepository.findById(Math.toIntExact(userId));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserCredential userCredential = userCredentialRepository.findByUserId(user.getId()).orElse(new UserCredential());
//            Account account = accountRepository.findByUserId(user.getId()).orElse(new Account());
            UserAddress userAddress = addressRepository.findByUserId(user.getId()).orElse(new UserAddress());
            UserState userState = stateRepository.findById(userAddress.getStateId()).orElse(new UserState());
            UserDistrict userDistrict = districtRepository.findById(userAddress.getDistrictId()).orElse(new UserDistrict());
            UserCity userCity = cityRepository.findById(userAddress.getCityId()).orElse(new UserCity());

            UserRequest userRequest = new UserRequest();
            userRequest.setUser(user);
            userRequest.setUserCredential(userCredential);
//            userRequest.setAccount(account);
            userRequest.setAddress(userAddress);
            userRequest.setState(userState);
            userRequest.setDistrict(userDistrict);
            userRequest.setCity(userCity);
            userRequest.setUserId(Long.valueOf(userId)); // Set userId in userRequest

            model.addAttribute("userRequest", userRequest);

            return "user/profile-setting";
        }else{

            return "user/profile-setting";
        }

    }
    @PostMapping("/user/updateuser")
    public String updateUser(@ModelAttribute UserRequest userRequest, Model model) {
        userService.updateUserData(userRequest.getUserId(), userRequest);
        model.addAttribute("message", "Profile updated successfully");
        return "redirect:/user/profile";
    }

    @GetMapping("/user/credit")
    public String getCreditRequestForm(Model model) {
        model.addAttribute("transactionRequest", new TransactionRequest());

        //for getting id who user login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserCredential userCredential1 = userCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer userId = userCredential1.getUserId();

        //for getting user data name and gender
        Optional<User> userOptional1= userRepository.findById(userId);
        User user1= userOptional1.get();
        user1=User.builder().firstName(user1.getFirstName()).lastName(user1.getLastName()).cin(user1.getCin()).gender(user1.getGender()).build();
        model.addAttribute("users", user1);
        return "user/credit";
    }

    @GetMapping("/user/debit")
    public String getDebitRequestForm(Model model) {
        model.addAttribute("transactionRequest", new TransactionRequest());


        //for getting id who user login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserCredential userCredential1 = userCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer userId = userCredential1.getUserId();

        //for getting user data name and gender
        Optional<User> userOptional1= userRepository.findById(userId);
        User user1= userOptional1.get();
        user1=User.builder().firstName(user1.getFirstName()).lastName(user1.getLastName()).cin(user1.getCin()).gender(user1.getGender()).build();
        model.addAttribute("users", user1);
        return "user/debit";
    }

    @PostMapping("/user/requestCredit")
    public String requestCredit(@ModelAttribute TransactionRequest transactionRequest, Model model) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            pendingTransactionService.createPendingRequest(transactionRequest, username, TransactionType.Credit);
            model.addAttribute("message", "Credit request submitted for approval!");
            return "redirect:/user/transaction";
        } catch (Exception e) {
            model.addAttribute("message", "Credit request failed: " + e.getMessage());
            return "redirect:/user/transaction";
        }
    }

    @PostMapping("/user/requestDebit")
    public String requestDebit(@ModelAttribute TransactionRequest transactionRequest, Model model) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            pendingTransactionService.createPendingRequest(transactionRequest, username, TransactionType.Debit);
            model.addAttribute("message", "Debit request submitted for approval!");
            return "redirect:/user/transaction";
        } catch (Exception e) {
            model.addAttribute("message", "Debit request failed: " + e.getMessage());
            return "redirect:/user/transaction";
        }
    }


    //for transfer amount from one user to another
    @GetMapping("/user/transfer")
    public String getTransferForm(Model model) {
        model.addAttribute("transferRequest", new TransferRequest());

        //for getting id who user login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserCredential userCredential1 = userCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer userId = userCredential1.getUserId();

        //for getting user data name and gender
        Optional<User> userOptional1= userRepository.findById(userId);
        User user1= userOptional1.get();
        user1=User.builder().firstName(user1.getFirstName()).lastName(user1.getLastName()).cin(user1.getCin()).gender(user1.getGender()).build();
        model.addAttribute("users", user1);
        return "user/transfer";
    }

    @PostMapping("/user/transfer")
    public String transferAmount(@ModelAttribute TransferRequest transferRequest, Model model) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<UserCredential> userCredentialOpt = userCredentialRepository.findByUserName(username);

            if (userCredentialOpt.isPresent()) {
                Integer senderUserId = userCredentialOpt.get().getUser().getId();
                TransferResponse response = transactionService.amountTransfer(transferRequest, senderUserId);
                model.addAttribute("message", "Transfer successful!");
                model.addAttribute("transactions", response.getTransactions());
                return "redirect:/user/transaction";
            } else {
                model.addAttribute("message", "User not found");
                return "redirect:/user/transaction";
            }
        } catch (Exception e) {
            model.addAttribute("message", "Transfer failed: " + e.getMessage());
            return "redirect:/user/transfer";

        }

    }

    @GetMapping("/user/transaction")
    public String showTransactionPage(@RequestParam(value = "message", required = false) String message, Model model) {
        model.addAttribute("message", message);
       String username=SecurityContextHolder.getContext().getAuthentication().getName();
       Optional<UserCredential> userCredential = userCredentialRepository.findByUserName(username);
        // Add the transfer transaction details to the model
        List<TransferTransaction> transferTransactions = transactionService.getAllTransactions(Long.valueOf(userCredential.get().getUser().getId())); // Fetch transactions as needed
        model.addAttribute("transferTransactions", transferTransactions);
//        // Add the credit debit transaction details to the model
        List<Transaction> transactions = transactionService.getTransactionsForUser(username);
        model.addAttribute("transactions", transactions);
        if (message != null) {
            model.addAttribute("message", message);
        }

        //for getting data name and gender
        UserCredential userCredential1 = userCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer userId = userCredential1.getUserId();

        //for getting user data name and gender
        Optional<User> userOptional1= userRepository.findById(userId);
        User user1= userOptional1.get();
        user1=User.builder().firstName(user1.getFirstName()).lastName(user1.getLastName()).cin(user1.getCin()).gender(user1.getGender()).build();
        model.addAttribute("users", user1);

        return "user/transaction"; // This should match the Thymeleaf template name
    }



}


