package com.proyectoFinalDWS.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectoFinalDWS.Servicios.AdminImplementacion;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase controlador para las vistas admin
 * @author Fran Gallego
 * Fecha: 28/01/2024 
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
	
	@Autowired
	private AdminImplementacion adminImplementacion;
	
	@GetMapping("/administracion-usuarios")
	public String vistaAdministracionUsuarios(Model model, HttpServletRequest request) {
		// Control de sesión
		if(!request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/home";
		}
		
		// Obtenemos una lista con todos los usuarios y lo agregamos al modelo
		model.addAttribute("listaUsuariosDTO", adminImplementacion.obtieneTodosLosUsuarios());
		
		// Devolvemos la vista
		return "administracionUsuarios";
	}
	
	@GetMapping("/administracion-suplementos")
	public String vistaAdministracionSuplementos(Model model, HttpServletRequest request) {
		// Control de sesión
		if(!request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/home";
		}
		
		// Obtenemos una lista con todos los suplementos y lo agregamos al modelo
		model.addAttribute("listaSuplementosDTO", adminImplementacion.obtieneTodosLosSuplementos());
		
		// Devolvemos la vista
		return "administracionSuplementos";
	}
	
	@GetMapping("/borra-usuario/{id_usuario}")
	public String borraUsuario(@PathVariable Long id_usuario, HttpServletRequest request) {
		// Control de sesión
		if(!request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/home";
		}
		
		// Eliminamos el usuario por el id_usuario
		boolean ok = adminImplementacion.borraUsuarioPorId(id_usuario);
		
		// Devolvemos la vista con un parametro segun se haya elimando o no
		if(ok)
			return "redirect:/admin/administracion-usuarios?success";
		else
			return "redirect:/admin/administracion-usuarios?error";
	}
}
