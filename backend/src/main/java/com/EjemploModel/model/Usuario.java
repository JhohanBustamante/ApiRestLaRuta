package com.EjemploModel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
    @ManyToMany(mappedBy = "usuarios")
    @JsonBackReference
    private List<Comunidad> comunidades;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    @Column(unique = true)
    private String correo;
    private String contrasena;
    @Column(unique = true)
    private String apodo;
    private double altura;
    private String localidad;
    private String sexo;
    private String nacimiento;
    private String nombre_2;
    private String apellido_2;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Reporte> reportes;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<ComunidadUsuario> comunidadesUsuario;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Publicacion> publicaciones;
}
