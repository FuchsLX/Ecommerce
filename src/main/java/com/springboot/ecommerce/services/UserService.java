package com.springboot.ecommerce.services;


import com.springboot.ecommerce.entities.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDetails loadUserDetailsByUsername(String email);

    int enableUser(String email);

    User findByEmail(String email);

    User saveUser(User user);

}
