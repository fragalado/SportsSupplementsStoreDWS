package com.proyectoFinalDWS.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoFinalDWS.DAOs.Carrito;
import com.proyectoFinalDWS.DTOs.CarritoDTO;
import com.proyectoFinalDWS.DTOs.SuplementoDTO;
import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Repositorios.CarritoRepository;
import com.proyectoFinalDWS.Utiles.Util;

import jakarta.transaction.Transactional;

/**
 * Implementación de la interfaz Carrito
 * @author Fran Gallego
 * Fecha: 08/02/2024
 */
@Service
@Transactional
public class CarritoImplementacion implements CarritoInterfaz {

	@Autowired
	private UsuarioImplementacion usuarioImplementacion;
	
	@Autowired
	private SuplementoImplementacion suplementoImplementacion;
	
	@Autowired
	private CarritoRepository carritoRepositorio;
	
	@Override
	public List<CarritoDTO> obtieneCarritoUsuario(String emailUsuario) {
		try {
			// Obtenemos el usuario por el email
			UsuarioDTO usuarioDTO = usuarioImplementacion.obtieneUsuarioPorEmail(emailUsuario);
			
			// Ahora que tenemos el usuario vamos a obtener el carrito
			List<Carrito> listaCarritoDAO = carritoRepositorio.findAllCarritoNoComprado(Util.usuarioADao(usuarioDTO));
			
			// Convertimos la lista a DTO
			List<CarritoDTO> listaCarritoDTO = Util.listaCarritoADto(listaCarritoDAO);
			
			// Devolvemos la lista
			return listaCarritoDTO;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean borraCarrito(long id_carrito) {
		try {
			// Borramos el carrito
			carritoRepositorio.deleteById(id_carrito);
			
			// Ahora buscamos el carrito para comprobar si se ha borrado
			if(!carritoRepositorio.findById(id_carrito).isEmpty())
				return false; // Se ha encontrado, luego no se ha borrado
			
			return true; // No se ha encontrado el carrito, luego se ha borrado
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean agregaSuplemento(long id_suplemento, String email_usuario) {
		try {
			// Obtenemos el usuario por el email
			UsuarioDTO usuarioDTO = usuarioImplementacion.obtieneUsuarioPorEmail(email_usuario);
			
			// Obtenemos el suplemento por el id
			SuplementoDTO suplementoDTO = suplementoImplementacion.obtieneSuplementoPorId(id_suplemento);
			
			// Comprobamos si ya existe un carrito con el suplemento
			Carrito carrito = carritoRepositorio.findCarritoBySuplemento(Util.usuarioADao(usuarioDTO), Util.suplementoADao(suplementoDTO));
			
			if(carrito == null) {
				// Creamos el carrito y lo agregamos
				Carrito carritoDAO = new Carrito();
				carritoDAO.setUsuario(Util.usuarioADao(usuarioDTO));
				carritoDAO.setCantidad(1);
				carritoDAO.setSuplemento(Util.suplementoADao(suplementoDTO));
				
				// Agregamos el carrito
				carritoRepositorio.save(carritoDAO);
			} else {
				// Le aumentamos la cantidad en 1
				carrito.setCantidad(carrito.getCantidad() + 1);
				
				// Actualizamos el carrito
				carritoRepositorio.save(carrito);
			}
			
			return true; // Devolvemos true
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public float obtienePrecioTotalCarrito(List<CarritoDTO> listaCarrito) {
		try {
			// Obtenemos el precio total
			float total = 0;
			for (CarritoDTO aux : listaCarrito)
				total += aux.getCantidad() * aux.getSuplementoDTO().getPrecio_suplemento();
			
			// Devolvemos el total
			return total;
		} catch (Exception e) {
			return 0f;
		}
	}

}
