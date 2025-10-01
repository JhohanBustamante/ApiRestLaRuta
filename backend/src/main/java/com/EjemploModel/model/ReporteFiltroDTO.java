package com.EjemploModel.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReporteFiltroDTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String categoria;
    private String estado;
    private Double cantidadMin;
    private Double cantidadMax;
}
