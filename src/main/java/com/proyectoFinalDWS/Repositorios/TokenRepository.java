package com.proyectoFinalDWS.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyectoFinalDWS.DAOs.Token;

/**
 * Repositorio que dará servicio a la entidad Token
 * Extiende de JpaRepository para realizar operaciones CRUD y otras consultas
 * @author Fran Gallego
 * Fecha: 27/01/2024
 */
public interface TokenRepository extends JpaRepository<Token, Long>{
	
	@Query("SELECT t FROM Token t WHERE t.cod_token = :codToken")
	public Token findByCodToken(@Param("codToken") String codToken);
}
