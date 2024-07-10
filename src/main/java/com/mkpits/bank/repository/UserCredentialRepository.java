package com.mkpits.bank.repository;

import com.mkpits.bank.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialRepository extends JpaRepository<UserCredential,Integer> {
    UserCredential findByUserId(Integer id);
}
