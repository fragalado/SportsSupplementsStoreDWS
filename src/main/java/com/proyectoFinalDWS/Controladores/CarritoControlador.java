package com.proyectoFinalDWS.Controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectoFinalDWS.DTOs.CarritoDTO;
import com.proyectoFinalDWS.Servicios.CarritoImplementacion;

/**
 * Clase controlador para la vista Carrito
 * @author Fran Gallego
 * Fecha: 08/02/2024
 */
@Controller
@RequestMapping("/carrito")
public class CarritoControlador {

	@Autowired
	private CarritoImplementacion carritoImplementacion;
	
	@GetMapping()
	public String vistaCarrito(Model model, Authentication authentication) {
		
		// Obtenemos el carrito del usuario
		List<CarritoDTO> listaCarritoDTO = carritoImplementacion.obtieneCarritoUsuario(authentication.getName());
		
		// Añadimos al modelo una lista de carrito DTO
		if(listaCarritoDTO == null)
			model.addAttribute("listaCarritoDTO", new ArrayList<CarritoDTO>());
		else {
			model.addAttribute("listaCarritoDTO", listaCarritoDTO);
			model.addAttribute("precioTotal", carritoImplementacion.obtienePrecioTotalCarrito(authentication.getName()));
		}
			
		
		// Devolvemos la vista
		return "carrito";
	}
	
	@GetMapping("/borra-suplemento/{id_carrito}")
	public String borraSuplementoCarrito(@PathVariable("id_carrito") long id_carrito) {
		try {
			// Borramos el carrito
			boolean ok = carritoImplementacion.borraCarrito(id_carrito);
			
			if(ok)
				return "redirect:/carrito?success";
			else
				return "redirect:/carrito?error";
		} catch (Exception e) {
			return "redirect:/carrito?error";
		}
	}
}
