package com.proyectoFinalDWS.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoFinalDWS.DAOs.Token;
import com.proyectoFinalDWS.Repositorios.TokenRepository;

import jakarta.transaction.Transactional;

/**
 * Implementación de la interfaz Token
 * @author Fran Gallego
 * Fecha: 06/02/2024
 */
@Service
@Transactional
public class TokenImplementacion implements TokenInterfaz {

	@Autowired
	private TokenRepository tokenRepositorio;
	
	@Override
	public boolean guardaToken(Token token) {
		try {
			Token tokenDevuelto = tokenRepositorio.save(token);
			
			if(tokenDevuelto != null)
				return true;
			
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	@Override
	public Token obtieneToken(String token) {
		try {
			Token tokenEncontrado = tokenRepositorio.findByCodToken(token);
			
			if(tokenEncontrado != null) {
				return tokenEncontrado;
			}
			
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	
}
