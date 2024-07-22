package com.mkpits.bank.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "pincode", nullable = false, length = 7)
    private String pincode;

    @Column(name="city_id")
    private Integer cityId;
    @ManyToOne
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private UserCity city;

    @Column(name="district_id")
    private Integer districtId;
    @ManyToOne
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private UserDistrict district;

    @Column(name="state_id")
    private Integer stateId;
    @ManyToOne
    @JoinColumn(name = "state_id", insertable = false, updatable = false)
    private UserState state;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by",nullable = false)
    private Integer createdBy;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "updated_by")
    private Integer updatedBy;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "state_id", insertable = false, updatable = false)
//    private UserState state;
@ManyToOne
@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
private UserCredential userCredential;

}

