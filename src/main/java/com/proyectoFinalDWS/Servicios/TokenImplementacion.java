package com.proyectoFinalDWS.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.proyectoFinalDWS.DAOs.Token;
import com.proyectoFinalDWS.Repositorios.TokenRepository;
import com.proyectoFinalDWS.Utiles.Util;

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
			// Log
			Util.logInfo("TokenImplementacion", "guardaToken", "Ha entrado en guardaToken");
			Token tokenDevuelto = tokenRepositorio.save(token);
			
			if(tokenDevuelto != null)
				return true;
			
			return false;
		} catch (IllegalArgumentException e) {
			Util.logError("TokenImplementacion", "guardaToken", "La entidad es nula");
			return false;
		} catch (OptimisticLockingFailureException e) {
			Util.logError("TokenImplementacion", "guardaToken", "Concurrencia optimista");
			return false;
		}
	}

	@Override
	public Token obtieneToken(String token) {
		try {
			Util.logInfo("TokenImplementacion", "obtieneToken", "Ha entrado en obtieneToken");
			Token tokenEncontrado = tokenRepositorio.findByCodToken(token);
			
			if(tokenEncontrado != null) {
				return tokenEncontrado;
			}
			
			return null;
		} catch (Exception e) {
			Util.logError("TokenImplementacion", "obtieneToken", "Se ha producido un error");
			return null;
		}
	}

	
}
