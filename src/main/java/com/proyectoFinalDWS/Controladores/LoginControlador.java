package com.proyectoFinalDWS.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Utiles.Util;

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
	 * Método que maneja las solicitudes GET para la ruta "/login"
	 * 
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@GetMapping()
	public String vistaLogin(Model model, HttpServletRequest request) {

		try {
			// Log
			Util.logInfo("LoginControlador", "vistaLogin", "Ha entrado en vistaLogin");
			
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
				// Log
				Util.logInfo("LoginControlador", "vistaLogin", "El usuario ya ha iniciado sesión. Se redirige a home");
				return "redirect:/home";
			}
			
			// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
			model.addAttribute("usuarioDTO", new UsuarioDTO());

			// Devolvemos la vista login
			return "login";
		} catch (Exception e) {
			// Log
			Util.logError("LoginControlador", "vistaLogin", "Se ha producido un error.");
			return "login";
		}
	}
}
