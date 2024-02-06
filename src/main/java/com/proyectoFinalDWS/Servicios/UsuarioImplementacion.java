package com.proyectoFinalDWS.Servicios;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyectoFinalDWS.DAOs.Acceso;
import com.proyectoFinalDWS.DAOs.Token;
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
	
	@Autowired
	private EmailImplementacion emailImplementacion;
	
	@Autowired
	private TokenImplementacion tokenImplementacion;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
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
	
	@Override
	public List<UsuarioDTO> obtieneTodosLosUsuarios() {
		try {
			// Obtenemos todos los usuarios de la base de datos y lo guardamos en una lista de tipo Usuario (DAO)
			List<Usuario> listaUsuariosDao = usuarioRepositorio.findAll();
			
			// Pasamos de DAO a DTO
			List<UsuarioDTO> listaUsuariosDTO = Util.listaUsuariosADto(listaUsuariosDao);
			
			// Devolvemos la lista de usuarios DTO
			return listaUsuariosDTO;
			
			/* OTRA OPCION
			 * return Util.listaUsuariosADto(usuarioRepositorio.findAll());
			 */
		} catch (Exception e) {
			System.out.println("[Error-AdminImplementacion-obtieneTodosLosUsuarios] Error al obtener todos los usuarios");
			return null;
		}
	}
	
	@Override
	public boolean borraUsuarioPorId(long id_usuario) {
		try {
			// Eliminamos el usuario por el id
			usuarioRepositorio.deleteById(id_usuario);
			
			// Ahora para comprobar si se ha eliminado vamos a buscar el usuario por el id
			Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findById(id_usuario);
			
			if(!usuarioEncontrado.isPresent())
				return true; // Devolvemos true si no existe
			
			return false; // En caso de que se haya encontrado un usuario con el id
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	@Override
	public UsuarioDTO obtieneUsuarioPorId(long id_usuario) {
		try {
			// Obtenemos el usuario
			Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findById(id_usuario);
			
			// Comprobamos si no se ha encontrado el usuario
			if(!usuarioEncontrado.isPresent())
				return null;
			
			// Si se ha encontrado vamos a convertir el usuario de DAO a DTO
			UsuarioDTO usuarioDTO = Util.usuarioADto(usuarioEncontrado.get());
			
			// Devolvemos el usuario convertido a DTO
			return usuarioDTO;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public boolean actualizaUsuario(UsuarioDTO usuarioDTO) {
		try {
			// Con el id del usuario pasado obtenemos el usuario de la base de datos
			Usuario usuarioEncontrado = usuarioRepositorio.findById(usuarioDTO.getId_usuario()).get();
			
			// Actualizamos algunos datos del usuarioEncontrado con el usuarioDTO
			usuarioEncontrado.setNombre_usuario(usuarioDTO.getNombre_usuario());
			usuarioEncontrado.setEmail_usuario(usuarioDTO.getEmail_usuario());
			usuarioEncontrado.setTlf_usuario(usuarioDTO.getTlf_usuario());
			usuarioEncontrado.setRutaImagen_usuario(usuarioDTO.getRutaImagen_usuario());
			
			// Actualizamos el usuario
			usuarioRepositorio.save(usuarioEncontrado);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
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
				emailImplementacion.enviarEmail("http://localhost:8080/activa-cuenta/activar", true, usuarioDevuelto);
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
			Token tokenDao = tokenImplementacion.obtieneToken(token);
			
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
			boolean ok = emailImplementacion.enviarEmail("http://localhost:8080/restablecer/cambiar-password", false, usuarioEncontrado);
			
			if(ok)
				return true; // Se ha enviado el correo correctamente
			else 
				return null; // Se ha producido un error al enviar el correo
		} catch (Exception e) {
			return null; // Se ha producido un error al enviar el correo
		}
	}

}
