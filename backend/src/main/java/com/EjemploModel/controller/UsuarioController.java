package com.EjemploModel.controller;

import com.EjemploModel.dto.UsuarioDto;
import com.EjemploModel.model.Usuario;
import com.EjemploModel.payload.SignupRequest;
import com.EjemploModel.service.UsuarioService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Controller
public class UsuarioController {
  private final UsuarioService usuarioService;

  @Autowired
  PasswordEncoder encoder;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }
  @GetMapping("/usuarios/info")
  public List<UsuarioDto> listarUsuarios() {
    return usuarioService.listarTodos();
  }

  @GetMapping("/usuarios/info/{id}")
  public Usuario buscarPorId(@PathVariable Long id) {
    return usuarioService.buscarPorId(id);
  }

  @GetMapping("/usuarios/apodo/{apodo}")
  public Usuario buscarPorApodo(@PathVariable String apodo) {
    return usuarioService.buscarPorApodo(apodo);
  }

  @PutMapping("/actualizar/{id}")
public ResponseEntity<?> actualizarUsuario(
    @PathVariable Long id,
    @RequestBody Usuario datosNuevos) {

  Usuario existenteCorreo = usuarioService.buscarPorCorreo(datosNuevos.getCorreo());
  if (existenteCorreo != null && !existenteCorreo.getId().equals(id)) {
    return ResponseEntity
        .badRequest()
        .body(Map.of("estado", false, "mensaje", "Ya existe un usuario con ese correo"));
  }

  Usuario existenteApodo = usuarioService.buscarPorApodo(datosNuevos.getApodo());
  if (existenteApodo != null && !existenteApodo.getId().equals(id)) {
    return ResponseEntity
        .badRequest()
        .body(Map.of("estado", false, "mensaje", "Ya existe un usuario con ese apodo"));
  }

  if (datosNuevos.getContrasena() != null && !datosNuevos.getContrasena().isBlank()) {
    datosNuevos.setContrasena(encoder.encode(datosNuevos.getContrasena()));
  }

  Usuario actualizado = usuarioService.actualizarUsuario(id, datosNuevos);

  if (actualizado == null) {
    return ResponseEntity
        .badRequest()
        .body(Map.of("estado", false, "mensaje", "Usuario no encontrado"));
  } else {
    return ResponseEntity.ok(
        Map.of("estado", true, "mensaje", "Usuario actualizado correctamente", "usuario", actualizado));
  }
}

}