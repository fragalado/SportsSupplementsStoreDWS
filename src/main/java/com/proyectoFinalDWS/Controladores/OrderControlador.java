package com.proyectoFinalDWS.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.proyectoFinalDWS.Paypal.PaypalImplementacion;
import com.proyectoFinalDWS.Servicios.OrdenImplementacion;
import com.proyectoFinalDWS.Utiles.Util;

/**
 * Controlador para la operación de compra y compra con paypal
 * @author Fran Gallego
 * Fecha: 08/02/2024
 */
@Controller
@RequestMapping("/comprar")
public class OrderControlador {

	@Autowired
	private PaypalImplementacion paypalImplementacion;
	
	@Autowired
	private OrdenImplementacion ordenImplementacion;
	
	/**
	 * Método que realiza el pago por paypal
	 * 
	 * @param total Total del pago
	 * @return Devuelve el nombre de la vista
	 */
	@PostMapping("/paypal")
	public String payment(@RequestParam Double total) {
		try {
            Payment payment = paypalImplementacion.createPayment(total.toString(), "USD", "http://localhost:8080/comprar/success", "http://localhost:8080/comprar/cancel");
            System.out.println(payment.getLinks().get(1).getHref());
            return "redirect:" + payment.getLinks().get(1).getHref(); // Redirige al usuario a la URL de aprobación de PayPal
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return "redirect:/carrito?cancel";
        }
	}
	
	/**
	 * Método que realiza el pago beta
	 * 
	 * @param authentication Objeto Authentication que contiene los datos de la sesión
	 * @return Devuelve el nombre de la vista
	 */
	@PostMapping("/normal")
	public String pago(Authentication authentication) {
		try {
			// Log
			Util.logInfo("OrderControlador", "pago", "Ha entrado en normal");
            // Realizamos la compra del carrito del usuario
			boolean ok = ordenImplementacion.comprarCarritoUsuario(authentication.getName());
            
			if(ok)
            	return "redirect:/carrito?compraSuccess";
			else
				return "redirect:/carrito?cancel";
        } catch (Exception e) {
        	// Log
			Util.logError("OrderControlador", "pago", "Se ha producido un error.");
            return "redirect:/carrito?cancel";
        }
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/comprar/cancel"
	 * @return Devuelve un redireccionamiento a /carrito?cancel
	 */
	@GetMapping("/cancel")
	public String cancelPay() {
		return "redirect:/carrito?cancel";
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/comprar/success"
	 * @return Devuelve un redireccionamiento a /carrito?success
	 */
	@GetMapping("/success")
	public String successPay() {
		return "redirect:/carrito?success";
	}
}
