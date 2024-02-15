package com.proyectoFinalDWS.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectoFinalDWS.DAOs.Orden;

/**
 * Repositorio que dará servicio a Orden
 * 
 * @author Fran Gallego
 * Fecha: 15/02/2024
 */
public interface OrdenRepository extends JpaRepository<Orden, Long> {

}
