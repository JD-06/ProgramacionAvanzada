package com.saeae.model;

/**
 * Representa un criterio de evaluacion del Producto Integrador.
 * Corresponde a una fila en filas 17-22 de la hoja ReporteProductoIntegrador.
 * Columna F = calificacion, Columna G = observacion.
 */
public class CriterioEvaluacion {

    private int numero;           // 1-6
    private double calificacion;  // 0-10
    private String observacion;

    public CriterioEvaluacion() {}

    public CriterioEvaluacion(int numero) {
        this.numero = numero;
        this.calificacion = 0;
        this.observacion = "";
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
