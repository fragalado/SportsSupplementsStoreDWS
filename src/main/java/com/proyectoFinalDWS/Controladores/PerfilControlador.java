package com.proyectoFinalDWS.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Servicios.UsuarioImplementacion;

/**
 * Clase controlador para la vista perfil
 * @author Fran Gallego
 * Fecha: 06/02/2024
 */
@Controller
@RequestMapping("/perfil")
public class PerfilControlador {
	
	@Autowired
	private UsuarioImplementacion usuarioImplementacion;
	
	@GetMapping()
	public String vistaPerfil(Model model, Authentication authentication) {
		try {
			
			// Obtenemos el usuario por el email
			UsuarioDTO usuario = usuarioImplementacion.obtieneUsuarioPorEmail(authentication.getName());
			
			if(usuario != null) {
				// Agregamos al model el usuario
				model.addAttribute("usuarioDTO", usuario);
				
				// Devolvemos la vista
				return "perfil";
			} else {
				return "redirect:/home";
			}
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/home";
		}
	}
}