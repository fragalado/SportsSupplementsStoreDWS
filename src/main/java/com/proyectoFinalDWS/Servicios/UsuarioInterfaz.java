package com.proyectoFinalDWS.Servicios;

import java.util.List;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;

/**
 * Interfaz que define los métodos que darán servicio a Usuario
 * @author Fran Gallego
 * Fecha: 06/02/2024
 */
public interface UsuarioInterfaz {

	/**
	 * Método que obtiene un usuario de la base de datos por el email
	 * 
	 * @param email Email a buscar
	 * @return Devuelve el usuario encontrado o null en caso de no encontrarlo
	 */
	public UsuarioDTO obtieneUsuarioPorEmail(String email);
	
	/**
	 * Método que obtiene un usuario de la base de datos por el id del usuario
	 * 
	 * @return Devuelve el usuario encontrado o null en caso de no encontrarlo
	 */
	public UsuarioDTO obtieneUsuarioPorId(long id_usuario);

	/**
	 * Método que obtiene todos los usuarios de la base de datos y los devuelve en
	 * una lista de tipo UsuarioDTO
	 * 
	 * @return Lista con todos los usuarios de la base de datos.
	 */
	public List<UsuarioDTO> obtieneTodosLosUsuarios();
	
	/**
	 * Método que borra un usuario de la base de datos por el id_usuario
	 * 
	 * @param id_usuario Id del usuario a eliminar
	 * @return Devuelve true si se ha eliminado o false si no.
	 */
	public boolean borraUsuarioPorId(long id_usuario);
	
	/**
	 * Actualiza un usuario pasado por parámetros en la base de datos
	 * @param usuarioDTO Usuario a actualizar
	 * @return Devuelve true si se ha actualizado correctamente o false si no.
	 */
	public boolean actualizaUsuario(UsuarioDTO usuarioDTO);
	
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
