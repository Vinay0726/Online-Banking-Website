package com.mkpits.bank.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mkpits.bank.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer userId;
    private String name;
    private String accountType;
    private BigDecimal balance;
    private String accountNumber;
    private BigDecimal rateOfInterest;
    private String branchId;
    private LocalDate openingDate;
    private LocalDate closingDate;

}
