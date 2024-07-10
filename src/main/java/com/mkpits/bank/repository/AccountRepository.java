package com.mkpits.bank.repository;

import com.mkpits.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByUserId(Integer userId);


    long countByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

//for getting last four account number unique increase number if 0001 then 0002
    @Query("SELECT MAX(a.accountNumber) FROM Account a WHERE a.accountNumber LIKE CONCAT(:prefix, '%')")
    String findMaxAccountNumberByPrefix(String prefix);
}
