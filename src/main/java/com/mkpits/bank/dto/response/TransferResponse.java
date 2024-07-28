package com.mkpits.bank.dto.response;

import com.mkpits.bank.model.Transaction;
import com.mkpits.bank.model.TransferTransaction;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TransferResponse {
    private Integer id;
    private String fullName;
    private int senderId;
    private String senderAccountNumber;
    private int receiverId;
    private String receiverAccountNumber;
    private Double transferredAmount;
    private BigDecimal senderBalance;
    private BigDecimal receiverBalance;


    private List<TransferTransaction> transactions; // Assuming Transaction is your entity for transactions
}