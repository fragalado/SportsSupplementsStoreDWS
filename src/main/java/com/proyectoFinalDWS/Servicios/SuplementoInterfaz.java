package com.proyectoFinalDWS.Servicios;

import java.util.List;

import com.proyectoFinalDWS.DTOs.SuplementoDTO;

/**
 * Interfaz que define los métodos que darán servicio a Suplemento
 * @author Fran Gallego
 * Fecha: 06/02/2024
 */
public interface SuplementoInterfaz {

	/**
	 * Método que obtiene todos los suplementos de la base de datos y los devuelve
	 * en una lista de tipo SuplementoDTO
	 * 
	 * @return Lista con todos los suplementos de la base de datos.
	 */
	public List<SuplementoDTO> obtieneTodosLosSuplementos();

	/**
	 * Método que borra un suplemento de la base de datos por el id_suplemento
	 * 
	 * @param id_suplemento Id del suplemento a eliminar
	 * @return Devuelve true si se ha eliminado o false si no.
	 */
	public boolean borraSuplementoPorId(long id_suplemento);

	/**
	 * Método que obtiene un suplemento de la base de datos por el id del suplemento
	 * 
	 * @return Devuelve el suplemento encontrado o null en caso de no encontrarlo
	 */
	public SuplementoDTO obtieneSuplementoPorId(long id_suplemento);

	/**
	 * Actualiza un suplemento pasado por parámetros en la base de datos
	 * 
	 * @param suplementoDTO Suplemento a actualizar
	 * @return Devuelve true si se ha actualizado correctamente o false si no.
	 */
	public boolean actualizaSuplemento(SuplementoDTO suplementoDTO);

	/**
	 * Método que agrega a la base de datos un nuevo suplemento.
	 * 
	 * @param suplementoDTO Suplemento que se va a añadir
	 * @return Devuelve true si se ha añadido correctamente o false si se ha
	 *         producido un error.
	 */
	public boolean agregaSuplemento(SuplementoDTO suplementoDTO);
}
