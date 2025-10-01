package com.EjemploModel.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.EjemploModel.model.ComunidadUsuario;


public interface ComunidadUsuarioRepository  extends JpaRepository<ComunidadUsuario, Long> {

}