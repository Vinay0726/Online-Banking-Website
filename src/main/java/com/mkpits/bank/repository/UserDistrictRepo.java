package com.mkpits.bank.repository;

import com.mkpits.bank.model.UserDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDistrictRepo extends JpaRepository<UserDistrict,Integer> {
    Optional<UserDistrict> findByName(String name);



}
