package com.example.buysell.dto;

import lombok.Getter;

@Getter
public class AuthResponse {
    private String message;
    private String accessToken;
    private String refreshToken;

    // Конструктор для Access Token и Refresh Token
    public AuthResponse(String message, String accessToken, String refreshToken) {
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Конструктор для только Access Token
    public AuthResponse(String message, String accessToken) {
        this.message = message;
        this.accessToken = accessToken;
    }

    // Геттеры и сеттеры
    public void setMessage(String message) {
        this.message = message;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
