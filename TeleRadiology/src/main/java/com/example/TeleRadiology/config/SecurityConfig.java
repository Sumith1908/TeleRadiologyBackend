package com.example.TeleRadiology.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.TeleRadiology.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {

        http.cors(config -> config.disable());
        http.csrf(config -> config.disable());
        http.authorizeHttpRequests(
                configure -> configure
//                        .requestMatchers("/teleRadiology/loginCredentials").permitAll()
//                        .requestMatchers("/teleRadiology/createPatientCred").permitAll()
//                        .requestMatchers("/teleRadiology/otpVerification/*").permitAll()
//                        .requestMatchers("/teleRadiology/giveConsent").permitAll()
//                        .anyRequest().authenticated());
                        .anyRequest().permitAll());
        http.httpBasic(Customizer.withDefaults());

        http.addFilterBefore(jwtFilter, BasicAuthenticationFilter.class);
        return http.build();
    }
}
