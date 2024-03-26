package com.example.TeleRadiology.jwt;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {
    Algorithm algorithm = Algorithm.HMAC256("secret");

    // using authentication object to create token because already spring has it
    // we can also directly send user name and role to generateToken function
    public String generateToken(Authentication auth) {
        String token = JWT.create()
                .withIssuer("TeleRadiology")
                .withClaim("email", auth.getName())
                .withClaim("role", auth.getAuthorities().stream().toList().get(0).getAuthority())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                .withIssuedAt(new Date())
                .withSubject("to authenticate")
                .sign(algorithm);
        return token;
    }

    public void validateToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("TeleRadiology")
                .build();
        DecodedJWT decode = verifier.verify(token);
        String email = decode.getClaim("email").asString();
        String role = decode.getClaim("role").asString();
        // type conversion
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
