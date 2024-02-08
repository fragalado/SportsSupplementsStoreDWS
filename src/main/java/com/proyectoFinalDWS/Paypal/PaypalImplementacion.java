package com.proyectoFinalDWS.Paypal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PaypalImplementacion {

	@Autowired
	private APIContext apiContext;
	
	public Payment createPayment(String total, String currency, String returnUrl, String cancelUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(total);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(getRedirectUrls(returnUrl, cancelUrl));

        return payment.create(apiContext);
    }

    private RedirectUrls getRedirectUrls(String returnUrl, String cancelUrl) {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(returnUrl);
        redirectUrls.setCancelUrl(cancelUrl);
        return redirectUrls;
    }
}
