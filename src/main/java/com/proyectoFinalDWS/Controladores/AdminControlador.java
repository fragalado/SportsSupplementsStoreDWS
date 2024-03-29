package com.proyectoFinalDWS.Controladores;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.proyectoFinalDWS.DTOs.SuplementoDTO;
import com.proyectoFinalDWS.DTOs.UsuarioDTO;
import com.proyectoFinalDWS.Servicios.SuplementoImplementacion;
import com.proyectoFinalDWS.Servicios.UsuarioImplementacion;
import com.proyectoFinalDWS.Utiles.Util;

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
	private SuplementoImplementacion suplementoImplementacion;
	
	@Autowired
	private UsuarioImplementacion usuarioImplementacion;
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/admin/administracion-usuarios"
	 * 
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@GetMapping("/administracion-usuarios")
	public String vistaAdministracionUsuarios(Model model, HttpServletRequest request) {
		try {
			// Log
			Util.logInfo("AdminControlador", "vistaAdministracionUsuarios", "Ha entrado en vistaAdministracionUsuarios");
			
			// Control de sesión
			if(!request.isUserInRole("ROLE_ADMIN")) {
				// Log
				Util.logInfo("AdminControlador", "vistaAdministracionUsuarios", "El usuario no es admin. Se redirige a home.");
				return "redirect:/home";
			}
			
			// Obtenemos una lista con todos los usuarios y la ordenamos por el id_acceso
			List<UsuarioDTO> listaUsuarios = usuarioImplementacion.obtieneTodosLosUsuarios().stream()
																						  .sorted(Comparator.comparingLong(UsuarioDTO::getId_acceso).reversed())
																						  .collect(Collectors.toList());
			// Agregamos la lista al modelo
			model.addAttribute("listaUsuariosDTO", listaUsuarios);
			
			// Devolvemos la vista
			return "administracionUsuarios";
		} catch (Exception e) {
			// Log
			Util.logError("AdminControlador", "vistaAdministracionUsuarios", "Se ha producido un error.");
			return "redirect:/home";
		}
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/admin/administracion-suplementos"
	 * 
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@GetMapping("/administracion-suplementos")
	public String vistaAdministracionSuplementos(Model model, HttpServletRequest request) {

		try {
			// Log
			Util.logInfo("AdminControlador", "vistaAdministracionSuplementos", "Ha entrado en vistaAdministracionSuplementos");
			// Control de sesión
			if (!request.isUserInRole("ROLE_ADMIN")) {
				// Log
				Util.logInfo("AdminControlador", "vistaAdministracionSuplementos", "El usuario no es admin. Se redirige a home");
				return "redirect:/home";
			}

			// Obtenemos una lista con todos los suplementos y lo agregamos al modelo
			model.addAttribute("listaSuplementosDTO", suplementoImplementacion.obtieneTodosLosSuplementos());

			// Devolvemos la vista
			return "administracionSuplementos";
		} catch (Exception e) {
			// Log
			Util.logError("AdminControlador", "vistaAdministracionSuplementos", "Se ha producido un error.");
			return "redirect:/home";
		}
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/admin/editar-usuario/{id_usuario}"
	 * 
	 * @param id_usuario Id del usuario a editar
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@GetMapping("/editar-usuario/{id_usuario}")
	public String vistaEditarUsuario(@PathVariable long id_usuario, Model model, HttpServletRequest request) {

		try {
			// Log
			Util.logInfo("AdminControlador", "vistaEditarUsuario", "Ha entrado en vistaEditarUsuario.");
			// Control de sesión
			if (!request.isUserInRole("ROLE_ADMIN")) {
				// Log
				Util.logInfo("AdminControlador", "vistaEditarUsuario", "El usuario no es admin. Se redirige a home");
				return "redirect:/home";
			}

			// Obtenemos el usuario de la base de datos y lo agregamos al modelo
			UsuarioDTO usuarioDTO = usuarioImplementacion.obtieneUsuarioPorId(id_usuario);

			// Lo agregamos al modelo
			model.addAttribute("usuarioDTO", usuarioDTO);

			// Devolvemos la vista
			return "editarUsuario";
		} catch (Exception e) {
			// Log
			Util.logError("AdminControlador", "vistaEditarUsuario", "Se ha producido un error.");
			return "redirect:/home";
		}
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/admin/editar-suplemento/{id_suplemento}"
	 * 
	 * @param id_suplemento Id del suplemento a editar
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@GetMapping("/editar-suplemento/{id_suplemento}")
	public String vistaEditarSuplemento(@PathVariable long id_suplemento, Model model, HttpServletRequest request) {

		try {
			// Log
			Util.logInfo("AdminControlador", "vistaEditarSuplemento", "Ha entrado en vistaEditarSuplemento.");
			// Control de sesión
			if (!request.isUserInRole("ROLE_ADMIN")) {
				// Log
				Util.logInfo("AdminControlador", "vistaEditarSuplemento", "El usuario no es admin. Se redirige a home");
				return "redirect:/home";
			}

			// Obtenemos el suplemento de la base de datos y lo agregamos al modelo
			SuplementoDTO suplementoDTO = suplementoImplementacion.obtieneSuplementoPorId(id_suplemento);

			// Lo agregamos al modelo
			model.addAttribute("suplementoDTO", suplementoDTO);

			// Devolvemos la vista
			return "editarSuplemento";
		} catch (Exception e) {
			// Log
			Util.logError("AdminControlador", "vistaEditarSuplemento", "Se ha producido un error.");
			return "redirect:/home";
		}
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/admin/agrega-suplemento"
	 * 
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@GetMapping("/agrega-suplemento")
	public String vistaAgregarSuplemento(Model model, HttpServletRequest request) {

		try {
			// Log
			Util.logInfo("AdminControlador", "vistaAgregarSuplemento", "Ha entrado en vistaAgregarSuplemento.");
			// Control de sesión
			if (!request.isUserInRole("ROLE_ADMIN")) {
				// Log
				Util.logInfo("AdminControlador", "vistaAgregarSuplemento", "El usuario no es admin. Se redirige a home");
				return "redirect:/home";
			}

			// Agregamos al modelo un objeto suplemento
			model.addAttribute("suplementoDTO", new SuplementoDTO());

			// Devolvemos la vista
			return "agregarSuplemento";
		} catch (Exception e) {
			// Log
			Util.logError("AdminControlador", "vistaAgregarSuplemento", "Se ha producido un error.");
			return "redirect:/home";
		}
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/admin/agrega-usuario"
	 * @param model Objeto Model que proporciona Spring para enviar datos a la vista
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@GetMapping("/agrega-usuario")
	public String vistaAgregarUsuario(Model model, HttpServletRequest request) {
		try {
			// Log
			Util.logInfo("AdminControlador", "vistaAgregarUsuario", "Ha entrado en vistaAgregarUsuario.");
			// Control de sesión
			if (!request.isUserInRole("ROLE_ADMIN")) {
				// Log
				Util.logInfo("AdminControlador", "vistaAgregarUsuario", "El usuario no es admin. Se redirige a home");
				return "redirect:/home";
			}
			
			// Agregamos al modelo un objeto de tipo UsuarioDTO
			model.addAttribute("usuarioDTO", new UsuarioDTO());
			
			// Devolvemos el nombre de la vista
			return "agregarUsuario";
		} catch (Exception e) {
			// Log
			Util.logError("AdminControlador", "vistaAgregarUsuario", "Se ha producido un error.");
			return "redirect:/home";
		}
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/admin/borra-usuario/{id_usuario}"
	 * 
	 * @param id_usuario Id del usuario a borrar
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@GetMapping("/borra-usuario/{id_usuario}")
	public String borraUsuario(@PathVariable long id_usuario, HttpServletRequest request) {

		try {
			// Log
			Util.logInfo("AdminControlador", "borraUsuario", "Ha entrado en borraUsuario.");
			// Control de sesión
			if (!request.isUserInRole("ROLE_ADMIN")) {
				// Log
				Util.logInfo("AdminControlador", "borraUsuario", "El usuario no es admin. Se redirige a home.");
				return "redirect:/home";
			}

			// Eliminamos el usuario por el id_usuario
			boolean ok = usuarioImplementacion.borraUsuarioPorId(id_usuario);

			// Devolvemos la vista con un parametro segun se haya elimando o no
			if (ok)
				return "redirect:/admin/administracion-usuarios?success";
			else
				return "redirect:/admin/administracion-usuarios?error";
		} catch (Exception e) {
			// Log
			Util.logError("AdminControlador", "borraUsuario", "Se ha producido un error.");
			return "redirect:/admin/administracion-usuarios?error";
		}
	}
	
	/**
	 * Método que maneja las solicitudes GET para la ruta "/admin/borra-suplemento/{id_suplemento}"
	 * 
	 * @param id_suplemento Id del suplemento a borrar
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@GetMapping("/borra-suplemento/{id_suplemento}")
	public String borraSuplemento(@PathVariable long id_suplemento, HttpServletRequest request) {

		try {
			// Log
			Util.logInfo("AdminControlador", "borraSuplemento", "Ha entrado en borraSuplemento.");
			// Control de sesión
			if (!request.isUserInRole("ROLE_ADMIN")) {
				// Log
				Util.logInfo("AdminControlador", "borraSuplemento", "El usuario no es admin. Se redirige a home.");
				return "redirect:/home";
			}

			// Eliminamos el suplemento por el id_suplemento
			boolean ok = suplementoImplementacion.borraSuplementoPorId(id_suplemento);

			// Devolvemos la vista con un parametro segun se haya elimando o no
			if (ok)
				return "redirect:/admin/administracion-suplementos?success";
			else
				return "redirect:/admin/administracion-suplementos?error";
		} catch (Exception e) {
			// Log
			Util.logError("AdminControlador", "borraSuplemento", "Se ha producido un error.");
			return "redirect:/admin/administracion-suplementos?error";
		}
	}
	
	/**
	 * Método que maneja las solicitudes POST para la ruta "/admin/editar-usuario"
	 * 
	 * @param usuario Objeto usuario con los datos del formulario
	 * @param imagenFile Objeto MultipartFile que contiene la imagen
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@PostMapping("/editar-usuario")
	public String editaUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO usuario, @RequestPart("imagenFile") MultipartFile imagenFile, HttpServletRequest request) {
		
		try {
			// Log
			Util.logInfo("AdminControlador", "editaUsuario", "Ha entrado en editaUsuario.");
			// Control de sesión
			if (!request.isUserInRole("ROLE_ADMIN")) {
				// Log
				Util.logInfo("AdminControlador", "editaUsuario", "El usuario no es admin. Se redirige a home.");
				return "redirect:/home";
			}
			
			// Controlamos los valores
			if(usuario.getNombre_usuario().length() > 50 || usuario.getEmail_usuario().length() > 50 
					|| usuario.getTlf_usuario().length() > 15)
				return "redirect:/admin/administracion-usuarios?usuarioAgregadoError";
			
			// Si la imagen no esta vacia se la añadimos al usuario
			if(!imagenFile.isEmpty()) {
				// Pasamos la imagen a String
				String foto = Util.convertirABase64(imagenFile.getBytes());
				
				// Le añadimos la imagen al usuarioDTO
				usuario.setImagen_usuario(foto);
			}
			
			// Actualizamos el usuario
			boolean ok = usuarioImplementacion.actualizaUsuario(usuario);
			
			if(ok)
				return "redirect:/admin/administracion-usuarios?usuarioEditadoSuccess";
			else
				return "redirect:/admin/administracion-usuarios?usuarioEditadoError";
		} catch (Exception e) {
			// Log
			Util.logError("AdminControlador", "editaUsuario", "Se ha producido un error.");
			return "redirect:/admin/administracion-usuarios?usuarioEditadoError";
		}
	}
	
	/**
	 * Método que realiza la operación de agregar un usuario a la base de datos.
	 * 
	 * @param usuarioDTO Objeto UsuarioDTO con los datos del usuario a agregar.
	 * @param imagenFile Objeto MultipartFile con la imagen del usuario.
	 * @param request Objeto HttpServletRequest con datos de la sesión
	 * @return Devuelve una redirección.
	 */
	@PostMapping("/agregar-usuario")
	public String agregaUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO, @RequestPart("imagenFile") MultipartFile imagenFile, HttpServletRequest request) {
		try {
			// Log
			Util.logInfo("AdminControlador", "agregaUsuario", "Ha entrado en agregaUsuario.");
			
			// Control de sesión
			if (!request.isUserInRole("ROLE_ADMIN")) {
				// Log
				Util.logInfo("AdminControlador", "agregaUsuario", "El usuario no es admin. Se redirige a home.");
				return "redirect:/home";
			}
			
			// Controlamos los valores
			if(usuarioDTO.getNombre_usuario().length() > 50 || usuarioDTO.getEmail_usuario().length() > 50 
					|| usuarioDTO.getTlf_usuario().length() > 15 || usuarioDTO.getPsswd_usuario().length() > 255)
				return "redirect:/admin/administracion-usuarios?usuarioAgregadoError";
			
			// Si la imagen no esta vacia se la añadimos al usuario
			if(!imagenFile.isEmpty()) {
				// Pasamos la imagen a String
				String foto = Util.convertirABase64(imagenFile.getBytes());
				
				// Le añadimos la imagen al usuarioDTO
				usuarioDTO.setImagen_usuario(foto);
			}

			
			// Actualizamos el suplemento
			boolean ok = usuarioImplementacion.agregaUsuario(usuarioDTO);
			
			if(ok)
				return "redirect:/admin/administracion-usuarios?usuarioAgregadoSuccess";
			else
				return "redirect:/admin/administracion-usuarios?usuarioAgregadoExiste";
		} catch (Exception e) {
			// Log
			Util.logError("AdminControlador", "agregaUsuario", "Se ha producido un error.");
			return "redirect:/admin/administracion-usuarios?usuarioAgregadoError";
		}
	}
	
	/**
	 * Método que maneja las solicitudes POST para la ruta "/admin/editar-suplemento"
	 * 
	 * @param suplementoDTO Objeto suplemento con los datos del formulario
	 * @param imagenFile Objeto MultipartFile que contiene la imagen
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@PostMapping("/editar-suplemento")
	public String editaSuplemento(@ModelAttribute("suplementoDTO") SuplementoDTO suplementoDTO, @RequestPart("imagenFile") MultipartFile imagenFile, HttpServletRequest request) {
		
		try {
			// Log
			Util.logInfo("AdminControlador", "editaSuplemento", "Ha entrado en editaSuplemento.");
			// Control de sesión
			if (!request.isUserInRole("ROLE_ADMIN")) {
				// Log
				Util.logInfo("AdminControlador", "editaSuplemento", "El usuario no es admin. Se redirige a home.");
				return "redirect:/home";
			}
			
			// Controlamos los valores
			if(suplementoDTO.getPrecio_suplemento() > 999 || suplementoDTO.getNombre_suplemento().length() > 255 
					|| suplementoDTO.getDesc_suplemento().length() > 255 || suplementoDTO.getMarca_suplemento().length() > 255 
					|| suplementoDTO.getTipo_suplemento().length() > 255) {
				return "redirect:/admin/administracion-suplementos?suplementoEditadoError";
			}
			
			// Pasamos la imagen a String
			String foto = Util.convertirABase64(imagenFile.getBytes());
			
			// Le añadimos la imagen al suplementoDTO
			suplementoDTO.setImagen_suplemento(foto);
			
			// Actualizamos el suplemento
			boolean ok = suplementoImplementacion.actualizaSuplemento(suplementoDTO);
			
			if(ok)
				return "redirect:/admin/administracion-suplementos?suplementoEditadoSuccess";
			else
				return "redirect:/admin/administracion-suplementos?suplementoEditadoError";
		} catch (Exception e) {
			// Log
			Util.logError("AdminControlador", "editaSuplemento", "Se ha producido un error.");
			return "redirect:/admin/administracion-suplementos?suplementoEditadoError";
		}
	}
	
	/**
	 * Método que maneja las solicitudes POST para la ruta "/admin/agregar-suplemento"
	 * 
	 * @param suplementoDTO Objeto suplemento con los datos del formulario
	 * @param imagenFile Objeto MultipartFile que contiene la imagen
	 * @param request Objeto HttpServletRequest para poder acceder a información sobre la solicitud HTTP
	 * @return Devuelve el nombre de la vista
	 */
	@PostMapping("/agregar-suplemento")
	public String agregaSuplemento(@ModelAttribute("suplementoDTO") SuplementoDTO suplementoDTO, @RequestPart("imagenFile") MultipartFile imagenFile, HttpServletRequest request) {
		
		try {
			// Log
			Util.logInfo("AdminControlador", "agregaSuplemento", "Ha entrado en agregaSuplemento.");
			// Control de sesión
			if (!request.isUserInRole("ROLE_ADMIN")) {
				// Log
				Util.logInfo("AdminControlador", "agregaSuplemento", "El usuario no es admin. Se redirige a home.");
				return "redirect:/home";
			}
			
			// Controlamos los valores
			if(suplementoDTO.getPrecio_suplemento() > 999 || suplementoDTO.getNombre_suplemento().length() > 255 
					|| suplementoDTO.getDesc_suplemento().length() > 255 || suplementoDTO.getMarca_suplemento().length() > 255 
					|| suplementoDTO.getTipo_suplemento().length() > 255) {
				return "redirect:/admin/administracion-suplementos?suplementoAgregadoError";
			}
			
			// Pasamos la imagen a String
			String foto = Util.convertirABase64(imagenFile.getBytes());
			
			// Le añadimos la imagen al suplementoDTO
			suplementoDTO.setImagen_suplemento(foto);
			
			// Agregamos el suplemento a la base de datos
			boolean ok = suplementoImplementacion.agregaSuplemento(suplementoDTO);
			
			if(ok)
				return "redirect:/admin/administracion-suplementos?suplementoAgregadoSuccess";
			else
				return "redirect:/admin/administracion-suplementos?suplementoAgregadoError";
		} catch (Exception e) {
			// Log
			Util.logError("AdminControlador", "agregaSuplemento", "Se ha producido un error.");
			return "redirect:/admin/administracion-suplementos?suplementoAgregadoError";
		}
	}
	
}
