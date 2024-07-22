package com.mkpits.bank.repository;

import com.mkpits.bank.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential,Integer> {

   Optional<UserCredential> findByUserId(Integer userId);



   Optional<UserCredential> findByUserName(String username);
}
