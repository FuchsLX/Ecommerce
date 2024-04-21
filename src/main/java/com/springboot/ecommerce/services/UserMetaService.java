package com.springboot.ecommerce.services;

import com.springboot.ecommerce.entities.user.UserMeta;
import org.springframework.stereotype.Service;

@Service
public interface UserMetaService {

    UserMeta getUserMeta(String userMateId);

    UserMeta getUserMetaByCurrentUser(String currentUserId);

    void saveUserMeta(UserMeta userMeta);
}
