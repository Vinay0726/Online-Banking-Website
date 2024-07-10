package com.mkpits.bank.repository;

import com.mkpits.bank.model.UserCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCityRepo extends JpaRepository<UserCity,Integer> {
   Optional<UserCity> findByName(String name);
}
