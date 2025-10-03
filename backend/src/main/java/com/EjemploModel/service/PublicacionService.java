package com.EjemploModel.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.EjemploModel.dto.ReportePublicacionDto;
import com.EjemploModel.model.Publicacion;
import com.EjemploModel.repository.PublicacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;

    /**
     * 🧾 Obtiene todas las publicaciones
     */
    public List<Publicacion> listarTodas() {
        return publicacionRepository.findAll();
    }

    /**
     * 🔍 Obtiene publicaciones creadas por el apodo del usuario
     */
    public List<ReportePublicacionDto> obtenerPublicacionesPorApodo(String apodo) {
        List<Object[]> resultados = publicacionRepository.findPublicacionesPorApodo(apodo);
        return mapearAReporteDto(resultados);
    }

    /**
     * 🔍 Obtiene publicaciones por el ID del usuario
     */
    public List<ReportePublicacionDto> obtenerPublicacionesPorUsuarioId(Long usuarioId) {
        List<Object[]> resultados = publicacionRepository.findPublicacionesPorUsuarioId(usuarioId);
        return mapearAReporteDto(resultados);
    }

    /**
     * 🔍 Obtiene publicaciones por categoría
     */
    public List<ReportePublicacionDto> obtenerPublicacionesPorCategoria(String categoria) {
        List<Object[]> resultados = publicacionRepository.findPublicacionesPorCategoria(categoria);
        return mapearAReporteDto(resultados);
    }

    /**
     * 📊 Reporte general de todas las publicaciones
     */
    public List<ReportePublicacionDto> obtenerReporteGeneral() {
        List<Object[]> resultados = publicacionRepository.findReporteGeneral();
        return mapearAReporteDto(resultados);
    }

    /**
     * 🔎 Obtiene una publicación por su ID
     */
    public Optional<Publicacion> obtenerPorId(Long id) {
        return publicacionRepository.findById(id);
    }

    /**
     * 💾 Crea o actualiza una publicación
     */
    public Publicacion guardar(Publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }

    /**
     * 🗑️ Elimina una publicación por su ID
     */
    public void eliminar(Long id) {
        publicacionRepository.deleteById(id);
    }

    /**
     * 🔄 Convierte los resultados (Object[]) en ReportePublicacionDto
     */
    private List<ReportePublicacionDto> mapearAReporteDto(List<Object[]> resultados) {
        return resultados.stream().map(obj -> new ReportePublicacionDto(
                (String) obj[0], // apodoUsuario
                ((Number) obj[1]).longValue(), // usuario_id
                ((Number) obj[2]).longValue(), // publicacionId
                (String) obj[3], // contenido
                (String) obj[4], // nombre
                (String) obj[5], // descripcion
                (String) obj[6], // categoria
                (String) obj[7], // estado
                obj[8] != null ? ((Date) obj[8]).toLocalDate() : null // fecha
        )).collect(Collectors.toList());
    }
}
