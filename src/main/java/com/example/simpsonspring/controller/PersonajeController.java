package com.example.simpsonspring.controller;

import com.example.simpsonspring.dto.FiltroPersonajesDto;
import com.example.simpsonspring.model.Personaje;
import com.example.simpsonspring.servicio.PersonajeServicio;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Capa de PRESENTACIÓN: recibe la petición, valida, pide los datos al servicio
 * y elige la vista. Aquí NO hay ningún stream: eso es trabajo del servicio.
 */
@Controller
@RequestMapping("/personajes")   // todas las URLs de esta clase empiezan por /personajes
public class PersonajeController {

    private final PersonajeServicio servicio;

    // Inyección por constructor: Spring crea el servicio y nos lo entrega aquí.
    public PersonajeController(PersonajeServicio servicio) {
        this.servicio = servicio;
    }

    // ---- Listas para los <select> --------------------------------------
    // Spring ejecuta estos métodos ANTES de listar() y deja lo que devuelven
    // en el Model con el nombre indicado. La vista las lee como ${lugares}
    // y ${ocupaciones}.
    @ModelAttribute("lugares")
    public List<String> lugares() {
        return servicio.lugaresDisponibles();
    }

    @ModelAttribute("ocupaciones")
    public List<String> ocupaciones() {
        return servicio.ocupacionesDisponibles();
    }

    // ---- Petición GET /personajes (con o sin filtros en la URL) --------
    @GetMapping
    public String listar(
            // 1) Spring crea el DTO y lo rellena con los parámetros de la URL
            //    (?edadMax=18&limite=5...), y lo guarda en el Model como "filtro".
            // 2) @Valid ejecuta las anotaciones del DTO (@Min, @Max, @NotBlank...).
            @Valid @ModelAttribute("filtro") FiltroPersonajesDto filtro,
            // Aquí quedan los errores de validación. Debe ir JUSTO después del @Valid.
            BindingResult result,
            Model model) {

        // La vista usará esto para decidir si enseña la tabla o no.
        model.addAttribute("hayErrores", result.hasErrors());

        // Si algún campo no es válido, no buscamos nada: se vuelve a la
        // misma vista y Thymeleaf pintará los errores junto a cada campo.
        if (result.hasErrors()) {
            model.addAttribute("personajes", List.of());
            return "personajes";
        }

        // Todo correcto: el servicio filtra, ordena y limita con streams.
        List<Personaje> personajes = servicio.buscar(
                filtro.getLugar(), filtro.getEdadMax(), filtro.getOrdenarPor(),
                filtro.isDescendente(), filtro.getLimite(),
                filtro.getOcupacion(), filtro.isSoloFamilia());

        model.addAttribute("personajes", personajes);

        // Nombre de la vista: templates/personajes.html
        return "personajes";
    }
}