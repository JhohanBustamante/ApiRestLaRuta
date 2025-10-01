package com.EjemploModel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EjemploModel.model.Reporte;
import com.EjemploModel.model.ReporteFiltroDTO;
import com.EjemploModel.repository.ReporteRepository;

@Service
public class ReporteMultiCriterioService {
    @Autowired
    private ReporteRepository reporteRepository;

    public List<Reporte> generarReporte(ReporteFiltroDTO filtro) {
        return reporteRepository.findReportesPorFiltros(
            filtro.getFechaInicio(), filtro.getFechaFin(),
            filtro.getCategoria(), filtro.getEstado(),
            filtro.getCantidadMin(), filtro.getCantidadMax());
    }
    
    public Reporte crearReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }
}
