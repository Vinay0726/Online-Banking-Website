package com.mkpits.bank.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferRequest {

    private int senderUserId;
    private String senderAccountNumber;

    private int receiverUserId;
    private String receiverAccountNumber;


    private Double amount;

}
