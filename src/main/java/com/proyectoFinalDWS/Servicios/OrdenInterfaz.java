package com.proyectoFinalDWS.Servicios;

/**
 * Interfaz que define los métodos que darán servicio a Orden
 * @author Fran Gallego
 * Fecha: 15/02/2024
 */
public interface OrdenInterfaz {

	/**
	 * Realiza la compra de los carritos del usuario
	 * 
	 * @param email_usuario Email del usuario
	 * @return Devuelve true si se ha completado la compra o false si no
	 */
	public boolean comprarCarritoUsuario(String email_usuario);
}
