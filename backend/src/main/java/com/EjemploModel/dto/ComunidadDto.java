package com.EjemploModel.dto;

import java.time.LocalDate;

import com.EjemploModel.model.Comunidad;

public record ComunidadDto(
    Long id,
    String nombre,
    String descripcion,
    String tematica,
    Comunidad.Tipo tipo,
    String categoria,
    String estado,
    LocalDate fecha,
    Long idCreador
) {}
