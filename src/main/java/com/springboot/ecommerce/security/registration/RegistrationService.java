package com.springboot.ecommerce.security.registration;

import com.springboot.ecommerce.constants.BootstrapRole;
import com.springboot.ecommerce.controller.dto.RegistrationDto;
import com.springboot.ecommerce.entities.user.User;
import com.springboot.ecommerce.exception.EmailAlreadyTakenException;
import com.springboot.ecommerce.exception.EmailNotValidException;
import com.springboot.ecommerce.utils.JwtService;
import com.springboot.ecommerce.security.registration.emailRegistration.EmailDetails;
import com.springboot.ecommerce.security.registration.emailRegistration.EmailService;
import com.springboot.ecommerce.security.registration.emailRegistration.EmailValidator;
import com.springboot.ecommerce.entities.token.Token;
import com.springboot.ecommerce.repositories.TokenRepository;
import com.springboot.ecommerce.services.RoleService;
import com.springboot.ecommerce.services.TokenService;
import com.springboot.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.springboot.ecommerce.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    private final RoleService roleService;

    public String register(RegistrationDto request){
        boolean isEmailValid = emailValidator.test(request.getEmail());
        if (!isEmailValid)
            throw new EmailNotValidException();

        var userExists = userService.findByEmail(request.getEmail());
        if (userExists != null) {
            throw new EmailAlreadyTakenException();
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleService.findByName(BootstrapRole.CUSTOMER.name()))
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .build();

        var savedUser = userService.saveUser(user);
        var jwtToken = jwtService.generateToken(user);
        tokenService.saveUserToken(user, jwtToken);

        String link  = "http://localhost:8080/registration/confirm?token=" + jwtToken;
        EmailDetails verifyEmail = EmailDetails.builder()
                .messageBody(emailService.buildVerifyEmail(request.getFirstName(), link))
                .recipient(request.getEmail())
                .subject("Confirmed your email")
                .build();
        emailService.sendSimpleMail(verifyEmail);
        return jwtToken;
    }

    public void confirmedToken(String jwtToken){
        Token token = tokenService.getToken(jwtToken)
                .orElseThrow(() -> new IllegalStateException(TOKEN_NOT_FOUND_MESSAGE));
        if (token.isConfirmedRegister())
            throw new IllegalStateException(EMAIL_ALREADY_TAKEN_MESSAGE);
        if (token.isExpired() || token.isRevoked())
            throw new IllegalStateException(TOKEN_EXPIRED_MESSAGE);

        tokenService.setConfirmedAt(jwtToken);
        userService.enableUser(token.getUser().getEmail());
    }

    private void revokeAllUserToken(User user){
        var validUserTokens = tokenService.findAllValidToken(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
