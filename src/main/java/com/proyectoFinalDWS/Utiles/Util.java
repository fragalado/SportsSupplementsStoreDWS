package com.proyectoFinalDWS.Utiles;

import java.util.ArrayList;
import java.util.List;

import com.proyectoFinalDWS.DAOs.Acceso;
import com.proyectoFinalDWS.DAOs.Suplemento;
import com.proyectoFinalDWS.DAOs.Usuario;
import com.proyectoFinalDWS.DTOs.SuplementoDTO;
import com.proyectoFinalDWS.DTOs.UsuarioDTO;

/**
 * Clase Util que contendrá métodos que se usarán muchas veces en toda la aplicación.
 * @author Fran Gallego
 * Fecha: 26/01/2024
 */
public class Util {
	
	/**
	 * Método que convierte un UsuarioDTO a DAO
	 * @return Devuelve el usuario convertido a DAO
	 */
	public static Usuario usuarioADao(UsuarioDTO usuarioDto) {
		try {
			// Convertimos el usuarioDto a usuarioDao
			Usuario usuarioDao = new Usuario();
			usuarioDao.setId_usuario(usuarioDto.getId_usuario());
			usuarioDao.setEmail_usuario(usuarioDto.getEmail_usuario());
			usuarioDao.setNombre_usuario(usuarioDto.getNombre_usuario());
			usuarioDao.setTlf_usuario(usuarioDto.getTlf_usuario());
			usuarioDao.setEstaActivado_usuario(usuarioDto.isEstaActivado_usuario());
			usuarioDao.setPsswd_usuario(usuarioDto.getPsswd_usuario());
			usuarioDao.setRutaImagen_usuario(usuarioDto.getRutaImagen_usuario());
			if(usuarioDto.getId_acceso() == 1)
				usuarioDao.setAcceso(new Acceso(1, "Usu", "Usuarios de la tienda"));
			else
				usuarioDao.setAcceso(new Acceso(2, "Admin", "Administrador de la tienda"));
			
			// Devolvemos el usuarioDao
			return usuarioDao;
		} catch (Exception e) {
			// Error al convertir
			System.out.println("[Error-Util-usuarioADao] Error al convertir el Usuario a DAO");
			return null;
		}
	}
	
	/**
	 * Método que convierte un Usuario (DAO) a DTO
	 * @return Devuelve el usuario convertido a DTO
	 */
	public static UsuarioDTO usuarioADto(Usuario usuarioDao) {
		try {
			// Convertimos el usuarioDao a usuarioDto
			UsuarioDTO usuarioDto = new UsuarioDTO();
			usuarioDto.setId_usuario(usuarioDao.getId_usuario());
			usuarioDto.setEmail_usuario(usuarioDao.getEmail_usuario());
			usuarioDto.setNombre_usuario(usuarioDao.getNombre_usuario());
			usuarioDto.setTlf_usuario(usuarioDao.getTlf_usuario());
			usuarioDto.setEstaActivado_usuario(usuarioDao.isEstaActivado_usuario());
			usuarioDto.setPsswd_usuario(usuarioDao.getPsswd_usuario());
			usuarioDto.setRutaImagen_usuario(usuarioDao.getRutaImagen_usuario());
			usuarioDto.setId_acceso(usuarioDao.getAcceso().getId_acceso());
			
			// Devolvemos el usuarioDto
			return usuarioDto;
		} catch (Exception e) {
			// Error al convertir
			System.out.println("[Error-Util-usuarioADto] Error al convertir el Usuario a DTO");
			return null;
		}
	}
	
	/**
	 * Método que convierte una lista de Usuarios (DAO) a DTO
	 * @param listaUsuariosDao Lista de tipo Usuario (DAO)
	 * @return Devuelve la lista de usuarios convertida a DTO
	 */
	public static List<UsuarioDTO> listaUsuariosADto(List<Usuario> listaUsuariosDao){
		try {
			// Lista donde guardaremos los usuarios convertidos
			List<UsuarioDTO> listaUsuariosDto = new ArrayList<UsuarioDTO>();
			
			// Convertimos
			for (Usuario aux : listaUsuariosDao) {
				UsuarioDTO usuario = new UsuarioDTO();
				usuario.setId_usuario(aux.getId_usuario());
				usuario.setNombre_usuario(aux.getNombre_usuario());
				usuario.setEmail_usuario(aux.getEmail_usuario());
				usuario.setTlf_usuario(aux.getTlf_usuario());
				usuario.setId_acceso(aux.getAcceso().getId_acceso());
				usuario.setPsswd_usuario(aux.getPsswd_usuario());
				usuario.setRutaImagen_usuario(aux.getRutaImagen_usuario());
				listaUsuariosDto.add(usuario);
			}
			
			// Devolvemos la nueva lista
			return listaUsuariosDto;
		} catch (Exception e) {
			// Error al convertir
			System.out.println("[Error-Util-listaUsuariosADto] Error al convertir el Usuario (DAO) a DTO");
			return null;
		}
	}
	
