package com.springboot.ecommerce.config;


import com.springboot.ecommerce.constants.BootstrapRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/home/**", "/registration/**", "/product/**", "/css/**", "/js/**", "/fonts/**", "/images/**", "/img/**").permitAll()
                        .requestMatchers("/category-management/**", "/tag-management/**", "/order-management/**", "/admin").hasAnyRole(BootstrapRole.ADMIN.getName(), BootstrapRole.STAFF.getName())
                        .requestMatchers("/product-management/**").hasAnyRole(BootstrapRole.ADMIN.getName(), BootstrapRole.STAFF.getName())
                        .requestMatchers("/cart/**", "/setCartSession", "/account/**", "/order/**").hasAnyRole(BootstrapRole.CUSTOMER.getName(), BootstrapRole.ADMIN.getName(), BootstrapRole.STAFF.getName())
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/setCartSession", true)
                        .failureHandler(authenticationFailureHandler)
                        .usernameParameter("email")
                        .passwordParameter("password")
                )
                .rememberMe(re -> re
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7))
                        .key("367639792442264528482B4D6251655468576D5A7134743777217A25432A462D")
                        .rememberMeParameter("remember-me")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .clearAuthentication(true)
                        .deleteCookies("SESSION", "remember-me")
                        .logoutSuccessUrl("/home")
                )
                .build();
    }
}
