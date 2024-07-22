package com.mkpits.bank.repository;

import com.mkpits.bank.model.Employee;
import com.mkpits.bank.model.Transaction;
import com.mkpits.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("SELECT CAST(u.createdAt AS date) AS date, COUNT(u) AS userCount " +
            "FROM User u GROUP BY CAST(u.createdAt AS date) ORDER BY CAST(u.createdAt AS date)")
    List<Object[]> findDailyUserData();

//    get last 5 users
    @Query("SELECT u FROM User u ORDER BY u.createdAt DESC LIMIT 5")
    List<User> findTop5ByOrderByUsersDateTimeDesc();

}
