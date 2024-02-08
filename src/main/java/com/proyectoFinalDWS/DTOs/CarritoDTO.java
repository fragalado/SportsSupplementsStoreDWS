package com.proyectoFinalDWS.DTOs;

/**
 * Entidad Carrito
 * @author Fran Gallego
 * Fecha: 08/02/2024
 */
public class CarritoDTO {
	
	// Atributos
	
	private long id_carrito;
	private long id_usuario;
	private SuplementoDTO suplementoDTO;
	private int cantidad;
	private boolean estaComprado_carrito;
	
	// Constructores -> Vacio
	
	// Getter y Setter
	
	public long getId_carrito() {
		return id_carrito;
	}
	public void setId_carrito(long id_carrito) {
		this.id_carrito = id_carrito;
	}
	public long getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}
	public SuplementoDTO getSuplementoDTO() {
		return suplementoDTO;
	}
	public void setSuplementoDTO(SuplementoDTO suplementoDTO) {
		this.suplementoDTO = suplementoDTO;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public boolean isEstaComprado_carrito() {
		return estaComprado_carrito;
	}
	public void setEstaComprado_carrito(boolean estaComprado_carrito) {
		this.estaComprado_carrito = estaComprado_carrito;
	}
	
}
