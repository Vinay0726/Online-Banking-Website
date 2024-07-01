package com.mkpits.bank.dto.response;

import com.mkpits.bank.model.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCredentialResponse {

    private Integer id;
    private Employee employee;
    private String fullName;
    private String userName;
    private String password;
    private LocalDateTime loginDateTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
