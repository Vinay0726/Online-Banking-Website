package com.mkpits.bank.repository;

import com.mkpits.bank.model.PendingTransactionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingTransactionRequestRepository extends JpaRepository<PendingTransactionRequest, Long> {
    List<PendingTransactionRequest> findAllByStatus(String status);
}
