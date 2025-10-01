package com.EjemploModel.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.EjemploModel.model.Publicacion;
import com.EjemploModel.dto.ReportePublicacionDto;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    // 🔍 Buscar publicaciones por apodo de usuario (igual que ComunidadRepository)
    @Query("""
        SELECT new com.EjemploModel.dto.ReportePublicacionDto(
            u.apodo, u.id, p.id, p.contenido, p.nombre, p.descripcion,
            p.categoria, p.estado, p.fecha
        )
        FROM Publicacion p
        JOIN p.usuario u
        WHERE u.apodo = :apodo
    """)
    List<ReportePublicacionDto> findPublicacionesPorApodo(@Param("apodo") String apodo);

    // 🔍 Buscar publicaciones por categoría
    @Query("""
        SELECT new com.EjemploModel.dto.ReportePublicacionDto(
            u.apodo, u.id, p.id, p.contenido, p.nombre, p.descripcion,
            p.categoria, p.estado, p.fecha
        )
        FROM Publicacion p
        JOIN p.usuario u
        WHERE p.categoria = :categoria
    """)
    List<ReportePublicacionDto> findPublicacionesPorCategoria(@Param("categoria") String categoria);

    // 🔍 Reporte general (todas las publicaciones con su usuario)
    @Query("""
        SELECT new com.EjemploModel.dto.ReportePublicacionDto(
            u.apodo, u.id, p.id, p.contenido, p.nombre, p.descripcion,
            p.categoria, p.estado, p.fecha
        )
        FROM Publicacion p
        JOIN p.usuario u
    """)
    List<ReportePublicacionDto> findReporteGeneral();
}
