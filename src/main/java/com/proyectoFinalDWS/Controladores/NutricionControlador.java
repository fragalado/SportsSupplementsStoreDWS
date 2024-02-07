package com.proyectoFinalDWS.Controladores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectoFinalDWS.DTOs.SuplementoDTO;
import com.proyectoFinalDWS.Servicios.SuplementoImplementacion;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase controlador para la vista Nutrición
 * @author Fran Gallego
 * Fecha: 07/02/2024
 */
@Controller
@RequestMapping("/nutricion")
public class NutricionControlador {

	@Autowired
	private SuplementoImplementacion suplementoImplementacion;
	
	@GetMapping("/{tipo}")
	public String vistaNutricion(@PathVariable int tipo, Model model, HttpServletRequest request) {
		
		// Obtenemos todos los suplementos
		List<SuplementoDTO> listaSuplementosDTO = suplementoImplementacion.obtieneTodosLosSuplementos();
		
		if(tipo == 1)
			listaSuplementosDTO = listaSuplementosDTO.stream().filter(x -> x.getTipo_suplemento().equals("Proteína")).collect(Collectors.toList());
		else if(tipo == 2)
			listaSuplementosDTO = listaSuplementosDTO.stream().filter(x -> x.getTipo_suplemento().equals("Creatina")).collect(Collectors.toList());
		
		// Añadimos la lista al modelo
		model.addAttribute("listaSuplementosDTO", listaSuplementosDTO);
		
		// Devolvemos la vista
		return "suplementos";
	}
}
