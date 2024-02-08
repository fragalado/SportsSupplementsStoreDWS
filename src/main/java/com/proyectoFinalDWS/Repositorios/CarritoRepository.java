package com.proyectoFinalDWS.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyectoFinalDWS.DAOs.Carrito;
import com.proyectoFinalDWS.DAOs.Suplemento;
import com.proyectoFinalDWS.DAOs.Usuario;

/**
 * Repositorio que dará servicio a Carrito
 * Extiende de JpaRepository para realizar operaciones CRUD y otras consultas
 * @author Fran Gallego
 * Fecha: 08/02/2024
 */
public interface CarritoRepository extends JpaRepository<Carrito, Long>{

	/**
	 * Método que devuelve una lista de tipo Carrito con todos los carritos que no estén comprados
	 * @return Devuelve una lista de tipo Carrito List<Carrito>
	 */
	@Query("SELECT c FROM Carrito c WHERE c.usuario=:usuario AND c.estaComprado_carrito = false")
	public List<Carrito> findAllCarritoNoComprado(@Param("usuario") Usuario usuario);
	
	/**
	 * Método que devuelve un carrito (DAO) por su usuario y el suplemento y si no esta comprado
	 * @param usuario Usuario del carrito
	 * @param suplemento Suplemento del carrito
	 * @return Devuelve el carrito obtenido
	 */
	@Query("SELECT c FROM Carrito c WHERE c.usuario=:usuario AND c.suplemento=:suplemento AND c.estaComprado_carrito = false")
	public Carrito findCarritoBySuplemento(@Param("usuario") Usuario usuario, @Param("suplemento") Suplemento suplemento);
}
