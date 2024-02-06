package com.proyectoFinalDWS.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase Controlador para la vista login
 * @author Fran Gallego
 * Fecha: 25/01/2024
 */
@Controller
@RequestMapping("/login")
public class LoginControlador {

	/**
	 * Método que maneja las solicitudes GET para la ruta "/login".
	 * 
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping()
	public String vistaLogin(Model model, HttpServletRequest request) {
		// Control de sesión
		if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USU")) {
			return "redirect:/home";
		}

		// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
		model.addAttribute("usuarioDTO", new UsuarioDTO());

		// Devolvemos la vista login
		return "login";
	}
}
