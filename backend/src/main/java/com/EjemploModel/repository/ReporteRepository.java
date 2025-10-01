package com.EjemploModel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.EjemploModel.model.Comunidad;
import com.EjemploModel.model.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
        @Query("SELECT r FROM Reporte r WHERE r.fecha BETWEEN :fechaInicio AND :fechaFin " +
                        "AND r.categoria = :categoria " +
                        "AND r.estado = :estado " +
                        "AND r.cantidad BETWEEN :cantidadMin AND :cantidadMax")
        List<Reporte> findReportesPorFiltros(@Param("fechaInicio") LocalDate fechaInicio,
                        @Param("fechaFin") LocalDate fechaFin,
                        @Param("categoria") String categoria,
                        @Param("estado") String estado,
                        @Param("cantidadMin") Double cantidadMin,
                        @Param("cantidadMax") Double cantidadMax);
}