package com.EjemploModel.service;

import com.EjemploModel.model.ImageFile;
import com.EjemploModel.model.Usuario;
import com.EjemploModel.repository.ImageFileRepository;
import com.EjemploModel.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path root;
    private final Set<String> allowed = Set.of("image/jpeg", "image/png", "image/webp", "image/gif");
    private final ImageFileRepository imageRepo;
    private final UsuarioRepository userRepo;

    @Value("${app.public.base-url:http://localhost:8080}")
    private String publicBaseUrl;

    public FileStorageService(
            @Value("${app.upload.dir:uploads}") String uploadDir,
            ImageFileRepository imageRepo,
            UsuarioRepository userRepo
    ) throws IOException {
        this.root = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.root);
        this.imageRepo = imageRepo;
        this.userRepo = userRepo;
    }

    public StoredImage store(MultipartFile file, Long userId) throws IOException {
        if (file.isEmpty()) throw new IOException("Archivo vacío");
        if (!allowed.contains(file.getContentType()))
            throw new IOException("Tipo no permitido: " + file.getContentType());

        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "" : file.getOriginalFilename());
        String ext = "";
        int dot = original.lastIndexOf('.');
        if (dot >= 0 && dot < original.length() - 1) ext = original.substring(dot).toLowerCase();

        String id = UUID.randomUUID().toString();
        String filename = id + ext;
        if (filename.contains("..")) throw new IOException("Nombre inválido");

        Usuario user = userRepo.findById(userId)
                .orElseThrow(() -> new IOException("Usuario no encontrado: " + userId));

        Path target = root.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        try {
            ImageFile entity = ImageFile.builder()
                    .id(id)
                    .filename(filename)
                    .usuario(user)
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .createdAt(Instant.now())
                    .build();
            imageRepo.save(entity);
        } catch (RuntimeException ex) {
            try { Files.deleteIfExists(target); } catch (IOException ignore) {}
            throw ex;
        }

        String url = publicBaseUrl + "/images/" + filename;
        return new StoredImage(id, filename, url, file.getContentType(), file.getSize(), userId);
    }

    public record StoredImage(
            String id, String filename, String url, String contentType, long size, Long userId
    ) {}
}
