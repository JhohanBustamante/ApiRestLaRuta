package com.EjemploModel.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comunidad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comunidad {
    @ManyToMany
    @JsonIgnore
    @JsonManagedReference
    private List<Usuario> usuarios;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tematica;
    private Long idCreador;
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "comunidad", cascade = CascadeType.ALL)
    private List<Reporte> reportes;

    @OneToMany(mappedBy = "comunidad", cascade = CascadeType.ALL)
    private List<ComunidadUsuario> usuariosComunidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Tipo tipo;

    private String categoria;
    private String estado;
    private LocalDate fecha;

    public enum Tipo {
        CHAT_GRUPAL("Chat Grupal"),
        GRUPO("Grupo"),
        COMUNIDAD("Comunidad"),
        GRAN_COMUNIDAD("Gran Comunidad");

        private final String value;

        Tipo(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
