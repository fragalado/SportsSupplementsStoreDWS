package com.proyectoFinalDWS.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Servicios.AccesoImplementacion;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase Controlador para las vistas login y register
 * @author Fran Gallego
 * Fecha: 25/01/2024
 */
@Controller
@RequestMapping("/acceso")
public class LoginControlador {

	@Autowired
	private AccesoImplementacion accesoImpl;

	/**
	 * Método que maneja las solicitudes GET para la ruta "/acceso/login".
	 * 
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping("/login")
	public String vistaLogin(Model model, HttpServletRequest request) {
		// Control de sesión
		if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USU")) {
			return "redirect:/home";
		}

		// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
		model.addAttribute("usuarioDTO", new UsuarioDTO());

		// Devolvemos la vista login
		return "login";
	}

	/**
	 * Método que maneja las solicitudes GET para la ruta "/acceso/register".
	 * 
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping("/register")
	public String vistaRegister(Model model, HttpServletRequest request) {
		// Control de sesión
		if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USU")) {
			return "redirect:/home";
		}

		// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
		model.addAttribute("usuarioDTO", new UsuarioDTO());

		// Devolvemos la vista register
		return "register";
	}

	/**
	 * Método que maneja las solicitudes GET para la ruta "/acceso/modificar-password".
	 * 
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @return El nombre de la vista que se mostrará al usuario
	 */
	@GetMapping("/modificar-password")
	public String vistaModificarContrasenya(Model model, HttpServletRequest request) {
		// Control de sesión
		if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USU")) {
			return "redirect:/home";
		}
		
		// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
		model.addAttribute("usuarioDTO", new UsuarioDTO());

		// Devolvemos la vista register
		return "modificarContrasenya";
	}
	
	@GetMapping("/estado-cuenta")
	public String vistaActivarCuenta(Model model, HttpServletRequest request) {
		// Control de sesión
		if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USU")) {
			return "redirect:/home";
		}
		
		// Creamos un nuevo objeto UsuarioDTO y lo agregamos al modelo
		model.addAttribute("usuarioDTO", new UsuarioDTO());

		// Devolvemos la vista register
		return "confirmarEmail";
	}
	
	@GetMapping("/activar-cuenta")
	public String activarCuenta(@ModelAttribute("tk") String token, HttpServletRequest request) {
		// Control de sesión
		if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USU")) {
			return "redirect:/home";
		}
		
		// Controlamos que el token no este vacio
		if(token.isEmpty()) {
			return "redirect:/acceso/login";
		}
		
		// Si el token no esta vacio vamos a activar la cuenta
		boolean ok = accesoImpl.activaCuenta(token);
		
		if(ok) {
			// Redirigimos a estado-cuenta con un parametro success
			return "redirect:/acceso/estado-cuenta?success";
		} else {
			// Redirigimos a estado-cuenta con un parametro de error
			return "redirect:/acceso/estado-cuenta?error";
		}
	}

	@PostMapping("/register")
	public String registrarUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO usuario) {
		try {
			Boolean ok = accesoImpl.registrarUsuario(usuario);

			if (ok) {
				// Se ha registrado el usuario correctamente
				return "redirect:/acceso/register?success";
			} else if (ok == null) {
				// Se ha producido un error al registrar el usuario
				return "redirect:/acceso/register?error";
			} else {
				// El email ya existe
				return "redirect:/acceso/register?email";
			}
		} catch (Exception e) {
			System.out.println("[Error-LoginControlador-registrarUsuario] Error al registrar al usuario");
			return "redirect:/acceso/register?error";
		}
	}
}
