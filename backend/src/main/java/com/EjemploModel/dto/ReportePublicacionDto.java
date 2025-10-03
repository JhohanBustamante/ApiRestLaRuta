package com.EjemploModel.dto;

import java.time.LocalDate;

public class ReportePublicacionDto {
    private String apodoUsuario;
    private Long usuario_id;
    private Long publicacionId;
    private String contenido;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String estado;
    private LocalDate fecha;

    public ReportePublicacionDto(String apodoUsuario, Long usuario_id, Long publicacionId,
                                String contenido, String nombre, String descripcion,
                                String categoria, String estado, LocalDate fecha) {
        this.apodoUsuario = apodoUsuario;
        this.usuario_id = usuario_id;
        this.publicacionId = publicacionId;
        this.contenido = contenido;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.estado = estado;
        this.fecha = fecha;
    }

    // Getters y setters (puedes usar Lombok si prefieres)
    public String getApodoUsuario() { return apodoUsuario; }
    public void setApodoUsuario(String apodoUsuario) { this.apodoUsuario = apodoUsuario; }

    public Long getUsuario_id() { return usuario_id; }
    public void setUsuario_id(Long usuario_id) { this.usuario_id = usuario_id; }

    public Long getPublicacionId() { return publicacionId; }
    public void setPublicacionId(Long publicacionId) { this.publicacionId = publicacionId; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
