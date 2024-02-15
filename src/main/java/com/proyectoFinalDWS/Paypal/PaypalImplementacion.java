package com.proyectoFinalDWS.Paypal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

/**
 * Implementación Paypal
 * @author Fran Gallego
 */
@Service
public class PaypalImplementacion {

	@Autowired
	private APIContext apiContext;
	
	/**
	 * Método que crea un objeto Payment para iniciar un proceso de pago con Paypal
	 * 
	 * @param total Total de la operación
	 * @param currency Moneda en la que se realiza la operación
	 * @param returnUrl  URL a la que se redirige después de un pago exitoso
	 * @param cancelUrl URL a la que se redirige si el usuario cancela el pago
	 * @return Objeto Payment que representa la transacción
	 * @throws PayPalRESTException
	 */
	public Payment createPayment(String total, String currency, String returnUrl, String cancelUrl) throws PayPalRESTException {
        // Configuramos el total y la moneda
		Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(total);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        // Configuramos el pagador
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal"); // Método de pago

        // Configuramos el objeto Payment
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(getRedirectUrls(returnUrl, cancelUrl));

        // Creación del pago utilizando el APIContext inyectado
        return payment.create(apiContext);
    }

	/**
	 * Método que crea y devuelve un objeto RedirectUrls con las URLs de redirección
	 * @param returnUrl URL a la que se redirige después de un pago exitoso.
	 * @param cancelUrl URL a la que se redirige si el usuario cancela el pago.
	 * @return Objeto RedirectUrls configurado con las URLs proporcionadas
	 */
    private RedirectUrls getRedirectUrls(String returnUrl, String cancelUrl) {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(returnUrl);
        redirectUrls.setCancelUrl(cancelUrl);
        return redirectUrls;
    }
}
