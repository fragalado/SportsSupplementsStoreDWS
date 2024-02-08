package com.proyectoFinalDWS.DAOs;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/*
 * Entidad Acceso que representa a la tabla Accesos en la base de datos
 * @autor Fran Gallego
 * Fecha: 25/01/2023
 */
@Entity
@Table(name = "Accesos", schema = "gtp_usuarios")
public class Acceso {
	
	// Atributos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_acceso;
	
	@Column(name = "cod_acceso", nullable = false)
	private String cod_acceso;
	
	@Column(name = "desc_acceso", nullable = false)
	private String desc_acceso;
	
	@OneToMany(mappedBy = "acceso")
	List<Usuario> listaUsuario;
	
	// Constructores
	
	public Acceso(long id_acceso, String cod_acceso, String desc_acceso) {
		super();
		this.id_acceso = id_acceso;
		this.cod_acceso = cod_acceso;
		this.desc_acceso = desc_acceso;
	}
	
	public Acceso(String cod_acceso, String desc_acceso) {
		super();
		this.cod_acceso = cod_acceso;
		this.desc_acceso = desc_acceso;
	}
	
	public Acceso() {
		super();
	}
	
	// Getter y Setter
	
	public long getId_acceso() {
		return id_acceso;
	}

	public void setId_acceso(long id_acceso) {
		this.id_acceso = id_acceso;
	}

	public String getCod_acceso() {
		return cod_acceso;
	}

	public void setCod_acceso(String cod_acceso) {
		this.cod_acceso = cod_acceso;
	}

	public String getDesc_acceso() {
		return desc_acceso;
	}

	public void setDesc_acceso(String desc_acceso) {
		this.desc_acceso = desc_acceso;
	}
	
}
