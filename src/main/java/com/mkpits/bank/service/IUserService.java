package com.mkpits.bank.service;


import com.mkpits.bank.dto.request.UserRequest;
import com.mkpits.bank.dto.response.*;
import com.mkpits.bank.model.Account;
import com.mkpits.bank.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {
 public List<UserResponse> getAllUsers();

public List<AccountResponse> getAllAccounts();

public List<UserCredentialResponse> getAllUsersCredentials();

public List<TransactionResponse> getAllTransactions();

public  List<TransferResponse> getAllTransferTransactions();

public List<AccountResponse> getAccountByUserId(Integer userId);

//for Register  User
 public UserRequest registerUsers(UserRequest userRequest);

// get total user from table
   public long getTotalUsers();
    // get total transactions from table
    public long getTotalTransactions();
    // get total accounts from table
  public long getTotalAccounts();
//how many accounts created today
   public long getTotalAccountsCreatedToday();

//   get daily user account created
   public List<Map<String,Object>> getDailyUserData();


   public void deleteUserById(Long id);

   //get user by id
    Optional<User> getUserById(Long id);



    //get Total balance of accounts
    BigDecimal getTotalBalance();

    //get last 5 users
    List<User> getLast5Users();

//update user
    void updateUserData(Long userId, UserRequest userRequest);

//get accounts by user Id
    List<AccountResponse> getAccountDetailsByUserId(Integer userId);


    long getTotalAccountsByUserId(Integer userId);

    long getTotalTransactionsByUserId(Integer userId);

    BigDecimal getTotalBalanceByUserId(Integer userId);

    Optional<Account> getAccountByAccountNumber(String accountNumber);

//    Optional<Account> getAccountsByUserId(Integer userId);


}
