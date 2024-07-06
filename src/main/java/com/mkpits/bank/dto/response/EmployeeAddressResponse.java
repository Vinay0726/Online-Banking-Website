package com.mkpits.bank.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAddressResponse {

    private Integer id;
    private Integer employeeId;
    private String address;
    private String state;
    private String city;
    private String pinCode;

}
