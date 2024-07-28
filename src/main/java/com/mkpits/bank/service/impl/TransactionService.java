package com.mkpits.bank.service.impl;

import com.mkpits.bank.dto.request.TransactionRequest;
import com.mkpits.bank.dto.request.TransferRequest;
import com.mkpits.bank.dto.response.TransferResponse;
import com.mkpits.bank.model.*;
import com.mkpits.bank.repository.*;
import com.mkpits.bank.service.ITransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    //for credit and debit
    @Autowired
    TransactionRepository transactionRepository;

    //for transfer
    @Autowired
    TransferTransactionRepository transferTransactionRepository;

@Autowired
UserCredentialRepository userCredentialRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Transaction> getLast5Transactions() {
        return transactionRepository.findTop5ByOrderByTransactionDateTimeDesc();
    }

    @Override
    public TransferResponse amountTransfer(TransferRequest transferRequest, Integer senderUserId) {
        Optional<User> senderUserOpt = userRepository.findById(senderUserId);
        Optional<Account> senderAccountNoOpt = accountRepository.findByAccountNumber(transferRequest.getSenderAccountNumber());
        Optional<Account> receiverAccountNoOpt = accountRepository.findByAccountNumber(transferRequest.getReceiverAccountNumber());

        if (senderUserOpt.isEmpty()) {
            throw new RuntimeException("Sender user not found");
        }

        if (senderAccountNoOpt.isEmpty() || receiverAccountNoOpt.isEmpty()) {
            throw new RuntimeException("Sender or receiver account not found");
        }

        Account senderAccount = senderAccountNoOpt.get();
        Account receiverAccount = receiverAccountNoOpt.get();

        // Check if the sender account belongs to the sender user
        if (!senderAccount.getUserId().equals(senderUserId)) {
            throw new RuntimeException("The sender account does not belong to the logged-in user");
        }

        BigDecimal transferAmount = BigDecimal.valueOf(transferRequest.getAmount());

        if (senderAccount.getBalance().compareTo(transferAmount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        senderAccount.setBalance(senderAccount.getBalance().subtract(transferAmount));
        receiverAccount.setBalance(receiverAccount.getBalance().add(transferAmount));

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        TransferTransaction transferTransaction = new TransferTransaction();
        transferTransaction.setSenderId(senderAccount.getUserId());
        transferTransaction.setSenderAccountNumber(senderAccount.getAccountNumber());
        transferTransaction.setReceiverId(receiverAccount.getUserId());
        transferTransaction.setReceiverAccountNumber(receiverAccount.getAccountNumber());
        transferTransaction.setTransferAmount(transferRequest.getAmount());
        transferTransaction.setSenderBalance(senderAccount.getBalance());
        transferTransaction.setReceiverBalance(receiverAccount.getBalance());
        transferTransaction.setCreatedBy(senderUserId); // Set the appropriate user ID
        transferTransaction.setCreatedAt(LocalDateTime.now());
        transferTransaction.setUpdatedBy(senderUserId); // Set the appropriate user ID
        transferTransaction.setUpdatedAt(LocalDateTime.now());
        transferTransactionRepository.save(transferTransaction);

        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setSenderId(senderAccount.getUserId());
        transferResponse.setSenderAccountNumber(senderAccount.getAccountNumber());
        transferResponse.setReceiverId(receiverAccount.getUserId());
        transferResponse.setReceiverAccountNumber(receiverAccount.getAccountNumber());
        transferResponse.setTransferredAmount(transferRequest.getAmount());
        transferResponse.setSenderBalance(senderAccount.getBalance());
        transferResponse.setReceiverBalance(receiverAccount.getBalance());
        List<TransferTransaction> transactions = transferTransactionRepository.findBySenderId((long) senderAccount.getUserId());
        transferResponse.setTransactions(transactions);

        return transferResponse;
    }
    private List<Transaction> getTransactionsByAccountNumber(String senderAccountNumber) {
        // Implement logic to fetch transactions for the given account number
        // Example:
        return transactionRepository.findByAccountNumber(senderAccountNumber);
    }

    //get transfer transaction details
    @Override
    public List<TransferTransaction> getAllTransactions(Long id) {
        return transferTransactionRepository.findBySenderId(id);
    }


    //for credit and debit amount
//    @Transactional
//    public void processTransaction(TransactionRequest transactionRequest, String username, TransactionType transactionType) {
//        Optional<UserCredential> userCredentialOpt = userCredentialRepository.findByUserName(username);
//
//        if (userCredentialOpt.isPresent()) {
//            UserCredential userCredential = userCredentialOpt.get();
//            User user = userCredential.getUser();
//            Optional<Account> accountOpt = accountRepository.findByAccountNumber(transactionRequest.getAccountNumber());
//
//            if (accountOpt.isPresent()) {
//                Account account = accountOpt.get();
//
//                // Check if the account belongs to the logged-in user
//                if (!account.getUser().getId().equals(user.getId())) {
//                    throw new RuntimeException("Account number not valid for the logged-in user");
//                }
//
//                BigDecimal amount = BigDecimal.valueOf(transactionRequest.getAmount());
//                if (transactionType == TransactionType.Debit && account.getBalance().compareTo(amount) < 0) {
//                    throw new RuntimeException("Insufficient funds");
//                }
//
//                BigDecimal newBalance = transactionType == TransactionType.Credit
//                        ? account.getBalance().add(amount)
//                        : account.getBalance().subtract(amount);
//
//                account.setBalance(newBalance);
//                accountRepository.save(account);
//
//                Transaction transaction = new Transaction();
//                transaction.setUser(user);
//                transaction.setAccountNumber(transactionRequest.getAccountNumber());
//                transaction.setAmount(amount);
//                transaction.setTransactionDateTime(LocalDateTime.now());
//                transaction.setTransactionType(transactionType);
//                transaction.setTransactionStatus("SUCCESS");
//                transaction.setCreatedAt(LocalDateTime.now());
//                transaction.setCreatedBy(user.getId());
//                transactionRepository.save(transaction);
//            } else {
//                throw new RuntimeException("Account not found");
//            }
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }
    

    public List<Transaction> getTransactionsForUser(String username) {
        Optional<UserCredential> userCredentialOpt = userCredentialRepository.findByUserName(username);
        if (userCredentialOpt.isPresent()) {
            Integer userId = userCredentialOpt.get().getUser().getId();
            return transactionRepository.findTop5ByUserIdOrderByTransactionDateTimeDesc(Long.valueOf(userId));
        }
        throw new RuntimeException("User not found");
    }

    //after user login total credit debit transaction get
    public BigDecimal getTotalCreditByUserId(Integer userId) {
        return transactionRepository.sumCreditByUserId(userId);
    }

    public BigDecimal getTotalDebitByUserId(Integer userId) {
        return transactionRepository.sumDebitByUserId(userId);
    }

    public int getTotalTransactionsByUserId(Integer userId) {
        return transactionRepository.countTransactionsByUserId(userId);
    }

    //for account balance info
    public BigDecimal getTotalCreditByAccountNumber(String accountNumber) {
        return transactionRepository.sumCreditByAccountNumber(accountNumber);
    }

    public BigDecimal getTotalDebitByAccountNumber(String accountNumber) {
        return transactionRepository.sumDebitByAccountNumber(accountNumber);
    }

    public int getTotalTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.countTransactionsByAccountNumber(accountNumber);
    }

    public BigDecimal getBalanceByAccountNumber(String accountNumber) {
        return accountRepository.findBalanceByAccountNumber(accountNumber);
    }

}

