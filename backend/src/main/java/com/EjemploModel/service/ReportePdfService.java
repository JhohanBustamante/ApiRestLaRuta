package com.EjemploModel.service;

import com.EjemploModel.dto.ReporteDto;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class ReportePdfService {

    public static byte[] generarPdf(List<ReporteDto> comunidades) {
        try (PDDocument document = new PDDocument();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            // Encabezado
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 16);
            content.newLineAtOffset(50, 750);
            content.showText("Reporte de Comunidades");
            content.endText();

            float y = 720;
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (ReporteDto dto : comunidades) {
                if (y < 100) {
                    content.close();
                    page = new PDPage();
                    document.addPage(page);
                    content = new PDPageContentStream(document, page);
                    y = 720;
                }

                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 10);
                content.newLineAtOffset(50, y);
                content.showText("Comunidad: " + dto.getNombreComunidad() + " | Creador: " + dto.getApodoUsuario() +
                                " | Estado: " + dto.getEstado() +
                                " | Fecha: " + (dto.getFecha() != null ? dto.getFecha().format(fmt) : "N/A"));
                content.endText();

                y -= 15;
            }

            content.close();
            document.save(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error generando PDF: " + e.getMessage(), e);
        }
    }
}
