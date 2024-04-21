package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.entities.user.UserMeta;
import com.springboot.ecommerce.repositories.UserMetaRepository;
import com.springboot.ecommerce.services.UserMetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMetaServiceImpl implements UserMetaService {


    private final UserMetaRepository userMetaRepository;

    @Override
    public UserMeta getUserMetaByCurrentUser(String currentUserId) {
        return userMetaRepository.getUserMetaByUser_Id(currentUserId);
    }

    @Override
    public void saveUserMeta(UserMeta userMeta){
        userMetaRepository.save(userMeta);
    }

    @Override
    public UserMeta getUserMeta(String userMateId) {
        Optional<UserMeta> userMetaOptional = userMetaRepository.findById(userMateId);
        if (userMetaOptional.isPresent()){
            return userMetaOptional.get();
        } else {
            throw new IllegalStateException("User meta not found for : " + userMateId);
        }
    }
}
