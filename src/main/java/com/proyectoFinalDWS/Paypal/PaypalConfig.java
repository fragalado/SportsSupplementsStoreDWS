package com.proyectoFinalDWS.Paypal;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

/**
 * Configuración para la integración de Paypal.
 * @author Fran Gallego
 */
@Configuration
public class PaypalConfig {

	// Valores de configuración leídos desde el application.properties 
	@Value("${paypal.client.id}")
	private String clientId;
	@Value("${paypal.client.secret}")
	private String clientSecret;
	@Value("${paypal.mode}")
	private String mode;
	
	/**
	 * Configuración de las credenciales del SDK de Paypal
	 * @return
	 */
	@Bean
	public Map<String, String> paypalSdkConfig(){
		Map<String, String> configMap = new HashMap<String, String>();
		configMap.put("mode", mode);
		
		return configMap;
	}
	
	/**
	 * Configuración de las credenciales de autorización OAuth para Paypal
	 * @return Instancia de OAuthTokenCredential con las credenciales leídas desde el application.properties
	 */
	@Bean
	public OAuthTokenCredential oAuthTokenCredential() {
		return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
	}
	
	/**
	 * Configuración del contexto de la API de Paypal
	 * @return Instancia de APIContext configurada con el token de acceso y la configuracion del SDK
	 * @throws PayPalRESTException
	 */
	@Bean
	public APIContext apiContext() throws PayPalRESTException {
		APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
		context.setConfigurationMap(paypalSdkConfig());
		return context;
	}
}
