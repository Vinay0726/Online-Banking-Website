package com.mkpits.bank.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(name = "account_number", length = 14, nullable = false)
        private String accountNumber;

        @Column(name = "amount", precision = 15, scale = 2, nullable = false)
        private BigDecimal amount;

        @Column(name = "transaction_date_time", nullable = false)
        private LocalDateTime transactionDateTime;

        @Enumerated(EnumType.STRING)
        @Column(name = "transaction_type", nullable = false)
        private TransactionType transactionType;

        @Column(name = "transaction_status", length = 12, nullable = false)
        private String transactionStatus;

        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt;

        @Column(name = "created_by", nullable = false)
        private Integer createdBy;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @Column(name = "updated_by")
        private Integer updatedBy;
    }



