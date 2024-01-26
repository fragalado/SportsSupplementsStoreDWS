package com.proyectoFinalDWS.DAOs;

import java.util.Calendar;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/*
 * Entidad Orden que hace referencia a la tabla Ordenes de la base de datos
 * @autor Fran Gallego
 * Fecha: 25/01/2024
 */
@Entity
@Table(name = "Ordenes", schema = "gbp_operacional2")
public class Orden {
	
	// Atributos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_orden;
	
	@Column(name = "precio_orden", nullable = false)
	private float precio_orden;
	
	@Column(name = "fch_orden", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fch_orden;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "orden")
	private List<RelOrdenCarrito> listaRelacion;
	
	// Constructores
	
	public Orden(float precio_orden, Calendar fch_orden) {
		super();
		this.precio_orden = precio_orden;
		this.fch_orden = fch_orden;
	}
	
	public Orden() {
		super();
	}
	
	// Getter y Setter
	
	public long getId_orden() {
		return id_orden;
	}

	public void setId_orden(long id_orden) {
		this.id_orden = id_orden;
	}

	public float getPrecio_orden() {
		return precio_orden;
	}

	public void setPrecio_orden(float precio_orden) {
		this.precio_orden = precio_orden;
	}

	public Calendar getFch_orden() {
		return fch_orden;
	}

	public void setFch_orden(Calendar fch_orden) {
		this.fch_orden = fch_orden;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
