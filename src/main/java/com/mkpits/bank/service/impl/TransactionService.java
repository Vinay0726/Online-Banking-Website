package com.mkpits.bank.service.impl;

import com.mkpits.bank.model.Transaction;
import com.mkpits.bank.repository.TransactionRepository;
import com.mkpits.bank.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TransactionService implements ITransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public List<Transaction> getLast5Transactions() {
        return transactionRepository.findTop5ByOrderByTransactionDateTimeDesc();
    }
}
