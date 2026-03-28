package com.saeae.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa a un alumno en la Lista de Cotejo.
 * Tiene una sola calificacion (0-10); el sistema determina automaticamente
 * en que columna del Excel va:
 *   >= 9.5  → Excelente 10  (columna D)
 *   >= 8.5  → Bueno 9       (columna E)
 *   >= 6.5  → Regular 8-7   (columna F)
 *   <  6.5  → No Alcanza    (columna G)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlumnoCotejo {

    private String nombre;
    private double calificacion; // 0-10

    public AlumnoCotejo() {}

    public AlumnoCotejo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }
}
