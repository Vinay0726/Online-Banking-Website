package com.mkpits.bank.repository;

import com.mkpits.bank.model.TransferTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferTransactionRepository extends JpaRepository<TransferTransaction,Integer> {

    //for transfer amount
    List<TransferTransaction> findBySenderId(Long id);
}
