package com.example.spring.Jwt;

public class UsernameAndPasswordRequest {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsernameAndPasswordRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String username;
    private String password;
}
