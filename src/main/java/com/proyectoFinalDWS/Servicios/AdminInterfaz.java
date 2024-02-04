package com.proyectoFinalDWS.Servicios;

import java.util.List;

import com.proyectoFinalDWS.DTOs.SuplementoDTO;
import com.proyectoFinalDWS.DTOs.UsuarioDTO;

/**
 * Interfaz que define los métodos que darán servicio a la administracion.
 * @author Fran Gallego
 * Fecha: 03/02/2024
 */
public interface AdminInterfaz {

	/**
	 * Método que obtiene todos los usuarios de la base de datos y los devuelve en una lista de tipo UsuarioDTO
	 * @return Lista con todos los usuarios de la base de datos.
	 */
	public List<UsuarioDTO> obtieneTodosLosUsuarios();
	
	/**
	 * Método que obtiene todos los suplementos de la base de datos y los devuelve en una lista de tipo SuplementoDTO
	 * @return Lista con todos los suplementos de la base de datos.
	 */
	public List<SuplementoDTO> obtieneTodosLosSuplementos();
	
	/**
	 * Método que borra un usuario de la base de datos por el id_usuario
	 * @param id_usuario Id del usuario a eliminar
	 * @return Devuelve true si se ha eliminado o false si no.
	 */
	public boolean borraUsuarioPorId(long id_usuario);
	
	/**
	 * Método que borra un suplemento de la base de datos por el id_suplemento
	 * @param id_suplemento Id del suplemento a eliminar
	 * @return Devuelve true si se ha eliminado o false si no.
	 */
	public boolean borraSuplementoPorId(long id_suplemento);
	
	/**
	 * Método que obtiene un usuario de la base de datos por el id del usuario
	 * @return Devuelve el usuario encontrado o null en caso de no encontrarlo
	 */
	public UsuarioDTO obtieneUsuarioPorId(long id_usuario);
	
	/**
	 * Actualiza un usuario pasado por parámetros en la base de datos
	 * @param usuarioDTO Usuario a actualizar
	 * @return Devuelve true si se ha actulizado correctamente o false si no.
	 */
	public boolean actualizaUsuario(UsuarioDTO usuarioDTO);
}
