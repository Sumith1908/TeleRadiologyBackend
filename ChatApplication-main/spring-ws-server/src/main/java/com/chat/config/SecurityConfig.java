package com.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.chat.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
        // Disable Cross Site Request Forgery (CSRF)
        http.csrf(csrf -> csrf.disable());

        // Disable Cross-Origin Resource Sharing (CORS)
        http.cors(cors -> cors.disable());

        http.authorizeHttpRequests(configurer -> configurer
                .anyRequest().permitAll());

        http.addFilterBefore(jwtFilter, BasicAuthenticationFilter.class);
        return http.build();
    }
}
