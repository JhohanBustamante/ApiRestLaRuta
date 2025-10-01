package com.EjemploModel.repository;

import com.EjemploModel.model.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageFileRepository extends JpaRepository<ImageFile, String> {
    boolean existsByFilename(String filename);
    List<ImageFile> findByUsuario_IdOrderByCreatedAtDesc(Long userId);
}