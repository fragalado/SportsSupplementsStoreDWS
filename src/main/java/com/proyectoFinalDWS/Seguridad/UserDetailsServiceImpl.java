package com.proyectoFinalDWS.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proyectoFinalDWS.DAOs.Usuario;
import com.proyectoFinalDWS.Repositorios.UsuarioRepository;

/**
 * Implementación de la interfaz UserDetailsSerivce
 * @author Fran Gallego
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	// La interfaz UserDetailsService es de Spring Security y sirve para cargar detalles del usuario durante el proceso de autentificación
	
	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	/**
	 * Método que carga un usuario por su nombre de usuario/email (utilizado en la autenticación)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DisabledException {
		
		// Obtenemos el usuario de la base de datos por el email
		Usuario usuarioEncontrado = usuarioRepositorio.findByEmailUsuario(username);
		
		// Comprobamos si no se ha encontrado
		if(usuarioEncontrado == null) {
			System.out.println("Usuario no encontrado");
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
		// Comprobamos si el usuario está desactivado
		if(!usuarioEncontrado.isEstaActivado_usuario()) {
			System.out.println("Usuario desactivado");
			throw new DisabledException("Usuario desactivado");
		}
		
		// Contruimos un objeto UserDetails con los datos
		return User.builder()
				.username(username)
				.password(usuarioEncontrado.getPsswd_usuario())
				.disabled(false)
				.authorities("ROLE_" + usuarioEncontrado.getAcceso().getCod_acceso().toUpperCase())
				.build();
	}

}
