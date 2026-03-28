package com.saeae.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Evaluaciones — capa de persistencia JSON.
 * Implementa operaciones CRUD sobre el archivo evaluaciones.json.
 */
public class EvaluacionRepository {

    private static final String FILE_PATH = "datos/evaluaciones.json";
    private final ObjectMapper mapper;

    public EvaluacionRepository() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        new File("datos").mkdirs();
    }

    /** Lee todas las evaluaciones del JSON. */
    public List<Evaluacion> findAll() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        EvaluacionDB db = mapper.readValue(file, EvaluacionDB.class);
        return db.getEvaluaciones() != null ? db.getEvaluaciones() : new ArrayList<>();
    }

    /** Busca una evaluacion por su ID compuesto. */
    public Optional<Evaluacion> findById(String id) throws IOException {
        return findAll().stream()
                .filter(e -> id.equals(e.getId()))
                .findFirst();
    }

    /**
     * Guarda o sobreescribe una evaluacion en el JSON.
     * Si ya existe con el mismo id, la reemplaza (no duplica).
     */
    public void save(Evaluacion evaluacion) throws IOException {
        List<Evaluacion> all = findAll();
        all.removeIf(e -> evaluacion.getId().equals(e.getId()));
        all.add(evaluacion);
        persist(all);
    }

    /** Elimina la evaluacion con el id dado. Retorna true si se elimino. */
    public boolean delete(String id) throws IOException {
        List<Evaluacion> all = findAll();
        boolean removed = all.removeIf(e -> id.equals(e.getId()));
        if (removed) {
            persist(all);
        }
        return removed;
    }

    /** Genera el ID compuesto Asignatura_Profesor_Grupo (sin espacios). */
    public String generateId(String asignatura, String profesor, String grupo) {
        return sanitize(asignatura) + "_" + sanitize(profesor) + "_" + sanitize(grupo);
    }

    private String sanitize(String value) {
        return value != null ? value.replaceAll("[^a-zA-Z0-9]", "") : "";
    }

    private void persist(List<Evaluacion> evaluaciones) throws IOException {
        EvaluacionDB db = new EvaluacionDB();
        db.setEvaluaciones(evaluaciones);
        mapper.writeValue(new File(FILE_PATH), db);
    }
}
