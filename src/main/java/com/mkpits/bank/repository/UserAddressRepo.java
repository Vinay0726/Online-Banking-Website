package com.mkpits.bank.repository;

import com.mkpits.bank.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepo extends JpaRepository<UserAddress,Integer> {
    UserAddress findByUserId(Integer id);
}
