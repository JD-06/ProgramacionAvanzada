package com.saeae.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class Equipo {

    private List<String> nombres;

    @JsonProperty("calificacion_rubrica")
    private double calificacionRubrica;

    public Equipo() {
        this.nombres = new ArrayList<>();
    }

    public List<String> getNombres() { return nombres; }
    public void setNombres(List<String> nombres) { this.nombres = nombres; }

    public double getCalificacionRubrica() { return calificacionRubrica; }
    public void setCalificacionRubrica(double calificacionRubrica) { this.calificacionRubrica = calificacionRubrica; }
}
