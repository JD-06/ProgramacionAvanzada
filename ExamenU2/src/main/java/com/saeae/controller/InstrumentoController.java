package com.saeae.controller;

import com.saeae.model.CriterioEvaluacion;
import com.saeae.model.CriterioRubrica;
import com.saeae.model.DatosInstrumento;
import com.saeae.model.Evaluacion;
import com.saeae.model.ListaCotejo;

import java.util.List;

/**
 * Controlador de Instrumentos — responsabilidad unica: logica de negocio pura.
 *
 * Contiene todas las reglas y calculos relacionados con los 3 instrumentos
 * de evaluacion, sin depender de servicios de I/O ni de la vista.
 *
 * Instrumentos cubiertos:
 *   1. Producto Integrador  — validacion de encabezado, promedio de criterios
 *   2. Rubrica              — validacion de criterios con descriptores
 *   3. Lista de Cotejo      — validacion de alumnos con calificaciones
 *
 * Calculo de estatus del semaforo:
 *   ROJO    — evaluacion nula o sin DatosInstrumento
 *   AMARILLO — datos parciales (falta rubrica completa o lista de cotejo)
 *   VERDE   — los 3 instrumentos tienen datos suficientes
 */
public class InstrumentoController {

    // No depende de servicios ni de la vista: sin constructor especial necesario.

    // =========================================================================
    // Estatus del semaforo
    // =========================================================================

    /**
     * Calcula el estatus global de la evaluacion.
     * Reglas:
     *   VERDE    = PI valido AND rubrica valida AND cotejo valido
     *   AMARILLO = PI valido pero falta rubrica o cotejo
     *   ROJO     = no hay DatosInstrumento o la evaluacion es null
     */
    public String calcularEstatus(Evaluacion eval) {
        if (eval == null || eval.getDatosInstrumento() == null) return "ROJO";

        boolean pi      = esProductoIntegradorValido(eval.getDatosInstrumento());
        boolean rubrica = esRubricaValida(eval.getCriteriosRubrica());
        boolean cotejo  = esCotejoValido(eval.getListaCotejo());

        if (pi && rubrica && cotejo) return "VERDE";
        if (pi || rubrica || cotejo) return "AMARILLO";
        return "ROJO";
    }

    // =========================================================================
    // Instrumento 1 — Producto Integrador
    // =========================================================================

    /**
     * Valida que el Producto Integrador tenga al menos los campos minimos:
     * periodo, actividad y atributo de egreso.
     */
    public boolean esProductoIntegradorValido(DatosInstrumento d) {
        if (d == null) return false;
        return noBlanco(d.getPeriodo())
                && noBlanco(d.getActividad())
                && noBlanco(d.getAtributo());
    }

    /**
     * Calcula el promedio de los 6 criterios del Producto Integrador.
     * Solo considera criterios con calificacion > 0.
     * Retorna 0.0 si no hay criterios con valor.
     */
    public double calcularPromedioPI(DatosInstrumento d) {
        if (d == null || d.getCriterios() == null || d.getCriterios().isEmpty()) return 0.0;
        return d.getCriterios().stream()
                .filter(c -> c.getCalificacion() > 0)
                .mapToDouble(CriterioEvaluacion::getCalificacion)
                .average()
                .orElse(0.0);
    }

    /**
     * Determina si un criterio del Producto Integrador es aprobatorio (calificacion >= 6).
     */
    public boolean criterioAprobado(CriterioEvaluacion criterio) {
        return criterio != null && criterio.getCalificacion() >= 6;
    }

    // =========================================================================
    // Instrumento 2 — Rubrica
    // =========================================================================

    /**
     * Valida que la rubrica tenga al menos un criterio con descripcion y calificacion.
     */
    public boolean esRubricaValida(List<CriterioRubrica> criterios) {
        if (criterios == null || criterios.isEmpty()) return false;
        return criterios.stream()
                .anyMatch(c -> noBlanco(c.getDescripcion()) && c.getCalificacion() > 0);
    }

    /**
     * Cuenta cuantos criterios de la rubrica tienen descripcion y calificacion asignada.
     */
    public long criteriosRubricaCompletos(List<CriterioRubrica> criterios) {
        if (criterios == null) return 0;
        return criterios.stream()
                .filter(c -> noBlanco(c.getDescripcion()) && c.getCalificacion() > 0)
                .count();
    }

    // =========================================================================
    // Instrumento 3 — Lista de Cotejo
    // =========================================================================

    /**
     * Valida que la lista de cotejo tenga al menos un alumno registrado.
     */
    public boolean esCotejoValido(ListaCotejo lc) {
        return lc != null
                && lc.getAlumnos() != null
                && !lc.getAlumnos().isEmpty();
    }

    /**
     * Calcula el promedio general del grupo (promedio de todas las calificaciones).
     * Retorna 0.0 si no hay alumnos con calificacion.
     */
    public double calcularPromediosCotejo(ListaCotejo lc) {
        if (!esCotejoValido(lc)) return 0.0;
        return lc.getAlumnos().stream()
                .filter(a -> a.getCalificacion() > 0)
                .mapToDouble(a -> a.getCalificacion())
                .average()
                .orElse(0.0);
    }

    /**
     * Cuenta alumnos con calificacion en nivel "No Alcanza" (calificacion < 6.5).
     */
    public long contarAlumnosNoAlcanza(ListaCotejo lc) {
        if (!esCotejoValido(lc)) return 0;
        return lc.getAlumnos().stream()
                .filter(a -> a.getCalificacion() > 0 && a.getCalificacion() < 6.5)
                .count();
    }

    // =========================================================================
    // Helper privado
    // =========================================================================

    private boolean noBlanco(String s) {
        return s != null && !s.isBlank();
    }
}
