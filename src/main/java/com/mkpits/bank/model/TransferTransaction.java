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
@Table(name = "transfer_transaction")
public class TransferTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sender_id", nullable = false)
    private Integer senderId;

    @Column(name = "sender_account_number", length = 12, nullable = false)
    private String senderAccountNumber;

    @Column(name = "receiver_id", nullable = false)
    private Integer receiverId;

    @Column(name = "receiver_account_number", length = 12, nullable = false)
    private String receiverAccountNumber;

    @Column(name = "transfer_amount", nullable = false)
    private Double transferAmount;

    @Column(name = "sender_balance", nullable = false)
    private BigDecimal senderBalance;

    @Column(name = "receiver_balance", nullable = false)
    private BigDecimal receiverBalance;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
