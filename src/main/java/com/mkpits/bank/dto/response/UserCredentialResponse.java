package com.mkpits.bank.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mkpits.bank.model.User;
import com.mkpits.bank.model.UserCredential;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialResponse {

    private Integer id;
    private Integer userId;
    private String fullName;
    private String userName;
    private String password;
    private LocalDateTime loginDateTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer updatedBy;


  

}
