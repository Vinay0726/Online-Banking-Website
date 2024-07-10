package com.mkpits.bank.dto.request;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String gender;
    private String dateOfBirth;
    private String adhaarCard;

//    employee credential
    private String userName;
    private String password;
    private String passwordSalt;
    private LocalDateTime loginDateTime;

//    employee address
    private String address;
    private String state;
    private String city;
    private String pinCode;





}
