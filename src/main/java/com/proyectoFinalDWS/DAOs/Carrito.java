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
@Table(name = "Carritos", schema = "gtp_hechos")
public class Carrito {

	// Atributos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_carrito;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_suplemento")
	private Suplemento suplemento;
	
	@Column(name = "cantidad", nullable = false)
	private int cantidad;
	
	@Column(name = "estaComprado_carrito")
	private boolean estaComprado_carrito;
	
	@OneToMany(mappedBy = "carrito")
	private List<RelOrdenCarrito> listaRelacion;
	
	// Constructores

	public Carrito(int cantidad) {
		super();
		this.cantidad = cantidad;
		estaComprado_carrito = false;
	}
	
	public Carrito() {
		super();
		estaComprado_carrito = false;
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
	
	public boolean getEstaComprado_carrito(){
			return estaComprado_carrito;
	}
	
	public void setEstaComprado_carrito(boolean estaComprado_carrito) {
		this.estaComprado_carrito = estaComprado_carrito;
	}
	
}
