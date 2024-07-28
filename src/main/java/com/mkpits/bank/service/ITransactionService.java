package com.mkpits.bank.service;

import com.mkpits.bank.dto.request.TransactionRequest;
import com.mkpits.bank.dto.request.TransferRequest;
import com.mkpits.bank.dto.response.TransferResponse;
import com.mkpits.bank.model.Transaction;
import com.mkpits.bank.model.TransactionType;
import com.mkpits.bank.model.TransferTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface ITransactionService {
    List<Transaction> getLast5Transactions();



    //for transfer amount from 1st account number to 2nd account number
   public TransferResponse amountTransfer(TransferRequest transferRequest, Integer id);

   //get all transaction
    List<TransferTransaction> getAllTransactions(Long id);

//    //for credit and debit amount
//    void processTransaction(TransactionRequest transactionRequest, String username, TransactionType transactionType);
    //for get credit and debit transaction
    List<Transaction> getTransactionsForUser(String username);



    //after user login get
    BigDecimal getTotalCreditByUserId(Integer userId);

    BigDecimal getTotalDebitByUserId(Integer userId);

    int getTotalTransactionsByUserId(Integer userId);

    //for account balance info
    BigDecimal getTotalCreditByAccountNumber(String defaultAccountNumber);

    BigDecimal getTotalDebitByAccountNumber(String defaultAccountNumber);

    int getTotalTransactionsByAccountNumber(String defaultAccountNumber);

    BigDecimal getBalanceByAccountNumber(String defaultAccountNumber);



}
