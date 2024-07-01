package com.mkpits.bank.service;

import com.mkpits.bank.dto.response.*;
import com.mkpits.bank.model.Employee;

import java.util.List;

public interface IUserService {
 public List<UserResponse> getAllUsers();

public List<AccountResponse> getAllAccounts();

public List<UserCredentialResponse> getAllUsersCredentials();

public List<TransactionResponse> getAllTransactions();

public List<AccountResponse> getAccountsByUserId(Integer userId);

}
