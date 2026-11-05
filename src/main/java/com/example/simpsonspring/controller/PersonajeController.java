package com.example.simpsonspring.controller;

import com.example.simpsonspring.dto.FiltroPersonajesDto;
import com.example.simpsonspring.model.Personaje;
import com.example.simpsonspring.servicio.PersonajeServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@Controller
//@RequestMapping("/personajes")
//public class PersonajeController {
//
//
//    @GetMapping
//    public String personaje(Model model) {
//
//        return "personajes";
//    }
//
//}

@Controller
@RequestMapping("/personajes")
public class PersonajeController {

    private final PersonajeServicio servicio;                    // (1)

    public PersonajeController(PersonajeServicio servicio) {     // (2)
        this.servicio = servicio;
    }

    @GetMapping
    public String listar(@ModelAttribute("filtro") FiltroPersonajesDto filtro, Model model) {
        List<Personaje> personajes = servicio.buscar(
                filtro.getLugar(), filtro.getEdadMax(), filtro.getOrdenarPor(),
                filtro.isDescendente(), filtro.getLimite(),
                filtro.getOcupacion(), filtro.isSoloFamilia());
        model.addAttribute("personajes", personajes);
        return "personajes";
    }
}