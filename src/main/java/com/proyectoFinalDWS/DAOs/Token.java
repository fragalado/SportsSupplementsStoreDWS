package com.proyectoFinalDWS.DAOs;

import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/*
 * Entidad Token que hace referencia a la tabla Tokens de la base de datos
 * @autor Fran Gallego
 * Fecha: 25/01/2024
 */
@Entity
@Table(name = "Tokens", schema = "gbp_operacional2")
public class Token {
	
	// Atributos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_token;
	
	@Column(name = "cod_token", nullable = false)
	private String cod_token;
	
	@Column(name = "fch_fin_token", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fch_fin_token;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	// Constructores
	
	public Token(String cod_token, Calendar fch_fin_token) {
		super();
		this.cod_token = cod_token;
		this.fch_fin_token = fch_fin_token;
	}
	
	public Token() {
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
