package com.proyectoFinalDWS.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.proyectoFinalDWS.Paypal.PaypalImplementacion;

@Controller
@RequestMapping("/comprar")
public class OrderControlador {

	@Autowired
	private PaypalImplementacion paypalImplementacion;
	
	@GetMapping()
	public String vistaComprar() {
		return "comprar";
	}
	
	@PostMapping()
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
	
	@GetMapping("/cancel")
	public String cancelPay() {
		return "redirect:/carrito?cancel";
	}
	
	@GetMapping("/success")
	public String successPay() {
		return "redirect:/carrito?success";
	}
}
