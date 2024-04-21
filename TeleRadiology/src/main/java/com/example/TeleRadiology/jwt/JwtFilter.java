package com.example.TeleRadiology.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;;

//checks if token already exists
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    public JwtService jwtService;

    final String AUTH = "authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader(AUTH);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            try {
                jwtService.validateToken(token);
            } catch (Exception e) {
                response.addHeader("Token", "Unauthorized");
            }
            // System.out.println("Hello");
        }

        filterChain.doFilter(request, response);

    }

    // @Override
    // protected boolean shouldNotFilter(HttpServletRequest request) throws
    // ServletException {
    // return request.getRequestURI().equals("/teleRadiology/loginCredentials");
    // }
}
