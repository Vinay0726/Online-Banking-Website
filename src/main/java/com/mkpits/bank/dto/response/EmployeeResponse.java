package com.mkpits.bank.dto.response;

import com.mkpits.bank.model.EmployeeAddress;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String gender;
    private LocalDate dateOfBirth;;
    private String aadhaarCard;
    private LocalDateTime createdAt;


    // New fields for address details
    private String address;
    private String state;
    private String city;
    private String pinCode;


}
