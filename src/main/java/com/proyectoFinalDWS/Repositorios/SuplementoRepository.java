package com.proyectoFinalDWS.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectoFinalDWS.DAOs.Suplemento;

/**
 * Repositorio que dará servicio a Suplemento.
 * Extiende de JpaRepository para realizar operaciones CRUD y otras consultas
 * @author Fran Gallego
 * Fecha: 03/02/2024
 */
public interface SuplementoRepository extends JpaRepository<Suplemento, Long>{

}
