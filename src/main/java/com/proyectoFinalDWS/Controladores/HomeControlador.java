package com.proyectoFinalDWS.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;


/**
 * Clase Controlador para las vistas relacionados con el home/bienvenida
 */
@Controller
@RequestMapping("/home")
public class HomeControlador {
	
	@GetMapping("")
	public String vistaHome(Model model) {
		// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
		model.addAttribute("usuarioDTO", new UsuarioDTO());

		// Devolvemos la vista register
		return "home";
	}
}
