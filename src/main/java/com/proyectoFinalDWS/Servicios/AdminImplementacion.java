package com.proyectoFinalDWS.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoFinalDWS.DAOs.Suplemento;
import com.proyectoFinalDWS.DAOs.Usuario;
import com.proyectoFinalDWS.DTOs.SuplementoDTO;
import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Repositorios.SuplementoRepository;
import com.proyectoFinalDWS.Repositorios.UsuarioRepository;
import com.proyectoFinalDWS.Utiles.Util;

import jakarta.transaction.Transactional;

/**
 * Implementación de la interfaz Admin
 * @author Fran Gallego
 * Fecha: 03/02/2024
 */
@Service
@Transactional
public class AdminImplementacion implements AdminInterfaz {

	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	@Autowired
	private SuplementoRepository suplementoRepositorio;
	
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
	public List<SuplementoDTO> obtieneTodosLosSuplementos() {
		try {
			// Obtenemos todos los suplementos de la base de datos y lo guardamos en una lista de tipo Suplemento (DAO)
			List<Suplemento> listaSuplementosDao = suplementoRepositorio.findAll();
			
			// Pasamos de DAO a DTO
			List<SuplementoDTO> listaSuplementosDTO = Util.listaSuplementosADto(listaSuplementosDao);
			
			// Devolvemos la lista de suplementos DTO
			return listaSuplementosDTO;
			
			/* OTRA OPCION
			 * return Util.listaUsuariosADto(usuarioRepositorio.findAll());
			 */
		} catch (Exception e) {
			System.out.println("[Error-AdminImplementacion-obtieneTodosLosSuplementos] Error al obtener todos los suplementos");
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
	public boolean borraSuplementoPorId(long id_suplemento) {
		try {
			// Eliminamos el suplemento por el id
			suplementoRepositorio.deleteById(id_suplemento);
			
			// Ahora para comprobar si se ha eliminado vamos a buscar el suplemento por el id
			Optional<Suplemento> suplementoEncontrado = suplementoRepositorio.findById(id_suplemento);
			
			if(!suplementoEncontrado.isPresent())
				return true; // Devolvemos true si no existe
			
			return false; // En caso de que se haya encontrado un suplemento con el id
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
	public SuplementoDTO obtieneSuplementoPorId(long id_suplemento) {
		try {
			// Obtenemos el suplemento
			Optional<Suplemento> suplementoEncontrado = suplementoRepositorio.findById(id_suplemento);
			
			// Comprobamos si no se ha encontrado el suplemento
			if(!suplementoEncontrado.isPresent())
				return null;
			
			// Si se ha encontrado vamos a convertir el suplemento de DAO a DTO
			SuplementoDTO suplementoDTO = Util.suplementoADto(suplementoEncontrado.get());
			
			// Devolvemos el suplemento convertido a DTO
			return suplementoDTO;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean agregaSuplemento(SuplementoDTO suplementoDTO) {
		try {
			// Convertimos el suplemento de DTO a DAO
			Suplemento suplementoDAO = Util.suplementoADao(suplementoDTO);
			
			// Agregamos el suplemento a la base de datos
			Suplemento suplementoDevuelto = suplementoRepositorio.save(suplementoDAO);
			
			// Comprobamos si se ha añadido
			if(suplementoDevuelto != null)
				return true; // Se ha añadido correctamente
			
			return false; // Si se ha producido algún error
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean actualizaSuplemento(SuplementoDTO suplementoDTO) {
		try {
			// Con el id del suplemento pasado obtenemos el suplemento de la base de datos
			Suplemento suplementoEncontrado = suplementoRepositorio.findById(suplementoDTO.getId_suplemento()).get();
			
			// Actualizamos algunos datos del suplementoEncontrado con el suplementoDTO
			suplementoEncontrado.setNombre_suplemento(suplementoDTO.getNombre_suplemento());
			suplementoEncontrado.setDesc_suplemento(suplementoDTO.getDesc_suplemento());
			suplementoEncontrado.setMarca_suplemento(suplementoDTO.getMarca_suplemento());
			suplementoEncontrado.setTipo_suplemento(suplementoDTO.getTipo_suplemento());
			suplementoEncontrado.setPrecio_suplemento(suplementoDTO.getPrecio_suplemento());
			if(suplementoDTO.getRutaImagen_suplemento() != null)
				suplementoEncontrado.setRutaImagen_suplemento(suplementoDTO.getTipo_suplemento());
			
			// Actualizamos el suplemento
			suplementoRepositorio.save(suplementoEncontrado);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
