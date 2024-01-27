package com.proyectoFinalDWS.DTOs;

import java.util.Calendar;

/**
 * Entidad TokenDTO
 * @author Fran Gallego
 * Fecha: 27/01/2024
 */
public class TokenDTO {

	// Atributos
	
	private long id_token;
	private String cod_token;
	private Calendar fch_fin_token;
	private long id_usuario;
	
	// Constructores
	
	public TokenDTO(String cod_token, Calendar fch_fin_token, long id_usuario) {
		super();
		this.cod_token = cod_token;
		this.fch_fin_token = fch_fin_token;
		this.id_usuario = id_usuario;
	}
	
	public TokenDTO() {
		super();
	}
	
	// Getter y Setter
	
	public long getId_token() {
		return id_token;
	}

	public void setId_token(long id_token) {
		this.id_token = id_token;
	}

	public String getCod_token() {
		return cod_token;
	}

	public void setCod_token(String cod_token) {
		this.cod_token = cod_token;
	}

	public Calendar getFch_fin_token() {
		return fch_fin_token;
	}

	public void setFch_fin_token(Calendar fch_fin_token) {
		this.fch_fin_token = fch_fin_token;
	}

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}
	
}
