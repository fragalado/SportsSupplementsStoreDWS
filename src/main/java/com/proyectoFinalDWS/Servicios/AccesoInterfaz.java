package com.proyectoFinalDWS.Servicios;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;

/**
 * Interfaz que define los métodos que darán servicio al acceso en la aplicación.
 * @author Fran Gallego
 * Fecha: 26/01/2024
 */
public interface AccesoInterfaz {
	
	/**
	 * Método que hace el registro de un usuario a la base de datos y envía un correo para confirmar cuenta. Si el email introducido ya existe no hará el registro.
	 * @param usuario Objeto usuario con los datos.
	 * @return Devuelve true si se ha producido el registro ,false si el email ya existe o null si se ha producido un error.
	 */
	public Boolean registrarUsuario(UsuarioDTO usuario);
	
	/**
	 * Método que activa la cuenta de un usuario. Se le pasa por parámetros el token.
	 * @param token El token.
	 * @return Devuelve true si se ha activado la cuenta o false si no.
	 */
	public boolean activaCuenta(String token);
	
	/**
	 * Método que obtiene un usuario de la base de datos, crea un token, lo guarda en la base de datos el token y envía un correo al email del usuario.
	 * @param usuarioDto Objeto usuario que contiene el email
	 * @return Devuelve true si se ha enviado el correo, false si no se ha encontrado el email en la base de datos o null si se ha producido un error.
	 */
	public Boolean restablePassword(UsuarioDTO usuarioDto);
	
	 
}
