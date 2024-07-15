package com.mkpits.bank.service;

import com.mkpits.bank.model.Transaction;

import java.util.List;

public interface ITransactionService {
    List<Transaction> getLast5Transactions();
}
