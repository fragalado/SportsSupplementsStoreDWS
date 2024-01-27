package com.proyectoFinalDWS.Servicios;

import com.proyectoFinalDWS.DAOs.Usuario;

/**
 * Interfaz Email que define los métodos que darán servicio para enviar emails
 * @author Fran Gallego
 * Fecha: 27/01/2024
 */
public interface EmailInterfaz {
	
	/**
	 * Método que enviará un email para recuperar/modificar contraseña o activar cuenta al email pasado por parámetros con el asunto pasado y con un cuerpo creado.
	 * @param direccion Direccion a la que enviará el botón del html
	 * @param esActivarCuenta Boolean para saber si es correo para recuperar contraseña o para activar cuenta.
	 * @param usuario Objeto Usuario (DAO)
	 */
	public void enviarEmail(String direccion, boolean esActivarCuenta, Usuario usuario);
}
