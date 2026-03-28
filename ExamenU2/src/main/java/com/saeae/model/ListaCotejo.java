package com.saeae.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista de Cotejo — instrumento que evalua a cada alumno del grupo
 * con una escala de 4 niveles (hoja ListaCotejoAtributo de examen.xlsx).
 *
 * Los alumnos se cargan de Datosbase.xlsx filtrando por Asignatura+Grupo.
 * Cada alumno tiene calificaciones en 4 escalas:
 *   Excelente 10 | Bueno 9 | Regular 8-7 | No Alcanza 6-0
 */
public class ListaCotejo {

    private List<AlumnoCotejo> alumnos;

    public ListaCotejo() {
        this.alumnos = new ArrayList<>();
    }

    public List<AlumnoCotejo> getAlumnos() { return alumnos; }
    public void setAlumnos(List<AlumnoCotejo> alumnos) { this.alumnos = alumnos; }
}
