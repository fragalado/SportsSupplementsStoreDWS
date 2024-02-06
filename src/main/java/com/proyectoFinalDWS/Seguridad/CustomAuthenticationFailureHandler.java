package com.proyectoFinalDWS.Seguridad;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		System.out.println(exception);
		if(exception instanceof BadCredentialsException) {
			// Controlamos el error de contraseña o email incorrectos
			response.sendRedirect("/login?email");
		}
		if(exception instanceof InternalAuthenticationServiceException) {
			// Controlamos el error de cuenta no activada
			response.sendRedirect("/login?activada");
		}
	}

}
