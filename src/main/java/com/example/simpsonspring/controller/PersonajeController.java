package com.example.simpsonspring.controller;

import com.example.simpsonspring.model.Personaje;
import com.example.simpsonspring.servicio.PersonajeServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String listar(Model model) {
        List<Personaje> personajes = servicio.buscar(null, null, "nombre", false, null, null, false);   // (3)
        model.addAttribute("personajes", personajes);               // (4)
        return "personajes";
    }
}