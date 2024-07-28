package com.mkpits.bank.service.impl;

import com.mkpits.bank.dto.request.TransactionRequest;
import com.mkpits.bank.model.*;
import com.mkpits.bank.repository.AccountRepository;
import com.mkpits.bank.repository.PendingTransactionRequestRepository;
import com.mkpits.bank.repository.TransactionRepository;
import com.mkpits.bank.repository.UserCredentialRepository;
import com.mkpits.bank.service.IPendingTransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class PendingTransactionService implements IPendingTransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    private PendingTransactionRequestRepository pendingTransactionRequestRepository;
@Autowired
    AccountRepository accountRepository;
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Override
    public void createPendingRequest(TransactionRequest transactionRequest, String username, TransactionType transactionType) {
        Optional<UserCredential> userCredentialOpt = userCredentialRepository.findByUserName(username);

        if (userCredentialOpt.isPresent()) {
            UserCredential userCredential = userCredentialOpt.get();
            User user = userCredential.getUser();

            PendingTransactionRequest pendingRequest = PendingTransactionRequest.builder()
                    .user(user)
                    .accountNumber(transactionRequest.getAccountNumber())
                    .amount(BigDecimal.valueOf(transactionRequest.getAmount()))
                    .transactionType(transactionType)
                    .requestDateTime(LocalDateTime.now())
                    .status("PENDING")
                    .createdAt(LocalDateTime.now())
                    .createdBy(user.getId())
                    .build();

            pendingTransactionRequestRepository.save(pendingRequest);
        } else {
            throw new RuntimeException("User not found");
        }
    }


    public List<PendingTransactionRequest> getAllPendingRequests() {
        return pendingTransactionRequestRepository.findAllByStatus("PENDING");
    }

    @Transactional
    public void approveRequest(Long requestId) {
        Optional<PendingTransactionRequest> requestOpt = pendingTransactionRequestRepository.findById(requestId);

        if (requestOpt.isPresent()) {
            PendingTransactionRequest request = requestOpt.get();
            processTransaction(request);
            request.setStatus("APPROVED");
            pendingTransactionRequestRepository.save(request);
        } else {
            throw new RuntimeException("Request not found");
        }
    }
    @Transactional
    public void rejectRequest(Long requestId) {
        Optional<PendingTransactionRequest> requestOpt = pendingTransactionRequestRepository.findById(requestId);

        if (requestOpt.isPresent()) {
            PendingTransactionRequest request = requestOpt.get();
            request.setStatus("REJECTED");
            pendingTransactionRequestRepository.save(request);
        } else {
            throw new RuntimeException("Request not found");
        }
    }



    @Transactional
    private void processTransaction(PendingTransactionRequest request) {
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(request.getAccountNumber());

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            User user = request.getUser();

            // Check if the account belongs to the logged-in user
            if (!account.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Account number not valid for the logged-in user");
            }

            BigDecimal amount = request.getAmount();
            if (request.getTransactionType() == TransactionType.Debit && account.getBalance().compareTo(amount) < 0) {
                throw new RuntimeException("Insufficient funds");
            }

            BigDecimal newBalance = request.getTransactionType() == TransactionType.Credit
                    ? account.getBalance().add(amount)
                    : account.getBalance().subtract(amount);

            account.setBalance(newBalance);
            accountRepository.save(account);

            Transaction transaction = Transaction.builder()
                    .user(user)
                    .accountNumber(request.getAccountNumber())
                    .amount(amount)
                    .transactionDateTime(LocalDateTime.now())
                    .transactionType(request.getTransactionType())
                    .transactionStatus("SUCCESS")
                    .createdAt(LocalDateTime.now())
                    .createdBy(request.getCreatedBy())
                    .build();
            transactionRepository.save(transaction);
        } else {
            throw new RuntimeException("Account not found");
        }
    }

}

