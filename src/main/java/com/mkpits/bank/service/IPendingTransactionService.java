package com.mkpits.bank.service;

import com.mkpits.bank.dto.request.TransactionRequest;
import com.mkpits.bank.model.TransactionType;

public interface IPendingTransactionService {
    void createPendingRequest(TransactionRequest transactionRequest, String username, TransactionType transactionType);

    Object getAllPendingRequests();

    void approveRequest(Long requestId);

    void rejectRequest(Long requestId);
}
