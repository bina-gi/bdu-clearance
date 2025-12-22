package com.bdu.clearance.jwt;

import java.util.List;

public class LoginResponse {

    private final String accessToken;
    private final String tokenType = "Bearer";
    private final String username;
    private final List<String> roles;
    private final long expiresAt; // epoch millis

    public LoginResponse(String username, List<String> roles, String accessToken, long expiresAt) {
        this.username = username;
        this.roles = roles;
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public long getExpiresAt() {
        return expiresAt;
    }
}