	/**
	 * Método que convierte una lista de Suplementos (DAO) a DTO
	 * @param listaSuplementosDao Lista de tipo Suplemento (DAO)
	 * @return Devuelve la lista de suplementos convertida a DTO
	 */
	public static List<SuplementoDTO> listaSuplementosADto(List<Suplemento> listaSuplementosDao){
		try {
			// Lista donde guardaremos los suplementos convertidos
			List<SuplementoDTO> listaSuplementosDto = new ArrayList<SuplementoDTO>();
			
			// Convertimos
			for (Suplemento aux : listaSuplementosDao) {
				SuplementoDTO suplemento = new SuplementoDTO();
				suplemento.setId_suplemento(aux.getId_suplemento());
				suplemento.setNombre_suplemento(aux.getNombre_suplemento());
				suplemento.setDesc_suplemento(aux.getDesc_suplemento());
				suplemento.setMarca_suplemento(aux.getMarca_suplemento());
				suplemento.setTipo_suplemento(aux.getTipo_suplemento());
				suplemento.setPrecio_suplemento(aux.getPrecio_suplemento());
				suplemento.setRutaImagen_suplemento(aux.getRutaImagen_suplemento());
				listaSuplementosDto.add(suplemento);
			}
			
			// Devolvemos la nueva lista
			return listaSuplementosDto;
		} catch (Exception e) {
			// Error al convertir
			System.out.println("[Error-Util-listaSuplementosADto] Error al convertir el Suplemento (DAO) a DTO");
			return null;
		}
	}
	
	/**
	 * Método que convierte un objeto de tipo Suplemento (DAO) a SuplementoDTO
	 * @return Devuelve el suplemento convertido a DTO
	 */
	public static SuplementoDTO suplementoADto(Suplemento suplementoDAO) {
		try {
			// Convertimos el suplementoDAO a suplementoDto
			SuplementoDTO suplementoDTO = new SuplementoDTO();
			suplementoDTO.setId_suplemento(suplementoDAO.getId_suplemento());
			suplementoDTO.setNombre_suplemento(suplementoDAO.getNombre_suplemento());
			suplementoDTO.setDesc_suplemento(suplementoDAO.getDesc_suplemento());
			suplementoDTO.setMarca_suplemento(suplementoDAO.getMarca_suplemento());
			suplementoDTO.setTipo_suplemento(suplementoDAO.getTipo_suplemento());
			suplementoDTO.setPrecio_suplemento(suplementoDAO.getPrecio_suplemento());
			suplementoDTO.setRutaImagen_suplemento(suplementoDAO.getRutaImagen_suplemento());
			
			// Devolvemos el suplementoDTO
			return suplementoDTO;
		} catch (Exception e) {
			// Error al convertir
			System.out.println("[Error-Util-suplementoADto] Error al convertir el Suplemento a DTO");
			return null;
		}
	}
	
	/**
	 * Método que convierte un objeto de tipo SuplementoDTO a DAO
	 * @return Devuelve el suplemento convertido a DAO
	 */
	public static Suplemento suplementoADao(SuplementoDTO suplementoDTO) {
		try {
			// Convertimos el suplementoDTO a suplementoDAO
			Suplemento suplementoDAO = new Suplemento();
			suplementoDAO.setId_suplemento(suplementoDTO.getId_suplemento());
			suplementoDAO.setNombre_suplemento(suplementoDTO.getNombre_suplemento());
			suplementoDAO.setDesc_suplemento(suplementoDTO.getDesc_suplemento());
			suplementoDAO.setMarca_suplemento(suplementoDTO.getMarca_suplemento());
			suplementoDAO.setTipo_suplemento(suplementoDTO.getTipo_suplemento());
			suplementoDAO.setPrecio_suplemento(suplementoDTO.getPrecio_suplemento());
			suplementoDAO.setRutaImagen_suplemento(suplementoDTO.getRutaImagen_suplemento());
			
			// Devolvemos el suplementoDAO
			return suplementoDAO;
		} catch (Exception e) {
			// Error al convertir
			System.out.println("[Error-Util-suplementoADao] Error al convertir el Suplemento a DAO");
			return null;
		}
	}
}
