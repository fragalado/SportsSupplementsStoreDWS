package com.proyectoFinalDWS.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase controlador para las vistas admin
 * @author Fran Gallego
 * Fecha: 28/01/2024 
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
	
	@GetMapping("/administracion-usuarios")
	public String vistaAdministracionUsuarios(Model model, HttpServletRequest request) {
		// Control de sesión
		if(!request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/home";
		}
		
		return "administracionUsuarios";
	}
	
	@GetMapping("/administracion-suplementos")
	public String vistaAdministracionSuplementos(Model model, HttpServletRequest request) {
		// Control de sesión
		if(!request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/home";
		}
		
		return "administracionSuplementos";
	}
}
