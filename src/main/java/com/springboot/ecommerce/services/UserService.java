package com.springboot.ecommerce.services;


import com.springboot.ecommerce.controller.dto.StaffDTO;
import com.springboot.ecommerce.entities.user.Role;
import com.springboot.ecommerce.entities.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDetails loadUserDetailsByUsername(String email);

    void enableUser(String email);

    User findByEmail(String email);

    User saveUser(User user);

    void saveStaffAccount(StaffDTO staff);

    void deleteStaffAccountById(String userId);

    List<StaffDTO> getAllStaffAccount();

    StaffDTO getStaffAccById(String id);


    void createSuperAccountIfNotFound(String email, Role role);
}
