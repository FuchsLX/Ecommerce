package com.springboot.ecommerce.services;

import com.springboot.ecommerce.entities.user.UserMeta;

public interface UserMetaService {

    UserMeta getUserMeta(Long userMateId);

    UserMeta getUserMetaByCurrentUser(Long currentUserId);
}
