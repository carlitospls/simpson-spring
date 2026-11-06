package com.example.simpsonspring.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO = "caja" donde Spring deja lo que el usuario escribió en el formulario.
 * Cada campo se llama IGUAL que el name/th:field del input correspondiente:
 * así Spring sabe dónde copiar cada valor.
 */
// Lombok genera los getters y setters. Spring los NECESITA para rellenar el objeto.
@Getter
@Setter
public class FiltroPersonajesDto {

    // Opcionales: si vienen vacíos (null o ""), el servicio no filtra por ellos.
    private String lugar;
    private String ocupacion;

    // Integer (no int) porque puede estar vacío: un int no puede valer null.
    // El message="{clave}" busca el texto en messages.properties, no está escrito aquí.
    @Min(value = 0, message = "{filtro.edadMax.rango}")
    @Max(value = 120, message = "{filtro.edadMax.rango}")
    private Integer edadMax;

    // boolean: un checkbox marcado = true, sin marcar = false.
    private boolean soloFamilia;

    // Obligatorio y con solo cuatro valores posibles.
    // El valor por defecto "nombre" es lo que hace que, al entrar sin filtros,
    // salgan todos ordenados por nombre.
    @NotBlank(message = "{filtro.ordenarPor.obligatorio}")
    @Pattern(regexp = "nombre|apellido|edad|lugar", message = "{filtro.ordenarPor.invalido}")
    private String ordenarPor = "nombre";

    private boolean descendente;

    @Min(value = 1, message = "{filtro.limite.rango}")
    @Max(value = 50, message = "{filtro.limite.rango}")
    private Integer limite;
}