package com.EjemploModel.dto;

import java.time.LocalDate;

import com.EjemploModel.model.Comunidad;

public record FiltroComunidadDto(
    Comunidad.Tipo tipo,
    String categoria,
    String estado,
    LocalDate fechaDesde,
    LocalDate fechaHasta
) {}
