package com.proyectoFinalDWS.Controladores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectoFinalDWS.DTOs.SuplementoDTO;
import com.proyectoFinalDWS.Servicios.SuplementoImplementacion;


/**
 * Clase Controlador para las vistas relacionados con el home/bienvenida
 */
@Controller
@RequestMapping("/home")
public class HomeControlador {
	
	@Autowired
	private SuplementoImplementacion suplementoImplementacion;
	
	@GetMapping("")
	public String vistaHome(Model model) {
		
		// Obtenemos todos los suplementos
		List<SuplementoDTO> listaSuplementosDTO = suplementoImplementacion.obtieneTodosLosSuplementos();
		
		// Ahora vamos a añadir al modelo los 6 primeros suplementos
		if(listaSuplementosDTO.size() > 6)
			model.addAttribute("listaSuplementosDTO", listaSuplementosDTO.stream().limit(6).collect(Collectors.toList()));
		else
			model.addAttribute("listaSuplementosDTO", listaSuplementosDTO.stream().limit(listaSuplementosDTO.size()).collect(Collectors.toList()));

		// Devolvemos la vista register
		return "home";
	}
}
