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
 * Entidad Suplemento que hace referencia a la tabla Suplementos de la base de datos
 * @autor Fran Gallego
 * Fecha: 25/01/2024
 * Fecha Modificacion: 03/02/2024, se ha añadido nueva propiedad rutaImagen_suplemento (String) y sus respectivos get y set
 */
@Entity
@Table(name = "Suplementos", schema = "gtp_hechos")
public class Suplemento {
	
	// Atributos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_suplemento;
	
	@Column(name = "nombre_suplemento", nullable = false)
	private String nombre_suplemento;
	
	@Column(name = "desc_suplemento", nullable = false)
	private String desc_suplemento;
	
	@Column(name = "precio_suplemento", nullable = false)
	private float precio_suplemento;
	
	@Column(name = "tipo_suplemento", nullable = false)
	private String tipo_suplemento;
	
	@Column(name = "marca_suplemento", nullable = false)
	private String marca_suplemento;
	
	@Column(name = "imagen_suplemento", nullable = false)
	private byte[] imagen_suplemento;
	
	@OneToMany(mappedBy = "suplemento")
	private List<Carrito> listaCarrito;
	
	// Constructores
	
	public Suplemento(String nombre_suplemento, String desc_suplemento, float precio_suplemento, String tipo_suplemento,
			String marca_suplemento, byte[] imagen_suplemento) {
		super();
		this.nombre_suplemento = nombre_suplemento;
		this.desc_suplemento = desc_suplemento;
		this.precio_suplemento = precio_suplemento;
		this.tipo_suplemento = tipo_suplemento;
		this.marca_suplemento = marca_suplemento;
		this.imagen_suplemento = imagen_suplemento;
	}
	
	public Suplemento() {
		super();
	}
	
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
	
	public byte[] getImagen_suplemento() {
		return imagen_suplemento;
	}
	
	public void setImagen_suplemento(byte[] imagen_suplemento) {
		this.imagen_suplemento = imagen_suplemento;
	}
	
}
