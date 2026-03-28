package com.saeae.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Datos del encabezado comun a los 3 instrumentos de evaluacion.
 * Se mapea a los campos de encabezado de las 3 hojas de examen.xlsx:
 *   ReporteProductoIntegrador : C3=asignatura, G3=grupo, C4=docente, C5=periodo,
 *                               E5=actividad, C6=atributo, C7=criterioDesempenio, C8-C10=indicadores
 *   RubricaProducto           : E6=asignatura, E7=docente, E9=atributo, ...
 *   ListaCotejoAtributo       : C3=asignatura, G3=grupo, C4=docente, ...
 */
public class DatosInstrumento {

    private String fecha;
    private String periodo;               // ej. "ENE-JUN 2026"
    private String actividad;             // nombre de la actividad evaluada

    private String atributo;              // texto del atributo de egreso (de AsignaturaAtributo)

    @JsonProperty("criterio_desempenio")
    private String criterioDesempenio;    // criterio de desempenio correspondiente

    @JsonProperty("nivel_desempenio")
    private String nivelDesempenio = "1"; // nivel 1-4 (celda J8/RubricaProducto y F5/ListaCotejo)

    private List<String> indicadores;     // hasta 3 indicadores (C8-C10)

    private List<CriterioEvaluacion> criterios; // 6 criterios: calificacion + observacion (F17-G22)

    public DatosInstrumento() {
        this.indicadores = new ArrayList<>();
        this.criterios = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            this.criterios.add(new CriterioEvaluacion(i));
        }
        for (int i = 0; i < 3; i++) {
            this.indicadores.add("");
        }
    }

    // -------------------------------------------------------------------------
    // Getters y Setters
    // -------------------------------------------------------------------------

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public String getActividad() { return actividad; }
    public void setActividad(String actividad) { this.actividad = actividad; }

    public String getAtributo() { return atributo; }
    public void setAtributo(String atributo) { this.atributo = atributo; }

    public String getCriterioDesempenio() { return criterioDesempenio; }
    public void setCriterioDesempenio(String criterioDesempenio) { this.criterioDesempenio = criterioDesempenio; }

    public String getNivelDesempenio() { return nivelDesempenio != null ? nivelDesempenio : "1"; }
    public void setNivelDesempenio(String nivelDesempenio) { this.nivelDesempenio = nivelDesempenio; }

    public List<String> getIndicadores() { return indicadores; }
    public void setIndicadores(List<String> indicadores) { this.indicadores = indicadores; }

    public List<CriterioEvaluacion> getCriterios() { return criterios; }
    public void setCriterios(List<CriterioEvaluacion> criterios) { this.criterios = criterios; }
}
