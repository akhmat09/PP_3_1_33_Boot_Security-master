package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    User findByEmail(@Param("email") String email);


    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :username")
    User findByUsername(@Param("username") String username);
}