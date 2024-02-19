package com.proyectoFinalDWS.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Servicios.UsuarioImplementacion;
import com.proyectoFinalDWS.Utiles.Util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase controlador para la vista recuperar contraseña
 * @author Fran Gallego
 * Fecha: 06/02/2024
 */
@Controller
@RequestMapping("/restablecer")
public class RestablecerPasswordControlador {

	@Autowired
	private UsuarioImplementacion usuarioImplementacion;
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/restablecer".
	 * 
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping()
	public String vistaModificarContrasenya(Model model, HttpServletRequest request) {
		
		try {
			// Log
			Util.logInfo("RestablecerPasswordControlador", "vistaModificarContrasenya", "Ha entrado en vistaModificarContrasenya");
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
				// Log
				Util.logInfo("RestablecerPasswordControlador", "vistaModificarContrasenya", "El usuario ya ha iniciado sesión. Se redirige a home.");
				return "redirect:/home";
			}
						
			// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
			model.addAttribute("usuarioDTO", new UsuarioDTO());

			// Devolvemos la vista register
			return "modificarContrasenya";
		} catch (Exception e) {
			// Log
			Util.logError("RestablecerPasswordControlador", "vistaModificarContrasenya", "Se ha producido un error.");
			return "modificarContrasenya";
		}
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/restablecer/cambia-password"
	 * 
	 * @param token Token
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping("/cambiar-password")
	public String vistaCambiaPassword(@ModelAttribute("tk") String token, Model model, HttpServletRequest request) {
		
		try {
			// Log
			Util.logInfo("RestablecerPasswordControlador", "vistaCambiaPassword", "Ha entrado en vistaCambiaPassword");
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
				// Log
				Util.logInfo("RestablecerPasswordControlador", "vistaCambiaPassword", "El usuario ya ha iniciado sesión. Se redirige a home.");
				return "redirect:/home";
			}
			
			// Controlamos que el token no este vacio
			if (token.isEmpty()) {
				// Log
				Util.logInfo("RestablecerPasswordControlador", "vistaCambiaPassword", "El token esta vacio. Se redirige a login.");
				return "redirect:/login";
			}

			// Si el token no esta vacio:
			// Agregamos al model el token
			model.addAttribute("token", token);

			// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
			model.addAttribute("usuarioDTO", new UsuarioDTO());

			// Devolvemos la vista
			return "cambiarPassword";
		} catch (Exception e) {
			// Log
			Util.logError("RestablecerPasswordControlador", "vistaCambiaPassword", "Se ha producido un error.");
			return "cambiarPassword";
		}
	}
	
	/**
	 * Método que maneja las solicitudes POST para la ruta "/restablecer"
	 * 
	 * @param usuario Objeto UsuarioDTO que contiene los datos del formulario
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@PostMapping()
	public String restablecerPassword(@ModelAttribute("usuarioDTO") UsuarioDTO usuario, HttpServletRequest request) {
		try {
			// Log
			Util.logInfo("RestablecerPasswordControlador", "restablecerPassword", "Ha entrado en restablecerPassword");
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
				// Log
				Util.logInfo("RestablecerPasswordControlador", "restablecerPassword", "El usuario ya ha iniciado sesión. Se redirige a home.");
				return "redirect:/home";
			}
			
			Boolean ok = usuarioImplementacion.restablecePassword(usuario);

			if (ok) {
				// Se ha enviado el correo al usuario
				return "redirect:/restablecer?success";
			} else if (ok == null) {
				// Se ha producido un error
				return "redirect:/restablecer?error";
			} else {
				// El email introducido no existe
				return "redirect:/restablecer?email";
			}
		} catch (Exception e) {
			// Log
			Util.logError("RestablecerPasswordControlador", "restablecerPassword", "Se ha producido un error.");
			return "redirect:/restablecer?error";
		}
	}
	
	/**
	 * Método que maneja las solicitudes POST para la ruta "/restablecer/cambiar-password"
	 * 
	 * @param token Token
	 * @param usuario Objeto UsuarioDTO que contiene los datos del formulario
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@PostMapping("/cambiar-password")
	public String cambiaPassword(@RequestParam String token, @ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO, HttpServletRequest request) {
		try {
			// Log
			Util.logInfo("RestablecerPasswordControlador", "cambiaPassword", "Ha entrado en cambiaPassword");
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
				// Log
				Util.logInfo("RestablecerPasswordControlador", "cambiaPassword", "El usuario ya ha iniciado sesión. Se redirige a home.");
				return "redirect:/home";
			}
			
			// Comprobamos que las contraseñas introducidas sean iguales
			if(!usuarioDTO.getPsswd_usuario().equals(usuarioDTO.getEmail_usuario()))
				return "redirect:/restablecer/cambiar-password?password&tk="+token;
			
			// Si son iguales modificamos la contraseña del usuario
			boolean ok = usuarioImplementacion.modificaPassword(token, usuarioDTO);
			
			// Controlamos la respuesta
			if(ok)
				return "redirect:/restablecer/cambiar-password?success&tk="+token;
			else
				return "redirect:/restablecer/cambiar-password?error&tk="+token;
		} catch (Exception e) {
			// Log
			Util.logError("RestablecerPasswordControlador", "cambiaPassword", "Se ha producido un error.");
			return "redirect:/restablecer/cambiar-password?error&tk="+token;
		}
	}
}
