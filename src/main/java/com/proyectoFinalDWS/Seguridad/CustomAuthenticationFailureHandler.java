package com.proyectoFinalDWS.Seguridad;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Clase para manejar los errores personalizados cuando ocurre un fallo en la autentifación
 * Implementa la interfaz AuthenticationFailureHandler de Spring Security
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	// Este método se llamará cuando la autentificación falla
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		// Verifica si la excepción es del tipo BadCredentialsException (credenciales incorrectas)
		if(exception instanceof BadCredentialsException) {
			// Controlamos el error de contraseña o email incorrectos
			response.sendRedirect("/login?email");
		}
		
		// Verifica si la excepción es del tipo InternalAuthenticationServiceException (cuenta no activada)
		if(exception instanceof InternalAuthenticationServiceException) {
			// Controlamos el error de cuenta no activada
			response.sendRedirect("/login?activada");
		}
	}

}
