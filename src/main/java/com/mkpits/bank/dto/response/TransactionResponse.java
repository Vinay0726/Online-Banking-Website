package com.mkpits.bank.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mkpits.bank.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer userId;
    private String fullName;
    private String accountNumber;
    private BigDecimal amount;
    private LocalDateTime transactionDateTime;
    private String transactionType;
    private String transactionStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
