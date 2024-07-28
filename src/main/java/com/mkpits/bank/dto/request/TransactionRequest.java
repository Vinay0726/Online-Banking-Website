package com.mkpits.bank.dto.request;

import com.mkpits.bank.model.TransactionType;
import com.mkpits.bank.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {
    private String accountNumber;
    private Double amount;



}