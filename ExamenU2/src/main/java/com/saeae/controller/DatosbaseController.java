package com.saeae.controller;

import com.saeae.model.AlumnoCotejo;
import com.saeae.service.DatosbaseService;
import com.saeae.view.MainView;

import java.util.List;

/**
 * Controlador de Datosbase — responsabilidad unica: leer Datosbase.xlsx.
 *
 * Expone a la vista los datos de referencia de solo lectura:
 *   - Listas para poblar JComboBox (materias, profesores, grupos)
 *   - Alumnos por materia+grupo (para la Lista de Cotejo)
 *   - Atributos de egreso por materia (para el encabezado de los instrumentos)
 *
 * Si Datosbase.xlsx no esta disponible, devuelve valores por defecto.
 */
public class DatosbaseController {

    private final DatosbaseService service;
    private MainView view;

    // Valores de respaldo cuando Datosbase.xlsx no esta disponible
    private static final List<String> DEFAULT_MATERIAS = List.of(
        "PROGRAMACION AVANZADA",
        "BASE DE DATOS AVANZADAS",
        "INGENIERIA DE SOFTWARE",
        "ESTRUCTURA DE DATOS",
        "SISTEMAS OPERATIVOS",
        "REDES DE AREA LOCAL",
        "ANALISIS DE ALGORITMOS",
        "LENGUAJES DE PROGRAMACION"
    );
    private static final List<String> DEFAULT_PROFESORES = List.of(
        "PROFESOR 1", "PROFESOR 2", "PROFESOR 3"
    );
    private static final List<String> DEFAULT_GRUPOS = List.of("G", "H", "I");

    public DatosbaseController() {
        this.service = new DatosbaseService();
    }

    public void setView(MainView view) { this.view = view; }

    // =========================================================================
    // Configuracion
    // =========================================================================

    public void setRutaDatosbase(String ruta) { service.setRutaDatosbase(ruta); }
    public String getRutaDatosbase() { return service.getRutaDatosbase(); }

    /** Indica si Datosbase.xlsx existe en la ruta configurada. */
    public boolean isDisponible() { return service.disponible(); }

    // =========================================================================
    // Datos para JComboBox
    // =========================================================================

    /** Lista de materias unicas del archivo. Usa defaults si no esta disponible. */
    public List<String> getMaterias() {
        List<String> lista = service.getMaterias();
        return lista.isEmpty() ? DEFAULT_MATERIAS : lista;
    }

    /** Lista de profesores unicos. Usa defaults si no esta disponible. */
    public List<String> getProfesores() {
        List<String> lista = service.getProfesores();
        return lista.isEmpty() ? DEFAULT_PROFESORES : lista;
    }

    /**
     * Profesores que imparten la materia indicada.
     * Usa defaults si Datosbase no esta disponible o no hay coincidencias.
     */
    public List<String> getProfesoresPorMateria(String materia) {
        List<String> lista = service.getProfesoresPorMateria(materia);
        return lista.isEmpty() ? DEFAULT_PROFESORES : lista;
    }

    /** Lista de grupos unicos (G, H, I). Usa defaults si no esta disponible. */
    public List<String> getGrupos() {
        List<String> lista = service.getGrupos();
        return lista.isEmpty() ? DEFAULT_GRUPOS : lista;
    }

    /**
     * Grupos donde el profesor imparte la materia indicada.
     * Usa defaults si Datosbase no esta disponible o no hay coincidencias.
     */
    public List<String> getGruposPorMateriaYProfesor(String materia, String profesor) {
        List<String> lista = service.getGruposPorMateriaYProfesor(materia, profesor);
        return lista.isEmpty() ? DEFAULT_GRUPOS : lista;
    }

    // =========================================================================
    // Datos contextuales por evaluacion
    // =========================================================================

    /**
     * Retorna los alumnos del grupo/materia como lista de AlumnoCotejo
     * con nombre cargado y calificaciones en 0 (listas para editar).
     * Lista vacia si no hay datos o Datosbase no esta disponible.
     */
    public List<AlumnoCotejo> getAlumnos(String materia, String grupo) {
        return service.getAlumnos(materia, grupo)
                      .stream()
                      .map(AlumnoCotejo::new)
                      .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Retorna los atributos de egreso asociados a la materia.
     * Lista vacia si no hay datos.
     */
    public List<String> getAtributos(String materia) {
        return service.getAtributos(materia);
    }
}
