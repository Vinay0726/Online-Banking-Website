package com.mkpits.bank.repository;

import com.mkpits.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account,Integer> {
//    Account findByUserId(Integer userId);


    long countByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

//for getting last four account number unique increase number if 0001 then 0002
    @Query("SELECT MAX(a.accountNumber) FROM Account a WHERE a.accountNumber LIKE CONCAT(:prefix, '%')")
    String findMaxAccountNumberByPrefix(String prefix);

    Optional<Account> findByUserId(Integer id);

    List<Account> findAccountBalanceByUserId(Integer userId);
    //for getting accounts by user id
    List<Account> findAllByUserId(Integer userId);

    //get total balance of all accounts
    @Query("SELECT SUM(a.balance) FROM Account a")
    BigDecimal findTotalBalance();

    Optional<Account> findFirstByUserId(Integer senderUserId);

    Optional<Account> findByAccountNumber(String senderAccountNumber);

//for account balance
@Query("SELECT a.balance FROM Account a WHERE a.accountNumber = :accountNumber")
BigDecimal findBalanceByAccountNumber(@Param("accountNumber") String accountNumber);

//
long countByUserId(Integer userId);
}
