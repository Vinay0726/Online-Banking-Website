package com.mkpits.bank.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateResponse {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String mobile;
    private String aadhar;
    private String state;
    private String city;
    private String pinCode;
    private String address;
    private String username;
    private String email;
    private String password;
    private String cin;
    private String accountNumber;
    private String branchId;
    private String accountType;
    private double rateOfInterest;
    private LocalDateTime openingDate;
    private double balance;

}
