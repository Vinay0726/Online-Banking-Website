package com.mkpits.bank.repository;

import com.mkpits.bank.model.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStateRepo extends JpaRepository<UserState,Integer> {
    Optional<UserState> findByName(String name);
}
