package com.proyectoFinalDWS.Servicios;

import com.proyectoFinalDWS.DAOs.Token;

/**
 * Interfaz que define los métodos que darán servicio a Token
 * @author Fran Gallego
 * Fecha: 06/02/2024
 */
public interface TokenInterfaz {

	/**
	 * Método que guarda un token en la base de datos
	 * @return Devuelve true si se ha guardado o false si no
	 */
	public boolean guardaToken(Token token);
	
	/**
	 * Método que obtiene un token de la base de datos y lo devuelve.
	 * @return Devuelve el token encontrado
	 */
	public Token obtieneToken(String token);
}
