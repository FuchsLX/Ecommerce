package com.springboot.ecommerce.config;


import com.springboot.ecommerce.constants.BootstrapPermission;
import com.springboot.ecommerce.security.loginError.CustomAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/home/**", "/registration/**", "/review/pd/**",  "/product/**", "/search/**", "/css/**", "/js/**", "/fonts/**", "/img/**", "/assets/**").permitAll()
                        .requestMatchers("/admin").hasAnyAuthority(BootstrapPermission.ADMIN_READ.getName(), BootstrapPermission.ADMIN_WRITE.getName(), BootstrapPermission.STAFF_READ.getName(), BootstrapPermission.STAFF_WRITE.getName())
                        .requestMatchers("/product-management/**", "/category-management/**", "/tag-management/**",  "/order-management/**").hasAnyAuthority(BootstrapPermission.STAFF_READ.getName(), BootstrapPermission.STAFF_WRITE.getName())
                        .requestMatchers("/staff-management/**", "/analytics/**", "/role-management/**").hasAnyAuthority(BootstrapPermission.ADMIN_WRITE.getName(), BootstrapPermission.ADMIN_READ.getName())
                        .requestMatchers("/order/**").hasAnyAuthority(BootstrapPermission.CUSTOMER_WRITE.getName(), BootstrapPermission.CUSTOMER_READ.getName(), BootstrapPermission.STAFF_WRITE.getName(), BootstrapPermission.STAFF_READ.getName())
                        .requestMatchers("/cart/**", "/account/**").hasAnyAuthority(BootstrapPermission.CUSTOMER_READ.getName(), BootstrapPermission.CUSTOMER_WRITE.getName())
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/setCartSession", true)
                        .failureHandler(this.authenticationFailureHandler())
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

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
}
