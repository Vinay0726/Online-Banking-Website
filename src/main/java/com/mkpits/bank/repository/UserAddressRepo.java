package com.mkpits.bank.repository;

import com.mkpits.bank.model.User;
import com.mkpits.bank.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAddressRepo extends JpaRepository<UserAddress,Integer> {
  Optional<UserAddress>  findByUserId(Integer id);


}
