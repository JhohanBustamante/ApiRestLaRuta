package com.EjemploModel.payload;

import lombok.Data;

@Data
public class SignupRequest {
    private String apodo;
    private String correo;
    private String contrasena;
    private String nombre;
    private String apellido;
}