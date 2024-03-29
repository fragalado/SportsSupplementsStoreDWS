package com.proyectoFinalDWS.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Servicios.UsuarioImplementacion;
import com.proyectoFinalDWS.Utiles.Util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase controlador para activar cuenta usuario
 * @author Fran Gallego
 * Fecha: 06/02/2024
 */
@Controller
@RequestMapping("/activa-cuenta")
public class ActivaCuentaControlador {

	@Autowired
	private UsuarioImplementacion usuarioImplementacion;
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/activa-cuenta/estado"
	 * 
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping("/estado")
	public String vistaActivarCuenta(Model model, HttpServletRequest request) {
		
		try {
			// Log
			Util.logInfo("ActivaCuentaControlador", "vistaActivarCuenta", "Ha entrado en vistaActivarCuenta.");
			
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
				// Log
				Util.logInfo("ActivaCuentaControlador", "vistaActivarCuenta", "El usuario ya ha iniciado sesión. Se redirige a home.");
				return "redirect:/home";
			}
			
			// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
			model.addAttribute("usuarioDTO", new UsuarioDTO());

			// Devolvemos la vista register
			return "confirmarEmail";
		} catch (Exception e) {
			// Log
			Util.logError("ActivaCuentaControlador", "vistaActivarCuenta", "Se ha producido un error.");
			return "redirect:/login";
		}
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/activa-cuenta/activar"
	 * 
	 * @param token Código del token
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping("/activar")
	public String activarCuenta(@ModelAttribute("tk") String token, HttpServletRequest request) {
		
		try {
			// Log
			Util.logInfo("ActivaCuentaControlador", "activarCuenta", "Ha entrado en activarCuenta");
			
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
				// Log
				Util.logInfo("ActivaCuentaControlador", "activarCuenta", "El usuario ya ha iniciado sesión. Se redirige a home.");
				return "redirect:/home";
			}
			
			// Controlamos que el token no este vacio
			if(token.isEmpty()) {
				// Log
				Util.logInfo("ActivaCuentaControlador", "activarCuenta", "El token esta vacio. Se redirige a login.");
				return "redirect:/login";
			}
			
			// Si el token no esta vacio vamos a activar la cuenta
			boolean ok = usuarioImplementacion.activaCuenta(token);
			
			if(ok) {
				// Redirigimos a activa-cuenta con un parametro success
				return "redirect:/activa-cuenta/estado?success";
			} else {
				// Redirigimos a activa-cuenta con un parametro de error
				return "redirect:/activa-cuenta/estado?error";
			}
		} catch (Exception e) {
			// Log
			Util.logError("ActivaCuentaControlador", "activarCuenta", "Se ha producido un error.");
			return "redirect:/login";
		}
	}
}
