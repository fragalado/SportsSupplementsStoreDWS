package com.proyectoFinalDWS.Servicios;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;

/**
 * Interfaz que define los métodos que darán servicio a Usuario
 * @author Fran Gallego
 * Fecha: 06/02/2024
 */
public interface UsuarioInterfaz {

	/**
	 * Método que obtiene un usuario de la base de datos por el email
	 * @param email Email a buscar
	 * @return Devuelve el usuario encontrado o null en caso de no encontrarlo
	 */
	public UsuarioDTO obtieneUsuarioPorEmail(String email);
}
