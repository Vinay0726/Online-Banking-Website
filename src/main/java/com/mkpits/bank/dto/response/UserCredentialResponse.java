package com.mkpits.bank.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialResponse {

    private Integer id;
    private String fullName;
    private String userName;
    private String password;
    private LocalDateTime loginDateTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer updatedBy;

}
