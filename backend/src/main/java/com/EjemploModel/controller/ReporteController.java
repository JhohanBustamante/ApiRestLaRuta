package com.EjemploModel.controller;

import java.util.List;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.EjemploModel.model.Comunidad;
import com.EjemploModel.model.Reporte;
import com.EjemploModel.model.ReporteFiltroDTO;
import com.EjemploModel.service.ReporteMultiCriterioService;
import com.EjemploModel.service.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteMultiCriterioService reporteMultiCriterioService;

    @Autowired
    private ReporteService reporteService;

    @PostMapping
    public ResponseEntity<Reporte> crearReporte(@RequestBody Reporte reporte) {
        Reporte nuevo = reporteService.crearReporte(reporte);
        return ResponseEntity.ok(nuevo);
    }

    // 🔹 Listar todos los reportes
    @GetMapping("/todos")
    public ResponseEntity<List<Reporte>> listarReportes() {
        return ResponseEntity.ok(reporteService.listarReportes());
    }

    @GetMapping
    public ResponseEntity<List<Reporte>> obtenerReporte(
            @RequestParam(required = false) LocalDate fechaInicio,
            @RequestParam(required = false) LocalDate fechaFin,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Double cantidadMin,
            @RequestParam(required = false) Double cantidadMax) {

        ReporteFiltroDTO filtro = new ReporteFiltroDTO();
        filtro.setFechaInicio(fechaInicio);
        filtro.setFechaFin(fechaFin);
        filtro.setCategoria(categoria);
        filtro.setEstado(estado);
        filtro.setCantidadMin(cantidadMin);
        filtro.setCantidadMax(cantidadMax);

        List<Reporte> reportes = reporteMultiCriterioService.generarReporte(filtro);
        return ResponseEntity.ok(reportes);
    }

    @GetMapping(value = "/pdf/comunidades", produces = org.springframework.http.MediaType.APPLICATION_PDF_VALUE)
public ResponseEntity<byte[]> generarReporteComunidades(
        @RequestParam Comunidad.Tipo tipo) {

    ByteArrayInputStream bis = reporteService.generarReporteComunidadesPorTipo(tipo);

    return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=comunidades.pdf")
            .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
            .body(bis.readAllBytes());
}
}
