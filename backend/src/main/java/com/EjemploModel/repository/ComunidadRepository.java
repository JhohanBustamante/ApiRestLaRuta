package com.EjemploModel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.EjemploModel.model.Comunidad;
import com.EjemploModel.model.Reporte;
import com.EjemploModel.dto.ReporteDto;

public interface ComunidadRepository extends JpaRepository<Comunidad, Long> {
    Optional<Comunidad> findByNombre(String nombre);

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

}
