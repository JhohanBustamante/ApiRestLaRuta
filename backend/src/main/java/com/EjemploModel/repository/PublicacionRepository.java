package com.EjemploModel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.EjemploModel.model.Publicacion;
import com.EjemploModel.dto.ReportePublicacionDto;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    // 🔍 Buscar publicaciones por apodo de usuario
    @Query(value = """
                SELECT
                    u.apodo AS apodoUsuario,
                    u.id AS usuario_id,
                    p.id AS publicacionId,
                    p.contenido AS contenido,
                    p.nombre AS nombre,
                    p.descripcion AS descripcion,
                    p.categoria AS categoria,
                    p.estado AS estado,
                    p.fecha AS fecha
                FROM usuario u
                INNER JOIN publicacion p ON p.usuario_id = u.id
                WHERE u.apodo = :apodo
            """, nativeQuery = true)
    List<Object[]> findPublicacionesPorApodo(@Param("apodo") String apodo);

    // 🔍 Buscar publicaciones por categoría
    @Query(value = """
                SELECT
                    u.apodo AS apodoUsuario,
                    u.id AS usuario_id,
                    p.id AS publicacionId,
                    p.contenido AS contenido,
                    p.nombre AS nombre,
                    p.descripcion AS descripcion,
                    p.categoria AS categoria,
                    p.estado AS estado,
                    p.fecha AS fecha
                FROM usuario u
                INNER JOIN publicacion p ON p.usuario_id = u.id
                WHERE p.categoria = :categoria
            """, nativeQuery = true)
    List<Object[]> findPublicacionesPorCategoria(@Param("categoria") String categoria);

    // 🔍 Reporte general de todas las publicaciones con su usuario
    @Query(value = """
                SELECT
                    u.apodo AS apodoUsuario,
                    u.id AS usuario_id,
                    p.id AS publicacionId,
                    p.contenido AS contenido,
                    p.nombre AS nombre,
                    p.descripcion AS descripcion,
                    p.categoria AS categoria,
                    p.estado AS estado,
                    p.fecha AS fecha
                FROM usuario u
                INNER JOIN publicacion p ON p.usuario_id = u.id
            """, nativeQuery = true)
    List<Object[]> findReporteGeneral();

    @Query(value = """
                SELECT
                    u.apodo AS apodoUsuario,
                    u.id AS usuario_id,
                    p.id AS publicacionId,
                    p.contenido AS contenido,
                    p.nombre AS nombre,
                    p.descripcion AS descripcion,
                    p.categoria AS categoria,
                    p.estado AS estado,
                    p.fecha AS fecha
                FROM usuario u
                INNER JOIN publicacion p ON p.usuario_id = u.id
                WHERE u.id = :usuarioId
            """, nativeQuery = true)
    List<Object[]> findPublicacionesPorUsuarioId(@Param("usuarioId") Long usuarioId);
}
