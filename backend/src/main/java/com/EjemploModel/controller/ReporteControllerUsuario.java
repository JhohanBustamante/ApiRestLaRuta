package com.EjemploModel.controller;

import com.EjemploModel.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReporteControllerUsuario {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/api/reportes/usuarios")
    public ResponseEntity<InputStreamResource> generarReporteUsuarios() {
        var bis = reporteService.generarReporteUsuarios();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_usuarios.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
    
}
