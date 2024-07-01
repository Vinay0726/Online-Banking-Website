package com.mkpits.bank.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;


    @Column(name = "last_name")
    private String lastName;


    @Column(name = "mobile_number")
    private String mobileNumber;


    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;


    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;


    @Column(name = "cin")
    private String cin;


    @Column(name = "adhaar_card")
    private String adhaarCard;


    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Address> addressList;
}

