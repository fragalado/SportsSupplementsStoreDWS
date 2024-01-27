package com.proyectoFinalDWS.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration // Indica que esta clase es de configuración
@EnableMethodSecurity
public class SeguridadConfig {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDetailsService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	
//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//	    return authConfig.getAuthenticationManager();
//	}
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
    	http
			// Cross-Site Request Forgery, método de seguridad que utiliza por defecto spring security
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authRequest -> {
				// Permite acceso a estas url
				authRequest.requestMatchers("/", "/acceso/**", "/css/**", "/js/**", "/img/**").permitAll();
				// Autenticación para cualquier otra solicitud
				authRequest.anyRequest().authenticated();
				}
			)
			.formLogin(login -> 
					login
						.loginPage("/acceso/login") // Establece la página de inicio de sesión personalizada
						.defaultSuccessUrl("/home", true) // Establece la url de redirección después de un inicio exitoso
						.loginProcessingUrl("/acceso/login") // Establece la url de procesamiento del formulario de login
						.failureHandler(customAuthenticationFailureHandler())
			)
			// Configura el proceso de cierre de sesión
			.logout(logout ->
					logout
						.logoutUrl("/acceso/logout") // Establece la url de cierre de sesión personalizada
						.logoutSuccessUrl("/acceso/login?logout") // Establece la url de redirección despues de un logout exitoso
			);
    	
//        // Configura un proveedor de autenticación personalizado.
//        http.authenticationProvider(authenticationProvider());
    	
    	return http.build();
    }
}