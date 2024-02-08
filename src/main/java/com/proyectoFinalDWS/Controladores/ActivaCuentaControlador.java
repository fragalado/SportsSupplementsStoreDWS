package com.proyectoFinalDWS.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Servicios.UsuarioImplementacion;

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
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping("/estado")
	public String vistaActivarCuenta(Model model) {
		
		try {
			// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
			model.addAttribute("usuarioDTO", new UsuarioDTO());

			// Devolvemos la vista register
			return "confirmarEmail";
		} catch (Exception e) {
			return "redirect:/login";
		}
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/activa-cuenta/activar"
	 * @param token Código del token
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping("/activar")
	public String activarCuenta(@ModelAttribute("tk") String token) {
		
		try {
			// Controlamos que el token no este vacio
			if(token.isEmpty()) {
				return "redirect:/login";
			}
			
			// Si el token no esta vacio vamos a activar la cuenta
			boolean ok = usuarioImplementacion.activaCuenta(token);
			
			if(ok) {
				// Redirigimos a estado-cuenta con un parametro success
				return "redirect:/activa-cuenta/estado?success";
			} else {
				// Redirigimos a estado-cuenta con un parametro de error
				return "redirect:/activa-cuenta/estado?error";
			}
		} catch (Exception e) {
			return "redirect:/login";
		}
	}
}
