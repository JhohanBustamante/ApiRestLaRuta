package com.EjemploModel.service;

import com.EjemploModel.model.Comunidad;
import com.EjemploModel.model.ComunidadUsuario;
import com.EjemploModel.model.Usuario;
import com.EjemploModel.dto.ComunidadDto;
import com.EjemploModel.dto.FiltroComunidadDto;
import com.EjemploModel.dto.ReporteDto;
import com.EjemploModel.repository.ComunidadRepository;
import com.EjemploModel.repository.ComunidadUsuarioRepository;
import com.EjemploModel.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ComunidadService {

    private ComunidadDto convertirADto(Comunidad c) {
        return new ComunidadDto(
                c.getId(),
                c.getNombre(),
                c.getDescripcion(),
                c.getTematica(),
                c.getTipo(),
                c.getCategoria(),
                c.getEstado(),
                c.getFecha(),
                c.getIdCreador());
    }

    private final ComunidadRepository comunidadRepository;
    private final ComunidadUsuarioRepository comunidadUsuarioRepository;
    private final UsuarioRepository usuarioRepository;
    

    public ComunidadService(
            ComunidadRepository comunidadRepository,
            ComunidadUsuarioRepository comunidadUsuarioRepository,
            UsuarioRepository usuarioRepository) {
        this.comunidadRepository = comunidadRepository;
        this.comunidadUsuarioRepository = comunidadUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ReporteDto> obtenerComunidadesFiltradas(FiltroComunidadDto filtro) {
    return comunidadRepository.filtrarComunidades(
        filtro.tipo(),
        filtro.categoria(),
        filtro.estado(),
        filtro.fechaDesde(),
        filtro.fechaHasta()
    );
}


    public List<ComunidadDto> listarTodos() {
    return comunidadRepository.findAll()
        .stream()
        .map(c -> new ComunidadDto(
            c.getId(),
            c.getNombre(),
            c.getDescripcion(),
            c.getTematica(),
            c.getTipo(),
            c.getCategoria(),
            c.getEstado(),
            c.getFecha(),
            c.getIdCreador()
        ))
        .toList();
}

    public Comunidad guardar(Comunidad comunidad) {
        return comunidadRepository.save(comunidad);
    }

    public ComunidadDto buscarPorNombre(String nombre) {
        return comunidadRepository.findByNombre(nombre).orElse(null);
    }

    public Comunidad buscarPorId(Long id) {
        return comunidadRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        comunidadRepository.deleteById(id);
    }

    public List<ReporteDto> obtenerComunidadesPorApodo(String apodo) {
        return comunidadRepository.findComunidadesPorApodo(apodo);
    }

    public List<ComunidadDto> obtenerPorIdCreador(Long idCreador) {
    return comunidadRepository.findByIdCreador(idCreador)
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    public ComunidadUsuario vincularUsuarioAComunidad(Long usuarioId, Long comunidadId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Comunidad comunidad = comunidadRepository.findById(comunidadId)
                .orElseThrow(() -> new RuntimeException("Comunidad no encontrada"));

        ComunidadUsuario cu = new ComunidadUsuario();
        cu.setUsuario(usuario);
        cu.setComunidad(comunidad);

        return comunidadUsuarioRepository.save(cu);
    }
}
