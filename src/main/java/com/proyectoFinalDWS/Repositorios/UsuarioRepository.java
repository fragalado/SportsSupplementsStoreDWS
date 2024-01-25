package com.proyectoFinalDWS.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectoFinalDWS.DAOs.Usuario;

/**
 * Repositorio que dará servicio a Usuario.
 * Extiende de JpaRepository para realizar operaciones CRUD y otras consultas
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	/**
	 * Método que busca un usuario por el email en la base de datos y lo devuelve
	 * @param email El email a buscar
	 * @return Devuelve el usuario encontrado
	 */
	public Usuario findByEmailUsuario(String email);
}
