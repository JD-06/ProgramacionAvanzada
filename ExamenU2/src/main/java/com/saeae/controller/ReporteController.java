package com.saeae.controller;

import com.saeae.model.Evaluacion;
import com.saeae.service.ExcelService;
import com.saeae.view.MainView;

import java.io.IOException;

/**
 * Controlador de Reportes — responsabilidad unica: generacion de archivos Excel.
 *
 * Genera un archivo por evaluacion (Asignatura_Profesor_Grupo.xlsx)
 * usando la plantilla C:\Users\const\Downloads\examen.xlsx como base.
 */
public class ReporteController {

    private final ExcelService service;
    private MainView view;

    private String carpetaGuardado = "reportes";

    public ReporteController() {
        this.service = new ExcelService();
    }

    public void setView(MainView view) { this.view = view; }

    // =========================================================================
    // Configuracion de rutas
    // =========================================================================

    public void setCarpetaGuardado(String carpeta) { this.carpetaGuardado = carpeta; }
    public String getCarpetaGuardado() { return carpetaGuardado; }

    public void setRutaPlantilla(String ruta) { service.setRutaPlantilla(ruta); }
    public String getRutaPlantilla() { return service.getRutaPlantilla(); }

    public boolean isPlantillaDisponible() {
        return new java.io.File(service.getRutaPlantilla()).exists();
    }

    // =========================================================================
    // Generacion de Excel
    // =========================================================================

    /**
     * Genera o sobreescribe el archivo Excel para la evaluacion dada.
     * Nombre resultante: Asignatura_Profesor_Grupo.xlsx
     *
     * @return Ruta absoluta del archivo generado, o null si ocurrio un error.
     */
    public String generarExcel(Evaluacion evaluacion) {
        try {
            return service.generarOActualizarExcel(evaluacion, carpetaGuardado);
        } catch (IOException e) {
            view.mostrarError("Error al generar el archivo Excel:\n" + e.getMessage());
            return null;
        }
    }

    /**
     * Construye el nombre esperado del archivo sin generarlo.
     */
    public String nombreArchivo(Evaluacion evaluacion) {
        String a = evaluacion.getAsignatura() != null
                ? evaluacion.getAsignatura().replaceAll("[^a-zA-Z0-9]", "_").replaceAll("_+", "_") : "sin_asignatura";
        String p = evaluacion.getProfesor() != null
                ? evaluacion.getProfesor().replaceAll("[^a-zA-Z0-9]", "_").replaceAll("_+", "_") : "sin_profesor";
        String g = evaluacion.getGrupo() != null ? evaluacion.getGrupo() : "sin_grupo";
        return a + "_" + p + "_" + g + ".xlsx";
    }
}
