package com.proyectoFinalDWS.DAOs;

import java.util.List;

import jakarta.persistence.CascadeType;
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
 * Entidad Usuario que representa la tabla Usuarios en la base de datos
 * @autor Fran Gallego
 * Fecha: 25/01/2024
 */
@Entity
@Table(name = "Usuarios", schema = "gtp_usuarios")
public class Usuario {
	
    // Atributos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_usuario;
	
	@Column(name = "nombre_usuario", nullable = false, length = 50)
	private String nombre_usuario;
	
	@Column(name = "tlf_usuario", nullable = false)
	private String tlf_usuario;
	
	@Column(name = "email_usuario", nullable = false, length = 50)
	private String email_usuario;
	
	@Column(name = "psswd_usuario", nullable = false)
	private String psswd_usuario;
	
	@ManyToOne
	@JoinColumn(name = "idAcceso")
	private Acceso acceso;
	
	@Column(name = "estaActivado_usuario", nullable = false)
	private boolean estaActivado_usuario;
	
	@Column(name = "imagen_usuario", nullable = true)
	private byte[] imagen_usuario;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Token> listaToken;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	private List<Carrito> listaCarrito;
    
	@OneToMany(mappedBy = "usuario")
	private List<Orden> listaOrden;

    // Constructores -> Constructor vacio (necesario por JPA)
    
    // Getter y Setter
    
	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public String getTlf_usuario() {
		return tlf_usuario;
	}

	public void setTlf_usuario(String tlf_usuario) {
		this.tlf_usuario = tlf_usuario;
	}

	public String getEmail_usuario() {
		return email_usuario;
	}

	public void setEmail_usuario(String email_usuario) {
		this.email_usuario = email_usuario;
	}

	public String getPsswd_usuario() {
		return psswd_usuario;
	}

	public void setPsswd_usuario(String psswd_usuario) {
		this.psswd_usuario = psswd_usuario;
	}

	public Acceso getAcceso() {
		return acceso;
	}

	public void setAcceso(Acceso acceso) {
		this.acceso = acceso;
	}

	public boolean isEstaActivado_usuario() {
		return estaActivado_usuario;
	}

	public void setEstaActivado_usuario(boolean estaActivado_usuario) {
		this.estaActivado_usuario = estaActivado_usuario;
	}

	public byte[] getImagen_usuario() {
		return imagen_usuario;
	}

	public void setImagen_usuario(byte[] imagen_usuario) {
		this.imagen_usuario = imagen_usuario;
	}
    
}
