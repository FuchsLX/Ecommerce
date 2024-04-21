//package com.springboot.ecommerce.tmp;
//
//
//import com.springboot.ecommerce.tmp.OldUser;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Repository
//public interface OldUserRepository extends JpaRepository<OldUser, String> {
//    Optional<OldUser> findByEmail(String email);
//
//    @Transactional
//    @Query("select u " +
//            "from OldUser as u " +
//            "where u.email = ?1")
//    OldUser findByUsername(String email);
//
//
//    @Transactional
//    @Modifying
//    @Query("update OldUser as u " +
//            "set u.enabled = true " +
//            "where u.email = ?1")
//    int enableUser(String email);
//}
