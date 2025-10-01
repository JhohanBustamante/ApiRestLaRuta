package com.EjemploModel.service;

import java.util.List;
import java.util.Optional;

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
     * Obtiene todas las publicaciones
     */
    public List<Publicacion> listarTodas() {
        return publicacionRepository.findAll();
    }

    /**
     * Obtiene publicaciones creadas por el apodo del usuario
     */
    public List<ReportePublicacionDto> obtenerPublicacionesPorApodo(String apodo) {
        return publicacionRepository.findPublicacionesPorApodo(apodo);
    }

    /**
     * Obtiene una publicación por ID
     */
    public Optional<Publicacion> obtenerPorId(Long id) {
        return publicacionRepository.findById(id);
    }

    /**
     * Crea o actualiza una publicación
     */
    public Publicacion guardar(Publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }

    /**
     * Elimina una publicación por ID
     */
    public void eliminar(Long id) {
        publicacionRepository.deleteById(id);
    }
}
