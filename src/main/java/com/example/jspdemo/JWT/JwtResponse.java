package com.example.jspdemo.JWT;

public class JwtResponse {
    private String token;

    // Конструктор
    public JwtResponse(String token) {
        this.token = token;
    }


    public String getToken() {
        return token;
    }

    // Сеттер
    public void setToken(String token) {
        this.token = token;
    }
}
