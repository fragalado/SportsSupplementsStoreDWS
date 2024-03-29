package com.proyectoFinalDWS.Servicios;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
			// Log
			Util.logInfo("UsuarioImplementacion", "obtieneUsuarioPorEmail", "Ha entrado en obtieneUsuarioPorEmail");
			
			// Buscamos el usuario por el email
			Usuario usuarioEncontrado = usuarioRepositorio.findByEmailUsuario(email);
			
			// Convertimos el usuario a DTO y lo devolvemos
			return Util.usuarioADto(usuarioEncontrado);
		} catch (Exception e) {
			// Log
			Util.logError("UsuarioImplementacion", "obtieneUsuarioPorEmail", "Se ha producido un error al obtener el usuario por el email.");
			return null; // Devuelve null en caso de no encontrarlo
		}
	}
	
	@Override
	public List<UsuarioDTO> obtieneTodosLosUsuarios() {
		try {
			// Log
			Util.logInfo("UsuarioImplementacion" ,"obtieneTodosLosUsuarios", "Ha entrado en obtieneTodosLosUsuarios");
			
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
			// Log
			Util.logError("UsuarioImplementacion" ,"obtieneTodosLosUsuarios", "Se ha producido un error al obtener todos los usuarios de la base de datos.");
			return null;
		}
	}
	
	@Override
	public boolean borraUsuarioPorId(long id_usuario) {
		try {
			// Log
			Util.logInfo("UsuarioImplementacion" ,"borraUsuarioPorId", "Ha entrado en borraUsuarioPorId");
			
			// Eliminamos el usuario por el id
			usuarioRepositorio.deleteById(id_usuario);
			
			// Ahora para comprobar si se ha eliminado vamos a buscar el usuario por el id
			UsuarioDTO usuarioDTO = obtieneUsuarioPorId(id_usuario);
			
			if(usuarioDTO == null)
				return true; // Devolvemos true si no existe
			
			return false; // En caso de que se haya encontrado un usuario con el id
		} catch (IllegalArgumentException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"borraUsuarioPorId", "El id introducido es null.");
			return false;
		}
	}
	
	@Override
	public UsuarioDTO obtieneUsuarioPorId(long id_usuario) {
		try {
			// Log
			Util.logInfo("UsuarioImplementacion" ,"obtieneUsuarioPorId", "Ha entrado en obtieneUsuarioPorId");
						
			// Obtenemos el usuario
			Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findById(id_usuario);
			
			// Comprobamos si no se ha encontrado el usuario
			if(!usuarioEncontrado.isPresent())
				return null;
			
			// Si se ha encontrado vamos a convertir el usuario de DAO a DTO
			UsuarioDTO usuarioDTO = Util.usuarioADto(usuarioEncontrado.get());
			
			// Devolvemos el usuario convertido a DTO
			return usuarioDTO;
		} catch (IllegalArgumentException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"obtieneUsuarioPorId", "El id del usuario es null");
			return null;
		} catch (NoSuchElementException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"obtieneUsuarioPorId", "El elemento no existe o no tiene valor");
			return null;			
		}
	}
	
	@Override
	public boolean actualizaUsuario(UsuarioDTO usuarioDTO) {
		try {
			// Log
			Util.logInfo("UsuarioImplementacion" ,"actualizaUsuario", "Ha entrado en actualizaUsuario");
			
			// Con el id del usuario pasado obtenemos el usuario de la base de datos
			Usuario usuarioEncontrado = Util.usuarioADao(obtieneUsuarioPorId(usuarioDTO.getId_usuario()));
			
			// Actualizamos algunos datos del usuarioEncontrado con el usuarioDTO
			usuarioEncontrado.setNombre_usuario(usuarioDTO.getNombre_usuario());
			usuarioEncontrado.setEmail_usuario(usuarioDTO.getEmail_usuario());
			usuarioEncontrado.setTlf_usuario(usuarioDTO.getTlf_usuario());
			if(usuarioDTO.getImagen_usuario() != null)
				usuarioEncontrado.setImagen_usuario(Util.convertirAByteArray(usuarioDTO.getImagen_usuario()));
			
			// Actualizamos el usuario
			usuarioRepositorio.save(usuarioEncontrado);
			
			return true;
		} catch (IllegalArgumentException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"actualizaUsuario", "El objeto usuario es null.");
			return false;
		} catch (OptimisticLockingFailureException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"actualizaUsuario", "Concurrencia optimista. La entidad no existe en la base de datos o utiliza optimistic locking");
			return false;
		}
	}
	
	@Override
	public Boolean registrarUsuario(UsuarioDTO usuario) {
		
		try {
			// Log
			Util.logInfo("UsuarioImplementacion" ,"registrarUsuario", "Ha entrado en registrarUsuario");
						
			// Buscamos si existe un usuario con el email introducido
			UsuarioDTO usuarioEncontrado = obtieneUsuarioPorEmail(usuario.getEmail_usuario());

			if (usuarioEncontrado != null) {
				// Se ha encontrado un usuario con el email introducido
				// Luego devolveremos false
				// Log
				Util.logInfo("UsuarioImplementacion" ,"registrarUsuario", "Ya existe un usuario con el email introducido.");
				return false;
			}
			
			// Si no se ha encontrado seguimos con el registro
			// Encriptamos la contraseña
			usuario.setPsswd_usuario(passwordEncoder.encode(usuario.getPsswd_usuario()));
			
			// Convertimos el usuario (DTO) a DAO
			Usuario usuarioDAO = Util.usuarioADao(usuario);
			
			// Lo guardamos en la base de datos
			Usuario usuarioDevuelto =  usuarioRepositorio.save(usuarioDAO);
			
			// Si el Usuario devuelto es distinto de null es porque se ha registrado correctamente
			if(usuarioDevuelto != null) {
				// Enviamos el correo
				emailImplementacion.enviarEmail("http://localhost:8080/activa-cuenta/activar", true, usuarioDevuelto);
			}
			return usuarioDevuelto != null;
		} catch (IllegalArgumentException  e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"registrarUsuario", "El objeto usuario es null");
			return null;
		} catch (OptimisticLockingFailureException e) {
		    // Excepcion de concurrencia optimista
		    // Esto puede ocurrir si otro proceso ha modificado los datos mientras
		    // esta transacción estaba realizando sus operaciones.
			// Log
			Util.logError("UsuarioImplementacion" ,"registrarUsuario", "concurrencia optimista");
			return null;
		}
	}

	@Override
	public boolean activaCuenta(String token) {
		try {
			// Log
			Util.logInfo("UsuarioImplementacion" ,"activaCuenta", "Ha entrado en activaCuenta");
						
			// Obtenemos el token de la base de datos
			Token tokenDao = tokenImplementacion.obtieneToken(token);
			
			// Ahora comprobamos si el token no ha caducado
			// Obtenemos la fecha actual
			Calendar fechaActual = Calendar.getInstance();
			
			// Comparamos la fechaActual con la fecha del token
			if (fechaActual.compareTo(tokenDao.getFch_fin_token()) < 0 || fechaActual.compareTo(tokenDao.getFch_fin_token()) == 0) {
				// La fecha actual es menor que la fecha del token o son iguales, luego seguimos con el proceso
				// Obtenemos el usuario del token
				Usuario usuarioDao = tokenDao.getUsuario();
				
				// Modificamos la propiedad estaActivado y la ponemos en true
				usuarioDao.setEstaActivado_usuario(true);
				
				// Actualizamos en la base de datos
				Usuario usuarioDevuelto =  usuarioRepositorio.save(usuarioDao);
				
				// Comprobamos si se ha actualizado correctamente
				if(usuarioDevuelto != null && usuarioDevuelto.isEstaActivado_usuario()) {
					return true; // El usuario ha sido activado
				} else {
					return false; // El usuario no ha sido activado
				}
	        } else {
	        	// La fecha actual es mayor que la fecha del token, luego ha caducado
	        	// Log
	        	Util.logInfo("UsuarioImplementacion" ,"activaCuenta", "El token ha caducado");
	        	return false;
	        }
		} catch (NullPointerException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"activaCuenta", "calendario nulo");
			return false;
		} catch (IllegalArgumentException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"activaCuenta", "objeto nulo o invalido");
			return false;
		} catch (OptimisticLockingFailureException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"activaCuenta", "concurrencia optimista.");
			return false;
		}
	}

	@Override
	public Boolean restablecePassword(UsuarioDTO usuarioDto) {
		try {
			// Log
			Util.logInfo("UsuarioImplementacion" ,"restablecePassword", "Ha entrado en restablecePassword");
			
			// Obtenemos el usuario de la base de datos
			Usuario usuarioEncontrado = Util.usuarioADao(obtieneUsuarioPorEmail(usuarioDto.getEmail_usuario()));
			
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
			// Log
			Util.logError("UsuarioImplementacion" ,"restablecePassword", "Se ha producido un error.");
			return null; // Se ha producido un error al enviar el correo
		}
	}

	@Override
	public boolean modificaPassword(String token, UsuarioDTO usuarioDTO) {
		try {
			// Log
			Util.logInfo("UsuarioImplementacion" ,"modificaPassword", "Ha entrado en modificaPassword");
						
			// Obtenemos el token
			Token tokenDao = tokenImplementacion.obtieneToken(token);
			
			// Ahora comprobamos si el token no ha caducado
			// Obtenemos la fecha actual
			Calendar fechaActual = Calendar.getInstance();

			// Comparamos la fechaActual con la fecha del token
			if (fechaActual.compareTo(tokenDao.getFch_fin_token()) < 0
					|| fechaActual.compareTo(tokenDao.getFch_fin_token()) == 0) {
				// La fecha actual es menor que la fecha del token o son iguales, luego seguimos
				// con el proceso
				// Obtenemos el usuario
				Usuario usuarioDao = tokenDao.getUsuario();

				// Modificamos la contraseña
				usuarioDao.setPsswd_usuario(passwordEncoder.encode(usuarioDTO.getPsswd_usuario()));

				// Actualizamos en la base de datos
				usuarioRepositorio.save(usuarioDao);
				
				return true;
			} else {
				// La fecha actual es mayor que la fecha del token, luego ha caducado
				// Log
				Util.logInfo("UsuarioImplementacion" ,"modificaPassword", "El token ha caducado.");
				return false;
			}
		} catch (NullPointerException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"modificaPassword", "al comparar un calendario null");
			return false;
		} catch (IllegalArgumentException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"modificaPassword", "la entidad es null");
			return false;
		} catch (OptimisticLockingFailureException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"modificaPassword", "concurrencia optimista");
			return false;
		}
	}

	@Override
	public boolean agregaUsuario(UsuarioDTO usuarioDTO) {
		try {
			// Log
			Util.logInfo("UsuarioImplementacion" ,"agregaUsuario", "Ha entrado en agregaUsuario");
			
			// Buscamos si existe un usuario con el email introducido
			UsuarioDTO usuarioEncontrado = obtieneUsuarioPorEmail(usuarioDTO.getEmail_usuario());

			if (usuarioEncontrado != null) {
				// Se ha encontrado un usuario con el email introducido
				// Luego devolveremos false
				// Log
				Util.logInfo("UsuarioImplementacion" ,"agregaUsuario", "El email introducido ya existe");
				return false;
			}
			
			// Si no se ha encontrado
			// Encriptamos la contraseña
			usuarioDTO.setPsswd_usuario(passwordEncoder.encode(usuarioDTO.getPsswd_usuario()));
			
			// Activamos la cuenta
			usuarioDTO.setEstaActivado_usuario(true);
			
			// Guardamos el usuario
			usuarioRepositorio.save(Util.usuarioADao(usuarioDTO));
			
			return true;
		} catch (IllegalArgumentException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"agregaUsuario", "La entidad es null");
			return false;
		} catch (OptimisticLockingFailureException e) {
			// Log
			Util.logError("UsuarioImplementacion" ,"agregaUsuario", "concurrencia optimista");
			return false;
		}
	}

}
