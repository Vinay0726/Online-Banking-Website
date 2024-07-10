package com.mkpits.bank.repository;

import com.mkpits.bank.model.EmployeeAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress,Integer> {


    Optional<EmployeeAddress> findByEmployeeId(Integer id);
}
