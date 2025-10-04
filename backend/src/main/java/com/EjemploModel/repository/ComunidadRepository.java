package com.EjemploModel.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.EjemploModel.model.Comunidad;
import com.EjemploModel.model.Reporte;
import com.EjemploModel.dto.ComunidadDto;
import com.EjemploModel.dto.ReporteDto;

public interface ComunidadRepository extends JpaRepository<Comunidad, Long> {
    
    Optional<ComunidadDto> findByNombre(String nombre);

    @Query("""
                SELECT new com.EjemploModel.dto.ReporteDto(
                    u.apodo, u.id, c.id, c.descripcion, c.nombre,
                    c.tematica, c.tipo, c.categoria, c.estado, c.fecha
                )
                FROM ComunidadUsuario cu
                JOIN cu.comunidad c
                JOIN cu.usuario u
                WHERE u.apodo = :apodo
            """)
    List<ReporteDto> findComunidadesPorApodo(@Param("apodo") String apodo);

    List<Comunidad> findByTipo(Comunidad.Tipo tipo);


    @Query("""
    SELECT new com.EjemploModel.dto.ReporteDto(
        'N/A', 
        null, 
        c.id, 
        c.descripcion, 
        c.nombre, 
        c.tematica, 
        c.tipo, 
        c.categoria, 
        c.estado, 
        c.fecha
    )
    FROM Comunidad c
    WHERE 
        (:tipo IS NULL OR c.tipo = :tipo)
        AND (:categoria IS NULL OR LOWER(c.categoria) LIKE LOWER(CONCAT('%', :categoria, '%')))
        AND (:estado IS NULL OR LOWER(c.estado) = LOWER(:estado))
        AND (:fechaDesde IS NULL OR c.fecha >= :fechaDesde)
        AND (:fechaHasta IS NULL OR c.fecha <= :fechaHasta)
""")
List<ReporteDto> filtrarComunidades(
    @Param("tipo") Comunidad.Tipo tipo,
    @Param("categoria") String categoria,
    @Param("estado") String estado,
    @Param("fechaDesde") LocalDate fechaDesde,
    @Param("fechaHasta") LocalDate fechaHasta
);

    List<Comunidad> findByIdCreador(Long idCreador);

}
