package com.proyectoFinalDWS.Servicios;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.proyectoFinalDWS.DAOs.Carrito;
import com.proyectoFinalDWS.DAOs.Orden;
import com.proyectoFinalDWS.DAOs.RelOrdenCarrito;
import com.proyectoFinalDWS.DAOs.Usuario;
import com.proyectoFinalDWS.DTOs.CarritoDTO;
import com.proyectoFinalDWS.Repositorios.CarritoRepository;
import com.proyectoFinalDWS.Repositorios.OrdenRepository;
import com.proyectoFinalDWS.Repositorios.RelOrdenCarritoRepository;
import com.proyectoFinalDWS.Utiles.Util;

/**
 * Implementación de la interfaz Orden
 * @author Fran Gallego
 * Fecha: 15/02/2024
 */
@Service
public class OrdenImplementacion implements OrdenInterfaz {

	@Autowired
	private CarritoImplementacion carritoImplementacion;
	
	@Autowired
	private UsuarioImplementacion usuarioImplementacion;
	
	@Autowired
	private OrdenRepository ordenRepositorio;
	
	@Autowired
	private RelOrdenCarritoRepository ordenCarritoRepositorio;
	
	@Autowired
	private CarritoRepository carritoRepositorio;
	
	@Override
	public boolean comprarCarritoUsuario(String email_usuario) {
		try {
			// Log
			Util.logInfo("OrdenImplementacion", "comprarCarritoUsuario", "Ha entrado en comprarCarritoUsuario");
			
			// Obtenemos el usuario
			Usuario usuarioDAO = Util.usuarioADao(usuarioImplementacion.obtieneUsuarioPorEmail(email_usuario));
			
			// Obtenemos los carritos del usuario
			List<CarritoDTO> listaCarritos = carritoImplementacion.obtieneCarritoUsuario(email_usuario);
			
			// Obtenemos el precio total del carrito
			float precioTotal = carritoImplementacion.obtienePrecioTotalCarrito(listaCarritos);
			
			// Creamos un objeto Orden
			Orden orden = new Orden();
			orden.setPrecio_orden(precioTotal);
			orden.setUsuario(usuarioDAO);
			orden.setFch_orden(Calendar.getInstance());
			
			// Guardamos la orden
			Orden ordenDevuelta = ordenRepositorio.save(orden);
			
			// Ahora vamos a tener que guardar en la tabla intermedia
			for (CarritoDTO aux : listaCarritos) {
				// Convertimos el carrito a DAO
				Carrito carritoDAO = new Carrito();
				carritoDAO.setId_carrito(aux.getId_carrito());
				carritoDAO.setEstaComprado_carrito(aux.isEstaComprado_carrito());
				carritoDAO.setCantidad(aux.getCantidad());
				carritoDAO.setSuplemento(Util.suplementoADao(aux.getSuplementoDTO()));
				// Obtenemos el usuario por el id
				carritoDAO.setUsuario(Util.usuarioADao(usuarioImplementacion.obtieneUsuarioPorId(aux.getId_usuario())));
				
				// Ahora que tenemos el carrito en DAO creamos el objeto RelOrdenCarrito
				RelOrdenCarrito ordenCarrito = new RelOrdenCarrito();
				ordenCarrito.setCarrito(carritoDAO);
				ordenCarrito.setOrden(ordenDevuelta);
				
				// Hacemos el insert en la tabla intermedia
				ordenCarritoRepositorio.save(ordenCarrito);
				
				// Ahora hacemos el update del carrito
				carritoDAO.setEstaComprado_carrito(true);
				carritoRepositorio.save(carritoDAO);		
			}
			
			return true;
		} catch (IllegalArgumentException e) {
			Util.logError("OrdenImplementacion", "comprarCarritoUsuario", "El objeto es nulo");
			return false;
		} catch (OptimisticLockingFailureException e) {
			Util.logError("OrdenImplementacion", "comprarCarritoUsuario", "Concurrencia optimista");
			return false;
		}
	}

}
