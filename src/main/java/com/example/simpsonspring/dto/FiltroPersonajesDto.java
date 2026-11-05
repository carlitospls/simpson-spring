package com.example.simpsonspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiltroPersonajesDto {

    private String lugar;

    private String ocupacion;

    private Integer edadMax;

    private boolean soloFamilia;

    private String ordenarPor = "nombre";

    private boolean descendente;

    private Integer limite;
}
