package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.entities.token.Token;
import com.springboot.ecommerce.entities.user.User;
import com.springboot.ecommerce.repositories.TokenRepository;
import com.springboot.ecommerce.entities.token.TokenType;
import com.springboot.ecommerce.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public void saveUserToken(User user, String jwtToken){
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.JWT)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }


    @Override
    public Optional<Token> getToken(String token){return tokenRepository.findByToken(token);}

    @Override
    public int setConfirmedAt(String token){return tokenRepository.updateConfirmedAt(token);}

    @Override
    public List<Token> findAllValidToken(String id){return tokenRepository.findAllValidTokenByUser(id);}
}
