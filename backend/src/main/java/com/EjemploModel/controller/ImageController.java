package com.EjemploModel.controller;

// ImageController.java
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.EjemploModel.service.FileStorageService;
import com.EjemploModel.model.ImageFile;
import com.EjemploModel.repository.ImageFileRepository;

import lombok.RequiredArgsConstructor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final FileStorageService storage;
    private final ImageFileRepository repo;

    // Subir imagen asociada a un usuario
    @PostMapping(value = "/api/images", consumes = "multipart/form-data")
    public ResponseEntity<?> upload(
            @RequestPart("file") MultipartFile file,
            @RequestParam("userId") Long userId // <-- si ya usas JWT, sácalo del token
    ) {
        try {
            var saved = storage.store(file, userId);
            return ResponseEntity.ok(Map.of(
                    "id", saved.id(),
                    "filename", saved.filename(),
                    "url", saved.url(),
                    "userId", saved.userId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Listar imágenes de un usuario (más nuevas primero)
    @GetMapping("/api/users/{userId}/images")
    public ResponseEntity<?> listByUser(@PathVariable Long userId) {
        var list = repo.findByUsuario_IdOrderByCreatedAtDesc(userId)
                .stream()
                .map(img -> Map.of(
                        "id", img.getId(),
                        "filename", img.getFilename(),
                        "url", "/images/" + img.getFilename(),
                        "contentType", img.getContentType(),
                        "size", img.getSize(),
                        "createdAt", img.getCreatedAt()))
                .toList();
        return ResponseEntity.ok(list);
    }
}
