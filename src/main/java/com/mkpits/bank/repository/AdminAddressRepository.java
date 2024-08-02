package com.mkpits.bank.repository;

import com.mkpits.bank.model.AdminAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminAddressRepository extends JpaRepository<AdminAddress,Integer> {
    Optional<AdminAddress> findByAdminId(Integer id);
}
