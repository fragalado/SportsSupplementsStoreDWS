package com.proyectoFinalDWS.DAOs;

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

/*
 * Entidad Carrito que hace referencia a la tabla Carritos de la base de datos
 * @autor Fran Gallego
 * Fecha: 25/01/2024
 */
@Entity
@Table(name = "Carritos", schema = "gbp_operacional2")
public class Carrito {

	// Atributos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_carrito;
	
	@ManyToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_suplemento")
	private Suplemento suplemento;
	
	@Column(name = "cantidad", nullable = false)
	private int cantidad;
	
	@OneToMany(mappedBy = "carrito")
	private List<RelOrdenCarrito> listaRelacion;
	
	// Constructores

	public Carrito(int cantidad) {
		super();
		this.cantidad = cantidad;
	}
	
	public Carrito() {
		super();
	}
	
	// Getter y Setter
	
	public long getId_carrito() {
		return id_carrito;
	}

	public void setId_carrito(long id_carrito) {
		this.id_carrito = id_carrito;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Suplemento getSuplemento() {
		return suplemento;
	}

	public void setSuplemento(Suplemento suplemento) {
		this.suplemento = suplemento;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}
