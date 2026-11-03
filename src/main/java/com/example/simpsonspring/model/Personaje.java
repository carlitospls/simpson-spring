package com.example.simpsonspring.model;

/**
 * getters, setteres, equals, hasCode, toString...
 * @param nombre
 * @param apellido
 * @param edad
 * @param ocupacion
 * @param lugar
 * @param principal
 */
public record Personaje(
        String nombre,
        String apellido,
        int edad,
        String ocupacion,
        String lugar, // sitio donde se suel ver al personaje... casa, escuela
        boolean principal // ¿es de la familia protagista
) {

    // Opcional, puedo tener método propios

    public String nombreCompleto() {

        return apellido.isBlank() ? nombre : nombre +" "+apellido;
    }

    public boolean esMenor(){

        return edad < 18;
    }

}
