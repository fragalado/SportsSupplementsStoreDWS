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
				return true; // Devolvemos true si es igual a null
			
			return false; // En caso de que se haya encontrado un usuario con el id
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

}
