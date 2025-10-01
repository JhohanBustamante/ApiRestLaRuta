package com.EjemploModel.dto;

import java.time.LocalDate;
import com.EjemploModel.model.Comunidad;

public class ReporteDto {
        private String apodoUsuario;
        private Long usuarioId;
        private Long comunidadId;
        private String descripcionComunidad;
        private String nombreComunidad;
        private String tematica;
        private String tipo;
        private String categoria;
        private String estado;
        private LocalDate fecha;

        public ReporteDto(String apodoUsuario, Long usuarioId, Long comunidadId, String descripcionComunidad,
                        String nombreComunidad, String tematica, Comunidad.Tipo tipo, String categoria,
                        String estado, LocalDate fecha) {
                this.apodoUsuario = apodoUsuario;
                this.usuarioId = usuarioId;
                this.comunidadId = comunidadId;
                this.descripcionComunidad = descripcionComunidad;
                this.nombreComunidad = nombreComunidad;
                this.tematica = tematica;
                this.tipo = (tipo != null) ? tipo.name() : null;
                this.categoria = categoria;
                this.estado = estado;
                this.fecha = fecha;
        }

        public String getApodoUsuario() {
                return apodoUsuario;
        }

        public void setApodoUsuario(String apodoUsuario) {
                this.apodoUsuario = apodoUsuario;
        }

        public Long getUsuarioId() {
                return usuarioId;
        }

        public void setUsuarioId(Long usuarioId) {
                this.usuarioId = usuarioId;
        }

        public Long getComunidadId() {
                return comunidadId;
        }

        public void setComunidadId(Long comunidadId) {
                this.comunidadId = comunidadId;
        }

        public String getDescripcionComunidad() {
                return descripcionComunidad;
        }

        public void setDescripcionComunidad(String descripcionComunidad) {
                this.descripcionComunidad = descripcionComunidad;
        }

        public String getNombreComunidad() {
                return nombreComunidad;
        }

        public void setNombreComunidad(String nombreComunidad) {
                this.nombreComunidad = nombreComunidad;
        }

        public String getTematica() {
                return tematica;
        }

        public void setTematica(String tematica) {
                this.tematica = tematica;
        }

        public String getTipo() {
                return tipo;
        }

        public void setTipo(String tipo) {
                this.tipo = tipo;
        }

        public String getCategoria() {
                return categoria;
        }

        public void setCategoria(String categoria) {
                this.categoria = categoria;
        }

        public String getEstado() {
                return estado;
        }

        public void setEstado(String estado) {
                this.estado = estado;
        }

        public LocalDate getFecha() {
                return fecha;
        }

        public void setFecha(LocalDate fecha) {
                this.fecha = fecha;
        }
}
