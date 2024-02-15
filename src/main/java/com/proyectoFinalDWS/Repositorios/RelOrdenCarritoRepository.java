package com.proyectoFinalDWS.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectoFinalDWS.DAOs.RelOrdenCarrito;

/**
 * Repositorio que dará servicio a RelOrdenCarrito
 * 
 * @author Fran Gallego
 * Fecha: 15/02/2024
 */
public interface RelOrdenCarritoRepository extends JpaRepository<RelOrdenCarrito, Long> {

}
