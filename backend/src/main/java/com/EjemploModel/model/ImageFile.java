package com.EjemploModel.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;

import com.EjemploModel.model.Comunidad.Tipo;

@Entity
@Table(name = "image_file", indexes = {
                @Index(name = "idx_imagefile_user", columnList = "user_id"),
                @Index(name = "idx_imagefile_created", columnList = "created_at")
}, uniqueConstraints = {
                @UniqueConstraint(name = "uq_imagefile_filename", columnNames = "filename")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageFile {

        @Id
        @Column(length = 36)
        private String id; // UUID (mismo que devolvemos al front)

        @Column(nullable = false, unique = true)
        private String filename; // id + extensión

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private Usuario usuario; // FK lógica

        @Column(nullable = false)
        private String contentType;

        @Column(nullable = false)
        private long size;

        @Column(name = "created_at", nullable = false, updatable = false)
        private Instant createdAt;
        @Column(length = 50)
        
        private String categoria;

        @Column(length = 20)
        private String estado;

        @Column
        private LocalDate fecha;

        @Enumerated(EnumType.STRING)
        @Column(length = 20)
        private Tipo tipo;

}
