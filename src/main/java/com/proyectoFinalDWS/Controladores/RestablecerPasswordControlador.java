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
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
				return "redirect:/home";
			}
						
			// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
			model.addAttribute("usuarioDTO", new UsuarioDTO());

			// Devolvemos la vista register
			return "modificarContrasenya";
		} catch (Exception e) {
			return "modificarContrasenya";
		}
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/restablecer/cambia-password"
	 * 
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping("/cambiar-password")
	public String vistaCambiaPassword(@ModelAttribute("tk") String token, Model model, HttpServletRequest request) {
		
		try {
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
				return "redirect:/home";
			}
			
			// Controlamos que el token no este vacio
			if (token.isEmpty()) {
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
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
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
			System.out.println("[Error-RestablecerPasswordControlador-restablecerPassowrd] Error al mandar correo para restablecer contraseña");
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
			// Control de sesion
			if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
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
			// TODO
			return "redirect:/restablecer/cambiar-password?error&tk="+token;
		}
	}
}
