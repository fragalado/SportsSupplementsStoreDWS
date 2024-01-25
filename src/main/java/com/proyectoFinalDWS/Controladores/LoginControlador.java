package com.proyectoFinalDWS.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;

/**
 * Clase Controlador para las vistas login y register
 * @author Fran Gallego
 * Fecha: 25/01/2024
 */
@Controller
public class LoginControlador {
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/acceso/login".
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping("/acceso/login")
	public String vistaLogin(Model model) {
		// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		
		// Devolvemos la vista login
		return "login";
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/acceso/register".
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping("/acceso/register")
	public String vistaRegister(Model model) {
		// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		
		// Devolvemos la vista register
		return "register";
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/acceso/modificar-password".
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping("/acceso/modificar-password")
	public String vistaModificarContrasenya(Model model) {
		// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		
		// Devolvemos la vista register
		return "modificarContrasenya";
	}
}
