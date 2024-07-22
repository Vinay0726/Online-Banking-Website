package com.mkpits.bank.dto.request;

import com.mkpits.bank.model.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private Long userId;
    private User user;
    private Account account;
    private UserCredential userCredential;
    private UserAddress address;
    private UserState state;
    private UserDistrict district;
    private UserCity city;
}
