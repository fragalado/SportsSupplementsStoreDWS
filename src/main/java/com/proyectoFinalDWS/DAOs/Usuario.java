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
 * Entidad Usuario que representa la tabla Usuarios en la base de datos
 * @autor Fran Gallego
 * Fecha: 25/01/2024
 */
@Entity
@Table(name = "Usuarios", schema = "gbp_operacional2")
public class Usuario {
	
    // Atributos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuario;
	
	@Column(name = "nombre_usuario", nullable = false)
	private String nombreUsuario;
	
	@Column(name = "tlf_usuario", nullable = false)
	private String tlfUsuario;
	
	@Column(name = "email_usuario", nullable = false)
	private String emailUsuario;
	
	@Column(name = "psswd_usuario", nullable = false)
	private String psswdUsuario;
	
	@ManyToOne
	@JoinColumn(name = "idAcceso")
	private Acceso acceso;
	
	@Column(name = "estaActivado_usuario", nullable = false)
	private boolean estaActivadoUsuario;
	
	@Column(name = "rutaImagen_usuario", nullable = true)
	private String rutaImagenUsuario;

	@OneToMany(mappedBy = "usuario")
    private List<Token> listaToken;
	
	@OneToMany(mappedBy = "usuario")
	private List<Carrito> listaCarrito;
    
	@OneToMany(mappedBy = "usuario")
	private List<Orden> listaOrden;

    // Constructores
    
    public Usuario(String nombre_usuario, String tlf_usuario, String email_usuario, String psswd_usuario, boolean estaActivado_usuario, String rutaImagen_usuario)
    {
        this.nombreUsuario = nombre_usuario;
        this.tlfUsuario = tlf_usuario;
        this.emailUsuario = email_usuario;
        this.psswdUsuario = psswd_usuario;
        this.estaActivadoUsuario = estaActivado_usuario;
        this.rutaImagenUsuario = rutaImagen_usuario;
    }

    public Usuario() {}
    
    // Getter y Setter
    
	public long getId_usuario() {
		return idUsuario;
	}

	public void setId_usuario(long id_usuario) {
		this.idUsuario = id_usuario;
	}

	public String getNombre_usuario() {
		return nombreUsuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombreUsuario = nombre_usuario;
	}

	public String getTlf_usuario() {
		return tlfUsuario;
	}

	public void setTlf_usuario(String tlf_usuario) {
		this.tlfUsuario = tlf_usuario;
	}

	public String getEmail_usuario() {
		return emailUsuario;
	}

	public void setEmail_usuario(String email_usuario) {
		this.emailUsuario = email_usuario;
	}

	public String getPsswd_usuario() {
		return psswdUsuario;
	}

	public void setPsswd_usuario(String psswd_usuario) {
		this.psswdUsuario = psswd_usuario;
	}

	public Acceso getAcceso() {
		return acceso;
	}

	public void setAcceso(Acceso acceso) {
		this.acceso = acceso;
	}

	public boolean isEstaActivado_usuario() {
		return estaActivadoUsuario;
	}

	public void setEstaActivado_usuario(boolean estaActivado_usuario) {
		this.estaActivadoUsuario = estaActivado_usuario;
	}

	public String getRutaImagen_usuario() {
		return rutaImagenUsuario;
	}

	public void setRutaImagen_usuario(String rutaImagen_usuario) {
		this.rutaImagenUsuario = rutaImagen_usuario;
	}
    
}
