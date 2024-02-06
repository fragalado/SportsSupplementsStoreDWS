package com.proyectoFinalDWS.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoFinalDWS.DAOs.Usuario;
import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Repositorios.UsuarioRepository;
import com.proyectoFinalDWS.Utiles.Util;

import jakarta.transaction.Transactional;

/**
 * Implementación de la interfaz Usuario
 * @author Fran Gallego
 * Fecha: 06/02/2024
 */
@Service
@Transactional
public class UsuarioImplementacion implements UsuarioInterfaz {

	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	@Override
	public UsuarioDTO obtieneUsuarioPorEmail(String email) {
		try {
			// Buscamos el usuario por el email
			Usuario usuarioEncontrado = usuarioRepositorio.findByEmailUsuario(email);
			
			// Convertimos el usuario a DTO y lo devolvemos
			return Util.usuarioADto(usuarioEncontrado);
		} catch (Exception e) {
			return null; // Devuelve null en caso de no encontrarlo
		}
	}

}
