package com.springboot.ecommerce.repositories;

import com.springboot.ecommerce.entities.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    @Transactional
    @Query("select u from User as u where u.email = ?1")
    Optional<User> loadByUsername(String email);

    @Transactional
    @Modifying
    @Query("update User as u set u.isEnabled = true where u.email = ?1")
    int enableUser(String email);
}
