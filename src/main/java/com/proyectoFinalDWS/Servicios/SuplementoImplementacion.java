package com.proyectoFinalDWS.Servicios;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.proyectoFinalDWS.DAOs.Suplemento;
import com.proyectoFinalDWS.DTOs.SuplementoDTO;
import com.proyectoFinalDWS.Repositorios.SuplementoRepository;
import com.proyectoFinalDWS.Utiles.Util;

import jakarta.transaction.Transactional;

/**
 * Implementación de la interfaz Suplemento
 * @author Fran Gallego
 * Fecha: 06/02/2024
 */
@Service
@Transactional
public class SuplementoImplementacion implements SuplementoInterfaz {

	@Autowired
	private SuplementoRepository suplementoRepositorio;
	
	@Override
	public List<SuplementoDTO> obtieneTodosLosSuplementos() {
		try {
			// Log
			Util.logInfo("SuplementoImplementacion", "obtieneTodosLosSuplementos", "Ha entrado en obtieneTodosLosSuplementos");
			
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
			Util.logError("SuplementoImplementacion", "obtieneTodosLosSuplementos", "Se ha producido un error");
			return null;
		}
	}
	
	@Override
	public boolean borraSuplementoPorId(long id_suplemento) {
		try {
			// Log
			Util.logInfo("SuplementoImplementacion", "borraSuplementoPorId", "Ha entrado en borraSuplementoPorId");
			
			// Eliminamos el suplemento por el id
			suplementoRepositorio.deleteById(id_suplemento);
			
			// Ahora para comprobar si se ha eliminado vamos a buscar el suplemento por el id
			Optional<Suplemento> suplementoEncontrado = suplementoRepositorio.findById(id_suplemento);
			
			if(!suplementoEncontrado.isPresent())
				return true; // Devolvemos true si no existe
			
			return false; // En caso de que se haya encontrado un suplemento con el id
			
			/* OTRA OPCION
			 * return suplementoRepositorio.findById(id_suplemento).isEmpty();
			 */
		} catch (IllegalArgumentException e) {
			Util.logError("SuplementoImplementacion", "borraSuplementoPorId", "El id del suplemento es null");
			return false;
		}
	}
	
	@Override
	public SuplementoDTO obtieneSuplementoPorId(long id_suplemento) {
		try {
			// Log
			Util.logInfo("SuplementoImplementacion", "obtieneSuplementoPorId", "Ha entrado en obtieneSuplementoPorId");
			
			// Obtenemos el suplemento
			Optional<Suplemento> suplementoEncontrado = suplementoRepositorio.findById(id_suplemento);
			
			// Comprobamos si no se ha encontrado el suplemento
			if(!suplementoEncontrado.isPresent())
				return null;
			
			// Si se ha encontrado vamos a convertir el suplemento de DAO a DTO
			SuplementoDTO suplementoDTO = Util.suplementoADto(suplementoEncontrado.get());
			
			// Devolvemos el suplemento convertido a DTO
			return suplementoDTO;
		} catch (IllegalArgumentException e) {
			Util.logError("SuplementoImplementacion", "obtieneSuplementoPorId", "El id del suplemento es null");
			return null;
		} catch (NoSuchElementException e) {
			Util.logError("SuplementoImplementacion", "obtieneSuplementoPorId", "El objeto no existe o no tiene valor");
			return null;
		}
	}
	
	@Override
	public boolean agregaSuplemento(SuplementoDTO suplementoDTO) {
		try {
			// Log
			Util.logInfo("SuplementoImplementacion", "agregaSuplemento", "Ha entrado en agregaSuplemento");
			
			// Convertimos el suplemento de DTO a DAO
			Suplemento suplementoDAO = Util.suplementoADao(suplementoDTO);
			
			// Agregamos el suplemento a la base de datos
			Suplemento suplementoDevuelto = suplementoRepositorio.save(suplementoDAO);
			
			// Comprobamos si se ha añadido
			if(suplementoDevuelto != null)
				return true; // Se ha añadido correctamente
			
			return false; // Si se ha producido algún error
			
			/* OTRA OPCION
			 * return suplementoRepositorio.save(suplementoDAO) != null
			 */
		} catch (IllegalArgumentException e) {
			Util.logError("SuplementoImplementacion", "agregaSuplemento", "El objeto es nulo");
			return false;
		} catch (OptimisticLockingFailureException e) {
			Util.logError("SuplementoImplementacion", "agregaSuplemento", "Concurrencia optimista");
			return false;
		}
	}
	
	@Override
	public boolean actualizaSuplemento(SuplementoDTO suplementoDTO) {
		try {
			// Log
			Util.logInfo("SuplementoImplementacion", "actualizaSuplemento", "Ha entrado en actualizaSuplemento");
			
			// Con el id del suplemento pasado obtenemos el suplemento de la base de datos
			Suplemento suplementoEncontrado = suplementoRepositorio.findById(suplementoDTO.getId_suplemento()).get();
			
			// Actualizamos algunos datos del suplementoEncontrado con el suplementoDTO
			suplementoEncontrado.setNombre_suplemento(suplementoDTO.getNombre_suplemento());
			suplementoEncontrado.setDesc_suplemento(suplementoDTO.getDesc_suplemento());
			suplementoEncontrado.setMarca_suplemento(suplementoDTO.getMarca_suplemento());
			suplementoEncontrado.setTipo_suplemento(suplementoDTO.getTipo_suplemento());
			suplementoEncontrado.setPrecio_suplemento(suplementoDTO.getPrecio_suplemento());
			if(suplementoDTO.getImagen_suplemento() != null)
				suplementoEncontrado.setImagen_suplemento(Util.convertirAByteArray(suplementoDTO.getImagen_suplemento()));
			
			// Actualizamos el suplemento
			suplementoRepositorio.save(suplementoEncontrado);
			
			return true;
		} catch (IllegalArgumentException e) {
			Util.logError("SuplementoImplementacion", "actualizaSuplemento", "El objeto es nulo o no existe");
			return false;
		} catch (OptimisticLockingFailureException e) {
			Util.logError("SuplementoImplementacion", "actualizaSuplemento", "Concurrencia optimista");
			return false;
		}
	}
}
