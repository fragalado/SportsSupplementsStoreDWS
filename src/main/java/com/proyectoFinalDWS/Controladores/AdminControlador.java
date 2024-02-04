package com.proyectoFinalDWS.Controladores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Servicios.AdminImplementacion;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase controlador para las vistas admin
 * @author Fran Gallego
 * Fecha: 28/01/2024 
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
	
	@Autowired
	private AdminImplementacion adminImplementacion;
	
	@GetMapping("/administracion-usuarios")
	public String vistaAdministracionUsuarios(Model model, HttpServletRequest request) {
		// Control de sesión
		if(!request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/home";
		}
		
		// Obtenemos una lista con todos los usuarios y la ordenamos por el id_acceso
		List<UsuarioDTO> listaUsuarios = adminImplementacion.obtieneTodosLosUsuarios().stream()
																					  .sorted(Comparator.comparingLong(UsuarioDTO::getId_acceso).reversed())
																					  .collect(Collectors.toList());
		// Agregamos la lista al modelo
		model.addAttribute("listaUsuariosDTO", listaUsuarios);
		
		// Devolvemos la vista
		return "administracionUsuarios";
	}
	
	@GetMapping("/administracion-suplementos")
	public String vistaAdministracionSuplementos(Model model, HttpServletRequest request) {
		// Control de sesión
		if(!request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/home";
		}
		
		// Obtenemos una lista con todos los suplementos y lo agregamos al modelo
		model.addAttribute("listaSuplementosDTO", adminImplementacion.obtieneTodosLosSuplementos());
		
		// Devolvemos la vista
		return "administracionSuplementos";
	}
	
	@GetMapping("/editar-usuario/{id_usuario}")
	public String vistaEditarUsuario(@PathVariable long id_usuario, Model model, HttpServletRequest request) {
		// Control de sesión
		if(!request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/home";
		}
		
		// Obtenemos el usuario de la base de datos y lo agregamos al modelo
		UsuarioDTO usuarioDTO = adminImplementacion.obtieneUsuarioPorId(id_usuario);
		
		// Lo agregamos al modelo
		model.addAttribute("usuarioDTO", usuarioDTO);
		
		// Devolvemos la vista
		return "editarUsuario";
	}
	
	@GetMapping("/borra-usuario/{id_usuario}")
	public String borraUsuario(@PathVariable long id_usuario, HttpServletRequest request) {
		// Control de sesión
		if(!request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/home";
		}
		
		// Eliminamos el usuario por el id_usuario
		boolean ok = adminImplementacion.borraUsuarioPorId(id_usuario);
		
		// Devolvemos la vista con un parametro segun se haya elimando o no
		if(ok)
			return "redirect:/admin/administracion-usuarios?success";
		else
			return "redirect:/admin/administracion-usuarios?error";
	}
	
	@GetMapping("/borra-suplemento/{id_suplemento}")
	public String borraSuplemento(@PathVariable long id_suplemento, HttpServletRequest request) {
		// Control de sesión
		if(!request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/home";
		}
		
		// Eliminamos el suplemento por el id_suplemento
		boolean ok = adminImplementacion.borraSuplementoPorId(id_suplemento);
		
		// Devolvemos la vista con un parametro segun se haya elimando o no
		if(ok)
			return "redirect:/admin/administracion-suplementos?success";
		else
			return "redirect:/admin/administracion-suplementos?error";
	}
	
	@PostMapping("/editar-usuario")
	public String editaUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO usuario, @RequestPart("imagenFile") MultipartFile imagenFile, HttpServletRequest request) {
		// Control de sesión
		if(!request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/home";
		}
		
		try {
			if (imagenFile != null && !imagenFile.isEmpty()) {
	            // Genera un nombre único para la imagen
	            String nombreImagen = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(imagenFile.getOriginalFilename());

	            // Combina la ruta de la carpeta con el nombre de la imagen
	            String rutaCompleta = Paths.get("src", "main", "resources", "static", "img", "usuarios", nombreImagen).toString();

	            // Guarda la imagen en el sistema de archivos
	            try {
	                Files.copy(imagenFile.getInputStream(), Paths.get(rutaCompleta), StandardCopyOption.REPLACE_EXISTING);
	            } catch (IOException e) {
	                // Manejar la excepción según sea necesario
	                e.printStackTrace();
	            }

	            // Almacena la ruta de la imagen en la entidad Usuario
	            usuario.setRutaImagen_usuario("/img/usuarios/" + nombreImagen);
	        }
			// Actualizamos el usuario
			boolean ok = adminImplementacion.actualizaUsuario(usuario);
			
			if(ok)
				return "redirect:/admin/administracion-usuarios?usuarioEditadoSuccess";
			else
				return "redirect:/admin/administracion-usuarios?usuarioEditadoError";
		} catch (Exception e) {
			return "redirect:/admin/administracion-usuarios?usuarioEditadoError";
		}
	}
}
