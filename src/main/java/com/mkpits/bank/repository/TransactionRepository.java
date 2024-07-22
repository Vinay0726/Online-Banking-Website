package com.mkpits.bank.repository;

import com.mkpits.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {


    @Query("SELECT t FROM Transaction t ORDER BY t.transactionDateTime DESC LIMIT 5")
    List<Transaction> findTop5ByOrderByTransactionDateTimeDesc();

    //get transaction
    List<Transaction> findByAccountNumber(String senderAccountNumber);

    //for credit debit transaction getting
    @Query("SELECT t FROM Transaction t ORDER BY t.transactionDateTime DESC LIMIT 5")
    List<Transaction> findByUserId(Long userId);

    //after user login show total credit debit transaction
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.transactionType = 'Credit'")
    BigDecimal sumCreditByUserId(@Param("userId") Integer userId);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.transactionType = 'Debit'")
    BigDecimal sumDebitByUserId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.user.id = :userId")
    Integer countTransactionsByUserId(@Param("userId") Integer userId);

//for account balance

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.accountNumber = :accountNumber AND t.transactionType = 'Credit'")
    BigDecimal sumCreditByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.accountNumber = :accountNumber AND t.transactionType = 'Debit'")
    BigDecimal sumDebitByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.accountNumber = :accountNumber")
    Integer countTransactionsByAccountNumber(@Param("accountNumber") String accountNumber);

//getting recent transaction for user dashboard
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId ORDER BY t.transactionDateTime DESC LIMIT 5")
    List<Transaction> findTop5ByUserIdOrderByTransactionDateTimeDesc(@Param("userId") Long userId);


    long countByUserId(Integer userId);
}
