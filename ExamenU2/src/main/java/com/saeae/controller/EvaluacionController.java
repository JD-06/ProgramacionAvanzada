package com.saeae.controller;

import com.saeae.model.Evaluacion;
import com.saeae.model.EvaluacionRepository;
import com.saeae.view.MainView;

import java.io.IOException;
import java.util.Optional;

/**
 * Controlador de Evaluaciones — responsabilidad unica: CRUD sobre evaluaciones.json
 *
 * Solo interactua con EvaluacionRepository.
 * No sabe nada de Excel, Datosbase ni logica de instrumentos.
 */
public class EvaluacionController {

    private final EvaluacionRepository repository;
    private MainView view;

    public EvaluacionController() {
        this.repository = new EvaluacionRepository();
    }

    public void setView(MainView view) { this.view = view; }

    // =========================================================================
    // CRUD
    // =========================================================================

    /**
     * Busca en el JSON la evaluacion para la terna dada.
     * Retorna Optional.empty() si no existe ninguna coincidencia.
     */
    public Optional<Evaluacion> cargar(String asignatura, String profesor, String grupo) {
        String id = repository.generateId(asignatura, profesor, grupo);
        try {
            return repository.findById(id);
        } catch (IOException e) {
            view.mostrarError("Error al leer evaluaciones.json:\n" + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Persiste la evaluacion en el JSON (crea o sobreescribe por ID).
     * No genera Excel — eso es responsabilidad de ReporteController.
     * Retorna true si se guardo correctamente.
     */
    public boolean guardar(Evaluacion evaluacion) {
        try {
            repository.save(evaluacion);
            return true;
        } catch (IOException e) {
            view.mostrarError("Error al escribir evaluaciones.json:\n" + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina el registro de la terna dada del JSON.
     * Retorna true si se encontro y elimino; false si no existia.
     */
    public boolean eliminar(String asignatura, String profesor, String grupo) {
        String id = repository.generateId(asignatura, profesor, grupo);
        try {
            return repository.delete(id);
        } catch (IOException e) {
            view.mostrarError("Error al eliminar de evaluaciones.json:\n" + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // Utilidad
    // =========================================================================

    /** Genera el ID compuesto sin exponer el repositorio a la vista. */
    public String generarId(String asignatura, String profesor, String grupo) {
        return repository.generateId(asignatura, profesor, grupo);
    }
}
