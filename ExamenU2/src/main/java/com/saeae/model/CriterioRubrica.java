package com.saeae.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa un criterio de la Rubrica de Evaluacion (hoja RubricaProducto).
 * Corresponde a una fila en filas 15-21:
 *   Columna B  = descripcion del criterio
 *   Calificacion (0-10) — el sistema determina automaticamente la columna del Excel:
 *     >= 9.5  -> G (Excelente 10)
 *     >= 8.5  -> I (Bueno 9)
 *     >= 6.5  -> K (Regular 8-7)
 *     <  6.5  -> M (No Alcanza 6-0)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CriterioRubrica {

    private String descripcion;
    private double calificacion; // 0-10

    public CriterioRubrica() {}

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }
}
