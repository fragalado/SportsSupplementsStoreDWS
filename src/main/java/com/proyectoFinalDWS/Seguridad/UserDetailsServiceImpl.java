package com.proyectoFinalDWS.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proyectoFinalDWS.DAOs.Usuario;
import com.proyectoFinalDWS.Repositorios.UsuarioRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	/**
	 * Método que carga un usuario por su nombre de usuario/email (utilizado en la autenticación)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DisabledException {
		
		// Obtenemos el usuario de la base de datos
		Usuario usuarioEncontrado = usuarioRepositorio.findByEmailUsuario(username);
		
		// Construimos la instancia de UserDetails con los datos del usuario encontrado
		UserBuilder builder = null;
		
		// Comprobamos si se ha encontrado el usuario
		if(usuarioEncontrado != null) {
			System.out.println("Usuario encontrado");
			
			// Ahora comprobamos que el usuario este activado
			if(!usuarioEncontrado.isEstaActivado_usuario()) {
				// Entrará si no esta activado
				System.out.println("Usuario desactivado");
				throw new DisabledException("Usuario desactivado");	
			}
			
			// Si esta activado:
			// Configuramos los detalles del usuario para la autentificación
			builder = User.withUsername(username); // Iniciamos la configuración del usuario
			builder.disabled(false); // Indica que el usuario no está deshabilitado
			builder.password(usuarioEncontrado.getPsswd_usuario()); // Configuramos la contraseña del usuario
			builder.authorities("ROLE_" + usuarioEncontrado.getAcceso().getCod_acceso().toUpperCase()); // Configuramos los roles del usuario
		} else {
			System.out.println("Usuario no encontrado");
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
		// Devolvemos la instancia UserDetails construida
		return builder.build();
	}

}
