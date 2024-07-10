package com.mkpits.bank.repository;

import com.mkpits.bank.model.EmployeeCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeCredentialRepository extends JpaRepository<EmployeeCredential,Integer> {

    Optional<EmployeeCredential> findByEmployeeId(Integer id);
}
