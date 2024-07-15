package com.mkpits.bank.repository;

import com.mkpits.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {


    @Query("SELECT t FROM Transaction t ORDER BY t.transactionDateTime DESC LIMIT 5")
    List<Transaction> findTop5ByOrderByTransactionDateTimeDesc();
}
