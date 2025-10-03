package com.EjemploModel.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.EjemploModel.repository.UsuarioRepository;
import com.EjemploModel.repository.RoleRepository;
import com.EjemploModel.security.JwtUtils;

import com.EjemploModel.model.Role;
import com.EjemploModel.model.Usuario;

import com.EjemploModel.payload.LoginRequest;
import com.EjemploModel.payload.SignupRequest;
import com.EjemploModel.payload.JwtResponse;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        if (!usuarioRepository.existsByApodo(loginRequest.getApodo())) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Apodo no existe"));
        }

        Usuario usuario = usuarioRepository.findByApodo(loginRequest.getApodo()).orElse(null);
        if (usuario == null || !encoder.matches(loginRequest.getContrasena(), usuario.getContrasena())) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Contraseña incorrecta"));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getApodo(), loginRequest.getContrasena()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (usuarioRepository.existsByApodo(signUpRequest.getApodo())) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Apodo en uso"));
        }

        if (usuarioRepository.existsByCorreo(signUpRequest.getCorreo())) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Correo en uso"));
        }

        Usuario user = new Usuario();
        user.setApodo(signUpRequest.getApodo());
        user.setNombre(signUpRequest.getNombre());
        user.setApellido(signUpRequest.getApellido());
        user.setCorreo(signUpRequest.getCorreo());
        user.setContrasena(encoder.encode(signUpRequest.getContrasena()));

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role no encontrado"));
        user.getRoles().add(userRole);
        usuarioRepository.save(user);

        return ResponseEntity.ok(Map.of("success", true, "message", "Usuario registado"));
    }
}
