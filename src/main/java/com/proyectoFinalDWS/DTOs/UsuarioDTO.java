package com.proyectoFinalDWS.DTOs;

/*
 * Entidad UsuarioDTO
 */
public class UsuarioDTO {
	
	// Atributos

    private long id_usuario;
    private String nombre_usuario;
    private String tlf_usuario;
    private String email_usuario;
    private String psswd_usuario;
    private long id_acceso = 1;
    private boolean estaActivado_usuario = false;
    private String imagen_usuario;

    // Constructores -> Constructor vacío

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

	public long getId_acceso() {
		return id_acceso;
	}

	public void setId_acceso(long id_acceso) {
		this.id_acceso = id_acceso;
	}

	public boolean isEstaActivado_usuario() {
		return estaActivado_usuario;
	}

	public void setEstaActivado_usuario(boolean estaActivado_usuario) {
		this.estaActivado_usuario = estaActivado_usuario;
	}

	public String getImagen_usuario() {
		return imagen_usuario;
	}

	public void setImagen_usuario(String imagen_usuario) {
		this.imagen_usuario = imagen_usuario;
	}
    
}
