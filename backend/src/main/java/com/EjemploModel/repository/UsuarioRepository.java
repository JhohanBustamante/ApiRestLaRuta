package com.EjemploModel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.EjemploModel.model.Usuario;

import jakarta.transaction.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByApodo(String apodo);

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByApodoAndContrasena(String apodo, String contrasena);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.correo = :correo WHERE u.id = :id")
    int actualizarCorreo(Long id, String correo);

    Boolean existsByApodo(String apodo);
    Boolean existsByCorreo(String correo);
}