package com.EjemploModel.payload;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String apodo;
    private List<String> roles;

    public JwtResponse(String token, String apodo, List<String> roles) {
        this.token = token;
        this.apodo = apodo;
        this.roles = roles;
    }
}