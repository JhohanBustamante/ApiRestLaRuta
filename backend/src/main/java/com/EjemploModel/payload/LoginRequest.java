package com.EjemploModel.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String apodo;
    private String contrasena;
    private String correo;
}