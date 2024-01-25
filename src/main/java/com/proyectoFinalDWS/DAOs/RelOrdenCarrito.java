package com.proyectoFinalDWS.DAOs;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
 * Entidad RelOrdenCarrito que hace referencia a la tabla Rel_Orden_Carritos de la base de datos
 * @autor Fran Gallego
 * Fecha: 25/01/2024
 */
@Entity
@Table(name = "Rel_Orden_Carritos", schema = "gbp_operacional2")
public class RelOrdenCarrito {
	
	// Atributos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_rel_orden_carrito;
	
	@ManyToOne
	@JoinColumn(name = "id_orden")
	private Orden orden;
	
	@ManyToOne
	@JoinColumn(name = "id_carrito")
	private Carrito carrito;
	
	// Constructores -> Constructor vacío
	
	// Getter y Setter
	
	public long getId_rel_orden_carrito() {
		return id_rel_orden_carrito;
	}

	public void setId_rel_orden_carrito(long id_rel_orden_carrito) {
		this.id_rel_orden_carrito = id_rel_orden_carrito;
	}

	public Orden getOrden() {
		return orden;
	}

	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	public Carrito getCarrito() {
		return carrito;
	}

	public void setCarrito(Carrito carrito) {
		this.carrito = carrito;
	}
	
}
