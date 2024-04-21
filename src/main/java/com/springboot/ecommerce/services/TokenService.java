package com.springboot.ecommerce.services;

import com.springboot.ecommerce.entities.token.Token;
import com.springboot.ecommerce.entities.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TokenService {

    void saveUserToken(User user, String jwtToken);

    Optional<Token> getToken(String tokenVal);

    int setConfirmedAt(String tokenVal);

    List<Token> findAllValidToken(String userId);
}
