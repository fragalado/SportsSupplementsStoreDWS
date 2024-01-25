package com.proyectoFinalDWS.Seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // Indica que esta clase es de configuración
@EnableWebSecurity
public class SeguridadConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        		// Cross-Site Request Forgery, método de seguridad que utiliza por defecto spring security
        		.csrf(csrf -> csrf.disable())
        		.authorizeHttpRequests(authRequest -> {
        			//authRequest.requestMatchers("/", "/acceso/**").permitAll();
        			//authRequest.anyRequest().authenticated();
        			authRequest.anyRequest().permitAll();
        		}
        		)
        		.formLogin(withDefaults())
        		.build();
    }
}