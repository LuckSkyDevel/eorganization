package com.eorganization.portifolio.payload;

public class AuthResponse {
    private String accessToken;
    private String refreshToken;

    public AuthResponse() {
    }

    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String t) {
        this.accessToken = t;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String t) {
        this.refreshToken = t;
    }
}
