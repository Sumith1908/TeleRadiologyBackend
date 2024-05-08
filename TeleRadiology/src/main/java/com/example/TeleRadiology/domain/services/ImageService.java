package com.example.TeleRadiology.domain.services;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

import com.example.TeleRadiology.dto.GetAllReportsReq;
import com.example.TeleRadiology.dto.GetAllReportsRes;
import com.example.TeleRadiology.exception.GlobalException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final WebClient webClient;

    // public Object callImageServerPost(String path, Object request) {
    // GetAllReportsRes response = null;
    // try {
    // response = webClient.post()
    // .uri("http://localhost:8080/images" + path)
    // .bodyValue(request)
    // .retrieve()
    // .bodyToMono(GetAllReportsRes.class)
    // .block();
    // } catch (Exception e) {
    // throw new GlobalException("Failed to get response");
    // }

    // return response;
    // }
    public <T> T callImageServerPost(String path, Object request, Class<T> responseType) {
        try {
            return webClient.post()
                    .uri("http://192.168.0.113:8080/images" + path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();
        } catch (Exception e) {
            throw new GlobalException("Failed to get response");
        }
    }

    public <T> T callImageServerGet(String path, Class<T> responseType) {
        try {
            return webClient.get()
                    .uri("http://192.168.0.113:8080/images" + path)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();
        } catch (Exception e) {
            throw new GlobalException("Failed to get response");
        }
    }
    // private boolean authenticate() {
    // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    // String authToken = auth.getPrincipal().toString();
    // Boolean authenticated = false;
    // try {
    // authenticated = webClient.get()
    // .uri("http://localhost:8081/teleRadiology/authenticate")
    // .headers(headers -> headers.setBearerAuth(authToken))
    // .retrieve()
    // .bodyToMono(Boolean.class)
    // .block();
    // } catch (Exception e) {
    // throw new GlobalException("Unauthorized");
    // }

    // return authenticated;
    // }
}