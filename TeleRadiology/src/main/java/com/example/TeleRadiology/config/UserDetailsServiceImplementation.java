package com.example.TeleRadiology.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.domain.repositories.TeleRadiologyRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    @Autowired
    private TeleRadiologyRepository teleRep;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Credentials cred = teleRep.getUserByEmail(email);
        UserDetails user = User.withUsername(email)
                .password(cred.getPassword())
                .authorities(cred.getRole())
                .build();
        return user;
    }

}
