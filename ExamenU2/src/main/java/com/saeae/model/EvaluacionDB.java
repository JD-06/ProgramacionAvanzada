package com.saeae.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase contenedora raiz para la serializacion JSON.
 * Representa el objeto { "evaluaciones": [...] }
 */
public class EvaluacionDB {

    private List<Evaluacion> evaluaciones;

    public EvaluacionDB() {
        this.evaluaciones = new ArrayList<>();
    }

    public List<Evaluacion> getEvaluaciones() { return evaluaciones; }
    public void setEvaluaciones(List<Evaluacion> evaluaciones) { this.evaluaciones = evaluaciones; }
}
