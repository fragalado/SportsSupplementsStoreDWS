package com.proyectoFinalDWS.Servicios;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyectoFinalDWS.DAOs.Acceso;
import com.proyectoFinalDWS.DAOs.Token;
import com.proyectoFinalDWS.DAOs.Usuario;
import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Repositorios.TokenRepository;
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
	private TokenRepository tokenRepositorio;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailImplementacion emailImpl;

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
			
			// Si el Usuario devuelto es distinto de null es porque se ha registrado correctamente
			if(usuarioDevuelto != null) {
				// Enviamos el correo
				emailImpl.enviarEmail("http://localhost:8080/acceso/activar-cuenta", true, usuarioDevuelto);
			}
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

	@Override
	public boolean activaCuenta(String token) {
		try {
			// Obtenemos el token de la base de datos
			Token tokenDao = tokenRepositorio.findByCodToken(token);
			
			// Ahora comprobamos si el token no ha caducado
			// Obtenemos la fecha actual
			Calendar fechaActual = Calendar.getInstance();
			
			// Comparamos la fechaActual con la fecha del token
			if (fechaActual.compareTo(tokenDao.getFch_fin_token()) < 0 || fechaActual.compareTo(tokenDao.getFch_fin_token()) == 0) {
				// La fecha actual es menor que la fecha del token o son iguales, luego seguimos con el proceso
				Usuario usuarioDao = tokenDao.getUsuario();
				
				// Modificamos la propiedad estaActivado y la ponemos en true
				usuarioDao.setEstaActivado_usuario(true);
				
				// Actualizamos en la base de datos
				Usuario usuarioDevuelto =  usuarioRepositorio.save(usuarioDao);
				if(usuarioDevuelto != null && usuarioDevuelto.isEstaActivado_usuario()) {
					return true; // El usuario ha sido activado
				} else {
					return false; // El usuario no ha sido activado
				}
	        } else {
	        	// La fecha actual es mayor que la fecha del token, luego ha caducado
	        	return false;
	        }
		} catch (NullPointerException e) {
			System.out.println("[Error-AccesoImplementacion-activaCuenta] Error al intentar compararse un calendario nulo.");
			return false;
		} catch (IllegalArgumentException e) {
			System.out.println("[Error-AccesoImplementacion-activaCuenta] Error objeto nulo o invalido.");
			return false;
		} catch (OptimisticLockingFailureException e) {
			System.out.println("[Error-AccesoImplementacion-activaCuenta] Error de concurrencia optimista.");
			return false;
		}
	}

	@Override
	public Boolean restablePassword(UsuarioDTO usuarioDto) {
		try {
			// Obtenemos el usuario de la base de datos
			Usuario usuarioEncontrado = usuarioRepositorio.findByEmailUsuario(usuarioDto.getEmail_usuario());
			
			// Si usuarioEncontrado es null devolvemos false
			if(usuarioEncontrado == null)
				return false; // El email introducido no existe
			
			// Si llega aqui es porque se ha encontrado el usuario.
			boolean ok = emailImpl.enviarEmail("http://localhost:8080/acceso/cambiar-password", false, usuarioEncontrado);
			
			if(ok)
				return true; // Se ha enviado el correo correctamente
			else 
				return null; // Se ha producido un error al enviar el correo
		} catch (Exception e) {
			return null; // Se ha producido un error al enviar el correo
		}
	}
	
	

}
