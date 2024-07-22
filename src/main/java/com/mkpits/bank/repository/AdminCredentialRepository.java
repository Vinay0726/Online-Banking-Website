package com.mkpits.bank.repository;

import com.mkpits.bank.model.AdminCredential;
import com.mkpits.bank.model.EmployeeCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminCredentialRepository extends JpaRepository<AdminCredential,Integer> {
    Optional<AdminCredential> findByUserName(String username);
}
