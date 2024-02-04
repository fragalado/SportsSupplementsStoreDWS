package com.proyectoFinalDWS.DTOs;

/**
 * Entidad SuplementoDTO
 * @author Fran Gallego
 * Fecha: 03/02/2024
 */
public class SuplementoDTO {
	// Atributos
	
	private long id_suplemento;
	private String nombre_suplemento;
	private String desc_suplemento;
	private float precio_suplemento;
	private String tipo_suplemento;
	private String marca_suplemento;
	private String rutaImagen_suplemento;
	
	// Constructores
	
	
	
	// Getter y Setter
	
	public long getId_suplemento() {
		return id_suplemento;
	}
	public void setId_suplemento(long id_suplemento) {
		this.id_suplemento = id_suplemento;
	}
	public String getNombre_suplemento() {
		return nombre_suplemento;
	}
	public void setNombre_suplemento(String nombre_suplemento) {
		this.nombre_suplemento = nombre_suplemento;
	}
	public String getDesc_suplemento() {
		return desc_suplemento;
	}
	public void setDesc_suplemento(String desc_suplemento) {
		this.desc_suplemento = desc_suplemento;
	}
	public float getPrecio_suplemento() {
		return precio_suplemento;
	}
	public void setPrecio_suplemento(float precio_suplemento) {
		this.precio_suplemento = precio_suplemento;
	}
	public String getTipo_suplemento() {
		return tipo_suplemento;
	}
	public void setTipo_suplemento(String tipo_suplemento) {
		this.tipo_suplemento = tipo_suplemento;
	}
	public String getMarca_suplemento() {
		return marca_suplemento;
	}
	public void setMarca_suplemento(String marca_suplemento) {
		this.marca_suplemento = marca_suplemento;
	}
	public String getRutaImagen_suplemento() {
		return rutaImagen_suplemento;
	}
	public void setRutaImagen_suplemento(String rutaImagen_suplemento) {
		this.rutaImagen_suplemento = rutaImagen_suplemento;
	}
	
	
}
