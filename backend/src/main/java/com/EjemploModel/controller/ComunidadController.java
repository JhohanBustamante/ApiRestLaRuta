package com.EjemploModel.controller;

import com.EjemploModel.dto.ComunidadDto;
import com.EjemploModel.dto.FiltroComunidadDto;
import com.EjemploModel.dto.ReporteDto;
import com.EjemploModel.model.Comunidad;
import com.EjemploModel.model.ComunidadUsuario;
import com.EjemploModel.model.Usuario;
import com.EjemploModel.repository.ComunidadUsuarioRepository;
import com.EjemploModel.repository.UsuarioRepository;
import com.EjemploModel.service.ComunidadService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.EjemploModel.service.ReportePdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@Controller
public class ComunidadController {

    private final ComunidadService comunidadService;
    private final UsuarioRepository usuarioRepository;
    private final ComunidadUsuarioRepository comunidadUsuarioRepository;
    private final ReportePdfService reportePdfService;

    public ComunidadController(
            ComunidadService comunidadService,
        UsuarioRepository usuarioRepository,
        ComunidadUsuarioRepository comunidadUsuarioRepository,
        ReportePdfService reportePdfService) {
    this.comunidadService = comunidadService;
    this.usuarioRepository = usuarioRepository;
    this.comunidadUsuarioRepository = comunidadUsuarioRepository;
    this.reportePdfService = reportePdfService;
    }

    @GetMapping("/api/comunidades")
    public List<ComunidadDto> listarComunidades() {
    return comunidadService.listarTodos();
    }

    @GetMapping("/comunidad/{id}")
    public Comunidad obtenerPorId(@PathVariable Long id) {
        return comunidadService.buscarPorId(id);
    }

    @GetMapping("/comunidad/buscar/{nombre}")
    public ResponseEntity<ComunidadDto> obtenerPorNombre(@PathVariable String nombre) {
        ComunidadDto comunidad = comunidadService.buscarPorNombre(nombre);

        if (comunidad == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(comunidad);
    }

    @PostMapping("/comunidad/crear")
    public ResponseEntity<?> crear(@RequestBody Comunidad comunidad) {

        if (comunidadService.buscarPorNombre(comunidad.getNombre()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "estado", false,
                            "mensaje", "Ya existe una comunidad con ese nombre"));
        }
        Usuario creador = usuarioRepository.findById(comunidad.getIdCreador())
                .orElseThrow(() -> new RuntimeException("Usuario creador no encontrado"));

        Comunidad guardada = comunidadService.guardar(comunidad);

        ComunidadUsuario cu = new ComunidadUsuario();
        cu.setComunidad(guardada);
        cu.setUsuario(creador);
        comunidadUsuarioRepository.save(cu);

        return ResponseEntity.ok(Map.of(
                "estado", true,
                "mensaje", "Comunidad creada correctamente",
                "comunidad", guardada));
    }

    @PutMapping("/comunidad/actualizar/{id}")
    public Comunidad actualizar(@PathVariable Long id, @RequestBody Comunidad comunidad) {
        comunidad.setId(id);
        return comunidadService.guardar(comunidad);
    }

    @DeleteMapping("/comunidad/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        comunidadService.eliminar(id);
    }

    @GetMapping("/comunidad/apodo/{apodo}")
    public ResponseEntity<List<ReporteDto>> getComunidadesPorApodo(@PathVariable String apodo) {
        List<ReporteDto> comunidades = comunidadService.obtenerComunidadesPorApodo(apodo);
        return ResponseEntity.ok(comunidades);
    }

    @PostMapping("/comunidad/filtrar")
public ResponseEntity<List<ReporteDto>> filtrarComunidades(@RequestBody FiltroComunidadDto filtro) {
    List<ReporteDto> comunidades = comunidadService.obtenerComunidadesFiltradas(filtro);
    return ResponseEntity.ok(comunidades);
}

@PostMapping("/comunidad/filtrar/pdf")
public ResponseEntity<byte[]> generarReportePdf(@RequestBody FiltroComunidadDto filtro) {
    List<ReporteDto> reporte = comunidadService.obtenerComunidadesFiltradas(filtro);
    byte[] pdf = reportePdfService.generarPdf(reporte);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("attachment", "reporte_comunidades.pdf");

    return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
}

@GetMapping("/comunidad/creador/{idCreador}")
public ResponseEntity<List<ComunidadDto>> obtenerComunidadesPorCreador(@PathVariable Long idCreador) {
    List<ComunidadDto> comunidades = comunidadService.obtenerPorIdCreador(idCreador);
    return ResponseEntity.ok(comunidades);
}

}