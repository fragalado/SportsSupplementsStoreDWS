package com.proyectoFinalDWS.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyectoFinalDWS.DAOs.Usuario;

/**
 * Repositorio que dará servicio a Usuario.
 * Extiende de JpaRepository para realizar operaciones CRUD y otras consultas
 * @author Fran Gallego
 * Fecha: 25/01/2024
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	/**
	 * Método que busca un usuario por el email en la base de datos y lo devuelve
	 * @param email El email a buscar
	 * @return Devuelve el usuario encontrado
	 */
	@Query("SELECT u FROM Usuario u WHERE u.email_usuario= :email")
	public Usuario findByEmailUsuario(@Param("email") String email);
}
