package com.proyectoFinalDWS.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyectoFinalDWS.DAOs.Acceso;
import com.proyectoFinalDWS.DAOs.Usuario;
import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Repositorios.UsuarioRepository;
import com.proyectoFinalDWS.Utiles.Util;

import jakarta.transaction.Transactional;

/**
 * Implementación de la interfaz Acceso.
 * @author Fran Gallego
 * Fecha: 26/01/2024
 */
@Service
@Transactional
public class AccesoImplementacion implements AccesoInterfaz {

	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Boolean registrarUsuario(UsuarioDTO usuario) {
		//
		try {
			// Buscamos si existe un usuario con el email introducido
			Usuario usuarioEncontrado = usuarioRepositorio.findByEmailUsuario(usuario.getEmail_usuario());

			if (usuarioEncontrado != null) {
				// Se ha encontrado un usuario con el email introducido
				// Luego devolveremos false
				return false;
			}
			
			// Si no se ha encontrado seguimos con el registro
			// Encriptamos la contraseña
			usuario.setPsswd_usuario(passwordEncoder.encode(usuario.getPsswd_usuario()));
			
			// Convertimos el usuario (DTO) a DAO
			Usuario usuarioDAO = Util.usuarioADao(usuario);
			
			// Creamos un Acceso para añadirselo al usuario
			Acceso accesoUsu = new Acceso(1, "Usu", "Usuarios de la tienda");
			usuarioDAO.setAcceso(accesoUsu);
			
			// Lo guardamos en la base de datos
			Usuario usuarioDevuelto =  usuarioRepositorio.save(usuarioDAO);
			System.out.println(usuarioDevuelto.getId_usuario());
			
			// Si el Usuario devuelto es distinto de null es porque se ha registrado correctamente
			return usuarioDevuelto != null;
		} catch (IllegalArgumentException  e) {
			System.out.println("[Error-AccesoImplementacion-registrarUsuario] Error el usuario es nulo");
			return null;
		} catch (OptimisticLockingFailureException e) {
		    // Excepcion de concurrencia optimista
		    // Esto puede ocurrir si otro proceso ha modificado los datos mientras
		    // esta transacción estaba realizando sus operaciones.
		    System.out.println("[Error-AccesoImplementacion-registrarUsuario] Error de concurrencia optimista");
			return null;
		}
	}

}
