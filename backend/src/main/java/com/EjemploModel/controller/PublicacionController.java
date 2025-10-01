package com.EjemploModel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.EjemploModel.dto.ReportePublicacionDto;
import com.EjemploModel.model.Publicacion;
import com.EjemploModel.service.PublicacionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/publicaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PublicacionController {

    private final PublicacionService publicacionService;

    /**
     * Listar todas las publicaciones
     */
    @GetMapping
    public ResponseEntity<List<Publicacion>> listarTodas() {
        return ResponseEntity.ok(publicacionService.listarTodas());
    }

    /**
     * Obtener publicaciones por apodo de usuario
     */
    @GetMapping("/usuario/{apodo}")
    public ResponseEntity<List<ReportePublicacionDto>> obtenerPorApodo(@PathVariable String apodo) {
        List<ReportePublicacionDto> publicaciones = publicacionService.obtenerPublicacionesPorApodo(apodo);
        return ResponseEntity.ok(publicaciones);
    }

    /**
     * Obtener una publicación por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Publicacion> obtenerPorId(@PathVariable Long id) {
        return publicacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear una publicación
     */
    @PostMapping
    public ResponseEntity<Publicacion> crear(@RequestBody Publicacion publicacion) {
        Publicacion guardada = publicacionService.guardar(publicacion);
        return ResponseEntity.ok(guardada);
    }

    /**
     * Actualizar una publicación
     */
    @PutMapping("/{id}")
    public ResponseEntity<Publicacion> actualizar(@PathVariable Long id, @RequestBody Publicacion publicacion) {
        return publicacionService.obtenerPorId(id)
                .map(p -> {
                    publicacion.setId(id);
                    Publicacion actualizada = publicacionService.guardar(publicacion);
                    return ResponseEntity.ok(actualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar una publicación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        publicacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
