package com.proyectoFinalDWS.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Servicios.UsuarioImplementacion;
import com.proyectoFinalDWS.Utiles.Util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase controlador para la vista registro de sesión
 * @author Fran Gallego
 * Fecha: 06/02/2024
 */
@Controller
@RequestMapping("/register")
public class RegistroControlador {

	@Autowired
	private UsuarioImplementacion usuarioImplementacion;
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/register".
	 * 
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@GetMapping()
	public String vistaRegister(Model model, HttpServletRequest request) {

		try {
			// Log
			Util.logInfo("RegistroControlador", "vistaRegister", "Ha entrado en vistaRegister");
			
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
				// Log
				Util.logInfo("RegistroControlador", "vistaRegister", "El usuario ya ha iniciado sesión. Se redirige a home.");
				return "redirect:/home";
			}
			
			// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
			model.addAttribute("usuarioDTO", new UsuarioDTO());

			// Devolvemos la vista register
			return "register";
		} catch (Exception e) {
			// Log
			Util.logError("RegistroControlador", "vistaRegister", "Se ha producido un error.");
			return "register";
		}
	}
	
	/**
	 * Método que maneja las solicitudes POST para la ruta "/register"
	 * 
	 * @param usuario Objeto UsuarioDTO con los datos del formulario
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@PostMapping()
	public String registrarUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO usuario, HttpServletRequest request) {
		try {
			// Log
			Util.logInfo("RegistroControlador", "registrarUsuario", "Ha entrado en registrarUsuario");
			
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
				// Log
				Util.logInfo("RegistroControlador", "registrarUsuario", "El usuario ya ha iniciado sesión. Se redirige a home.");
				return "redirect:/home";
			}
			
			Boolean ok = usuarioImplementacion.registrarUsuario(usuario);

			if (ok) {
				// Se ha registrado el usuario correctamente
				return "redirect:/register?success";
			} else if (ok == null) {
				// Se ha producido un error al registrar el usuario
				return "redirect:/register?error";
			} else {
				// El email ya existe
				return "redirect:/register?email";
			}
		} catch (Exception e) {
			// Log
			Util.logError("RegistroControlador", "registrarUsuario", "Se ha producido un error.");
			return "redirect:/register?error";
		}
	}
}
