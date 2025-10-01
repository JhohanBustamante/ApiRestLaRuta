package com.EjemploModel.service;

import com.EjemploModel.model.Comunidad;
import com.EjemploModel.model.Reporte;
import com.EjemploModel.model.Usuario;
import com.EjemploModel.repository.ComunidadRepository;
import com.EjemploModel.repository.ReporteRepository;
import com.EjemploModel.repository.UsuarioRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComunidadRepository comunidadRepository;

    @Autowired
    private ReporteRepository reporteRepository;

    // 🔹 Reporte de usuarios (ya lo tienes)
    public ByteArrayInputStream generarReporteUsuarios() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            document.add(new Paragraph("Reporte de Usuarios"));
            document.add(new Paragraph(" ")); // salto de línea

            // Tabla
            PdfPTable table = new PdfPTable(3);
            table.addCell("ID");
            table.addCell("Apodo");
            table.addCell("Correo");

            List<Usuario> usuarios = usuarioRepository.findAll();
            for (Usuario u : usuarios) {
                table.addCell(String.valueOf(u.getId()));
                table.addCell(u.getApodo());
                table.addCell(u.getCorreo());
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    // 🔹 Métodos de reporte de entidades
    public Reporte crearReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public List<Reporte> listarReportes() {
        return reporteRepository.findAll();
    }

    public ByteArrayInputStream generarReporteComunidadesPorTipo(Comunidad.Tipo tipo) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Cabecera
            document.add(new Paragraph("📑 Reporte de Comunidades"));
            document.add(new Paragraph("Tipo de comunidad: " + tipo.getValue()));
            document.add(new Paragraph("Fecha de generación: " + LocalDate.now()));
            document.add(new Paragraph(" "));

            // Tabla
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Descripción");
            table.addCell("Usuarios");
            table.addCell("Reportes");
            table.addCell("Estado");

            List<Comunidad> comunidades = comunidadRepository.findByTipo(tipo);
            for (Comunidad c : comunidades) {
                table.addCell(String.valueOf(c.getId()));
                table.addCell(c.getNombre() != null ? c.getNombre() : "-");
                table.addCell(c.getDescripcion() != null ? c.getDescripcion() : "-");

                // Conteo de usuarios y reportes
                int usuariosCount = (c.getUsuarios() != null ? c.getUsuarios().size() : 0)
                        + (c.getUsuariosComunidad() != null ? c.getUsuariosComunidad().size() : 0);
                int reportesCount = c.getReportes() != null ? c.getReportes().size() : 0;

                table.addCell(String.valueOf(usuariosCount));
                table.addCell(String.valueOf(reportesCount));
                table.addCell(c.getEstado() != null ? c.getEstado() : "-");
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
