package com.mkpits.bank.repository;

import com.mkpits.bank.model.EmployeeCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeCredentialRepository extends JpaRepository<EmployeeCredential,Integer> {
}
