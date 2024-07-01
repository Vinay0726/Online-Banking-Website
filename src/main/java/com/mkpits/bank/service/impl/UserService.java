package com.mkpits.bank.service.impl;

import com.mkpits.bank.dto.response.AccountResponse;
import com.mkpits.bank.dto.response.TransactionResponse;
import com.mkpits.bank.dto.response.UserCredentialResponse;
import com.mkpits.bank.dto.response.UserResponse;
import com.mkpits.bank.model.*;
import com.mkpits.bank.repository.AccountRepository;
import com.mkpits.bank.repository.TransactionRepository;
import com.mkpits.bank.repository.UserCredentialRepository;
import com.mkpits.bank.repository.UserRepository;
import com.mkpits.bank.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
@Autowired
    UserRepository userRepository;
@Autowired
    AccountRepository accountRepository;
@Autowired
    UserCredentialRepository userCredentialRepository;
@Autowired
TransactionRepository transactionRepository;
    @Override
    public List<UserResponse> getAllUsers() {

        List<User> userList = (List<User>) userRepository.findAll();

        List<UserResponse> userRequestDtoList = new ArrayList<>();

        for(User user : userList){
            UserResponse userGetResponseDto = convertUserModelToUserDtoGetResponse(user);
            userGetResponseDto.setId(user.getId());
            userRequestDtoList.add(userGetResponseDto);
        }
        return userRequestDtoList;
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        List<Account> accountList = (List<Account>) accountRepository.findAll();

        List<AccountResponse> accontRequestDtoList = new ArrayList<>();
        for(Account account: accountList){
            AccountResponse accountResponse = convertAccountModelToAccountDtoGetResponse(account);
            accontRequestDtoList.add(accountResponse);
        }
        return accontRequestDtoList;
    }

    @Override
    public List<UserCredentialResponse> getAllUsersCredentials() {
        List<UserCredential> userCredentialList= (List<UserCredential>)userCredentialRepository .findAll();

        List<UserCredentialResponse> userCredentialRequestlist = new ArrayList<>();
        for(UserCredential userCredential: userCredentialList){
            UserCredentialResponse userCredentialResponse = convertUserCredentialModelToUserCredentialDtoGetResponse(userCredential);
            userCredentialRequestlist.add(userCredentialResponse);
        }
        return userCredentialRequestlist;
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactionList= (List<Transaction>)transactionRepository .findAll();

        List<TransactionResponse> TransactionRequestlist = new ArrayList<>();
        for(Transaction transaction: transactionList){
            TransactionResponse transactionResponse = convertTransactionModelToTransactionDtoGetResponse(transaction);
            TransactionRequestlist.add(transactionResponse);
        }
        return TransactionRequestlist;

    }

    @Override
    public List<AccountResponse> getAccountsByUserId(Integer userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        return accounts.stream().map(this::convertAccountModelToAccountDtoGetResponse).collect(Collectors.toList());

    }

    private TransactionResponse convertTransactionModelToTransactionDtoGetResponse(Transaction transaction) {
        User user = userRepository.findById(transaction.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TransactionResponse transactionResponse=new TransactionResponse();
        transactionResponse= TransactionResponse.builder().fullName(user.getFirstName()+' '+user.getLastName()).transactionType(String.valueOf(TransactionType.valueOf(transaction.getTransactionType().name()))).accountNumber(transaction.getAccountNumber()).amount(transaction.getAmount())
            .transactionDateTime(transaction.getTransactionDateTime()).transactionStatus(transaction.getTransactionStatus()).build();
    return transactionResponse;
    }

    private UserCredentialResponse convertUserCredentialModelToUserCredentialDtoGetResponse(UserCredential userCredential) {
    UserCredentialResponse userCredentialResponse=new UserCredentialResponse();
        User user = userCredential.getUser();
    userCredentialResponse=UserCredentialResponse.builder().fullName(user.getFirstName()+' '+user.getLastName())
            .userName(userCredential.getUserName()).password(userCredential.getPassword())
            .loginDateTime(userCredential.getLoginDateTime()).createdAt(userCredential.getCreatedAt()).updatedAt(userCredential.getUpdatedAt()).build();
    return userCredentialResponse;
    }

    private AccountResponse convertAccountModelToAccountDtoGetResponse(Account account) {
        AccountResponse accountResponse=new AccountResponse();
        User user = account.getUser();
            accountResponse= AccountResponse.builder().id(account.getId()).name(user.getFirstName()+' '+user.getLastName())
                .accountType(account.getAccountType()).balance(account.getBalance()).accountNumber(account.getAccountNumber())
                .rateOfInterest(account.getRateOfInterest()).branchId(account.getBranchId()).openingDate(account.getOpeningDate()).closingDate(account.getClosingDate()).build();
        return accountResponse;

    }



    private UserResponse convertUserModelToUserDtoGetResponse(User user) {
        UserResponse userResponse=new UserResponse();

         userResponse= UserResponse.builder()
                .firstName(user.getFirstName()).middleName(user.getMiddleName()).lastName(user.getLastName())
                .mobileNumber(user.getMobileNumber()).email(user.getEmail()).gender(user.getGender()).dateOfBirth(user.getDateOfBirth()
                        ).cin(user.getCin()).aadhaarCard(user.getAdhaarCard()).createdAt(user.getCreatedAt()).updatedAt(user.getUpdatedAt()).build();
                 return userResponse;

    }
}
