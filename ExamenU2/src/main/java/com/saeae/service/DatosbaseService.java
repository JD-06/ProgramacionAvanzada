package com.saeae.service;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Servicio de lectura de Datosbase.xlsx — fuente de referencia de solo lectura.
 *
 * Hoja "ListasAsistencia" (con encabezado en fila 0):
 *   Col 0 = LETRA   (grupo: G, H, I)
 *   Col 1 = PROFESOR
 *   Col 2 = MATERIA
 *   Col 3 = MATRICULA
 *   Col 4 = ALUMNO
 *
 * Hoja "AsignaturaAtributo" (SIN encabezado, datos desde fila 0):
 *   Col 0 = CARRERA
 *   Col 1 = ASIGNATURA
 *   Col 2 = ATRIBUTO
 */
public class DatosbaseService {

    private String rutaDatosbase = "Datosbase.xlsx";

    public void setRutaDatosbase(String ruta) { this.rutaDatosbase = ruta; }
    public String getRutaDatosbase() { return rutaDatosbase; }

    /** Comprueba si el archivo Datosbase.xlsx existe en la ruta configurada. */
    public boolean disponible() {
        return new File(rutaDatosbase).exists();
    }

    // =========================================================================
    // Metodos para poblar los JComboBox
    // =========================================================================

    /** Lista de materias unicas (Col 2) de la hoja ListasAsistencia. */
    public List<String> getMaterias() {
        return getDistinctFromAsistencia(2);
    }

    /** Lista de profesores unicos (Col 1) de la hoja ListasAsistencia. */
    public List<String> getProfesores() {
        return getDistinctFromAsistencia(1);
    }

    /**
     * Devuelve los profesores que imparten la materia indicada.
     * Filtra: MATERIA == materia (case-insensitive) → valores distintos de PROFESOR (Col 1).
     * Retorna lista vacia si no hay coincidencias o el archivo no esta disponible.
     */
    public List<String> getProfesoresPorMateria(String materia) {
        List<String> result = new ArrayList<>();
        if (!disponible() || materia == null || materia.isBlank()) return result;

        try (Workbook wb = WorkbookFactory.create(new File(rutaDatosbase))) {
            Sheet sheet = wb.getSheet("ListasAsistencia");
            if (sheet == null) return result;

            Set<String> vistos = new LinkedHashSet<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String materiaRow = getCellStr(row, 2).trim();
                if (materia.equalsIgnoreCase(materiaRow)) {
                    String profesor = getCellStr(row, 1).trim();
                    if (!profesor.isEmpty()) vistos.add(profesor);
                }
            }
            result.addAll(vistos);
        } catch (IOException e) {
            System.err.println("[DatosbaseService] Error al leer Datosbase.xlsx: " + e.getMessage());
        }
        return result;
    }

    /** Lista de grupos unicos (Col 0: G, H, I) de la hoja ListasAsistencia. */
    public List<String> getGrupos() {
        return getDistinctFromAsistencia(0);
    }

    /**
     * Devuelve los grupos donde el profesor imparte la materia indicada.
     * Filtra: MATERIA == materia AND PROFESOR == profesor → valores distintos de LETRA (Col 0).
     */
    public List<String> getGruposPorMateriaYProfesor(String materia, String profesor) {
        List<String> result = new ArrayList<>();
        if (!disponible() || materia == null || profesor == null) return result;

        try (Workbook wb = WorkbookFactory.create(new File(rutaDatosbase))) {
            Sheet sheet = wb.getSheet("ListasAsistencia");
            if (sheet == null) return result;

            Set<String> vistos = new LinkedHashSet<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String materiaRow  = getCellStr(row, 2).trim();
                String profesorRow = getCellStr(row, 1).trim();
                if (materia.equalsIgnoreCase(materiaRow) && profesor.equalsIgnoreCase(profesorRow)) {
                    String grupo = getCellStr(row, 0).trim();
                    if (!grupo.isEmpty()) vistos.add(grupo);
                }
            }
            result.addAll(vistos);
        } catch (IOException e) {
            System.err.println("[DatosbaseService] Error al leer Datosbase.xlsx: " + e.getMessage());
        }
        return result;
    }

    // =========================================================================
    // Metodos para obtener datos de evaluacion
    // =========================================================================

    /**
     * Devuelve la lista de nombres de alumnos para la combinacion materia+grupo.
     * Filtra: LETRA == grupo  AND  MATERIA == materia (case-insensitive).
     * Retorna lista vacia si el archivo no existe o no hay coincidencias.
     */
    public List<String> getAlumnos(String materia, String grupo) {
        List<String> alumnos = new ArrayList<>();
        if (!disponible()) return alumnos;

        try (Workbook wb = WorkbookFactory.create(new File(rutaDatosbase))) {
            Sheet sheet = wb.getSheet("ListasAsistencia");
            if (sheet == null) return alumnos;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // fila 0 = encabezado
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String letra   = getCellStr(row, 0);
                String materiaRow = getCellStr(row, 2);
                if (grupo.equalsIgnoreCase(letra.trim())
                        && materia.equalsIgnoreCase(materiaRow.trim())) {
                    String alumno = getCellStr(row, 4).trim();
                    if (!alumno.isEmpty()) alumnos.add(alumno);
                }
            }
        } catch (IOException e) {
            System.err.println("[DatosbaseService] Error al leer Datosbase.xlsx: " + e.getMessage());
        }
        return alumnos;
    }

    /**
     * Devuelve la lista de atributos de egreso para una asignatura.
     * Usa hoja AsignaturaAtributo (sin encabezado).
     * Filtra: ASIGNATURA == materia (case-insensitive).
     */
    public List<String> getAtributos(String materia) {
        List<String> atributos = new ArrayList<>();
        if (!disponible()) return atributos;

        try (Workbook wb = WorkbookFactory.create(new File(rutaDatosbase))) {
            Sheet sheet = wb.getSheet("AsignaturaAtributo");
            if (sheet == null) return atributos;

            for (int i = 0; i <= sheet.getLastRowNum(); i++) { // sin encabezado
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String asig = getCellStr(row, 1).trim();
                if (materia.equalsIgnoreCase(asig)) {
                    String atributo = getCellStr(row, 2).trim();
                    if (!atributo.isEmpty()) atributos.add(atributo);
                }
            }
        } catch (IOException e) {
            System.err.println("[DatosbaseService] Error al leer Datosbase.xlsx: " + e.getMessage());
        }
        return atributos;
    }

    // =========================================================================
    // Helpers privados
    // =========================================================================

    /** Lee valores distintos de la columna indicada en ListasAsistencia (skipHeader=true). */
    private List<String> getDistinctFromAsistencia(int col) {
        List<String> result = new ArrayList<>();
        if (!disponible()) return result;

        try (Workbook wb = WorkbookFactory.create(new File(rutaDatosbase))) {
            Sheet sheet = wb.getSheet("ListasAsistencia");
            if (sheet == null) return result;

            Set<String> seen = new LinkedHashSet<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String val = getCellStr(row, col).trim();
                if (!val.isEmpty()) seen.add(val);
            }
            result.addAll(seen);
        } catch (IOException e) {
            System.err.println("[DatosbaseService] Error al leer Datosbase.xlsx: " + e.getMessage());
        }
        return result;
    }

    /** Extrae el valor de una celda como String, manejando tipos numericos y de texto. */
    private String getCellStr(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue();
            case NUMERIC: return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            default:      return "";
        }
    }
}
