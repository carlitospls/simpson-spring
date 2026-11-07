package com.example.simpsonspring.servicio;
import com.example.simpsonspring.model.Personaje;
import com.example.simpsonspring.repository.PersonajeRepositorio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Aquí vive TODA la lógica de los streams
 * El servlet no filtra ni ordena
 */
@Service
public class PersonajeServicio {

    private final PersonajeRepositorio repositorio;

    public PersonajeServicio(PersonajeRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    // En Spring aprenderemos a usar inyección de dependencia y no usar new...
    /**
     *
     * @param lugar
     * @param edadMax
     * @param ordernaPor
     * @param descendente
     * @param limite
     * @return
     */
    // >>> EXAMEN [FILTRO NUEVO] 2/4: añade un parámetro aquí y una línea .filter(p -> param == null || condición) abajo.
    //     Patrón de filtro opcional: si el valor es null/vacío, deja pasar todo.
    public List<Personaje> buscar(String lugar,
                                  Integer edadMax,
                                  String ordernaPor, // pendiente
                                  boolean descendente, // pendiente
                                  Integer limite,
                                  String ocupacion,
                                  boolean soloFamilia
    ) {

        return repositorio.findAll().stream()

                // filter() deja pasar solo los que cumplen la condición
                .filter( p -> lugar == null || lugar.isBlank() || p.lugar().equalsIgnoreCase(lugar))
                .filter(p -> edadMax == null || p.edad() <= edadMax)

                .filter( p -> ocupacion == null || ocupacion.isBlank() || p.ocupacion().equalsIgnoreCase(ocupacion))

                .filter( p -> !soloFamilia || p.principal())

                //.sorted( (p1, p2) -> p1.nombre().compareTo(p2.nombre()))
                //.sorted(Comparator.comparing(Personaje::nombre))

                .sorted(crearComparador(ordernaPor, descendente))
                .limit(limite == null? Integer.MAX_VALUE : limite)
                .toList();
    }

    /**
     *
     * @return
     */
    public List<String> lugaresDisponibles(){
        // FORMA 1: funcional
        return repositorio.findAll().stream()
                //.map(p -> p.lugar())
                .map(Personaje::lugar)
                .distinct()
                .sorted()
                .toList();
        //.collect(Collectors.toList());
        // FORMA 2: IMPERATIVO

        // FORMA 3: convierto a Set (conjunto no repetido..)

    }


    /**
     *
     * @return
     */
    public List<String> ocupacionesDisponibles(){
        return repositorio.findAll().stream()
                .map(Personaje::ocupacion)
                .distinct()
                .sorted()
                .toList();
    }

    /**
     *
     * @param ordenaPor
     * @param descendente
     * @return
     */
    // >>> EXAMEN [ORDEN NUEVO]: para ordenar por otro campo, añade un 'case' al switch. thenComparing(nombre) resuelve los empates.
    private Comparator<Personaje> crearComparador(String ordenaPor, boolean descendente){
        Comparator<Personaje> comparador = switch( ordenaPor == null ? "" : ordenaPor){

            // Por edad y, si dos tienen la misma edad, por nombre;
            //case "edad" -> Comparator.comparingInt(p -> p.edad()); // con lambda
            case "edad" -> Comparator.comparingInt(Personaje::edad).thenComparing(Personaje::nombre); // con referencia a método

            // por apellido y, si no coinciden, por nombre
            case "apellido" -> Comparator.comparing(Personaje::apellido).thenComparing(Personaje::nombre);

            // MEJORA 2
            case "lugar" -> Comparator.comparing(Personaje::lugar).thenComparing(Personaje::nombre);

            default -> Comparator.comparing(Personaje::nombre); // por descarte, ha seleccionado nombre

        };

        return descendente ? comparador.reversed() : comparador;
    }



}
