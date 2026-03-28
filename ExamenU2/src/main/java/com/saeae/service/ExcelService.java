package com.saeae.service;

import com.saeae.model.AlumnoCotejo;
import com.saeae.model.CriterioEvaluacion;
import com.saeae.model.CriterioRubrica;
import com.saeae.model.DatosInstrumento;
import com.saeae.model.Evaluacion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Servicio de generacion de Excel para SAE-AE.
 *
 * Estrategia: cargar la plantilla examen.xlsx para preservar estilos y formato,
 * luego escribir SOLO valores calculados por Java — sin formulas de ningun tipo.
 *
 * Genera un archivo por evaluacion (Asignatura_Profesor_Grupo.xlsx).
 *
 * Hoja 1 — ReporteProductoIntegrador
 * Hoja 2 — RubricaProducto
 * Hoja 3 — ListaCotejoAtributo
 */
public class ExcelService {

    private String rutaPlantilla =
            "C:\\Users\\const\\Downloads\\examen.xlsx";

    public void setRutaPlantilla(String ruta) { this.rutaPlantilla = ruta; }
    public String getRutaPlantilla()           { return rutaPlantilla; }

    // =========================================================================
    // Punto de entrada
    // =========================================================================

    /**
     * Genera o sobreescribe Asignatura_Profesor_Grupo.xlsx con los datos de la evaluacion.
     * Usa la plantilla examen.xlsx como base para preservar el formato.
     *
     * @return Ruta absoluta del archivo generado.
     */
    public String generarOActualizarExcel(Evaluacion eval, String carpeta) throws IOException {
        new File(carpeta).mkdirs();

        String nombreArchivo = sanitize(eval.getAsignatura()) + "_"
                + sanitize(eval.getProfesor()) + "_"
                + sanitize(eval.getGrupo()) + ".xlsx";
        File destino = new File(carpeta, nombreArchivo);

        // Cargar la plantilla (copia fresca cada vez para no acumular datos previos)
        Workbook wb = cargarWorkbook();

        llenarPI(obtenerOCrearSheet(wb, "ReporteProductoIntegrador"), eval);
        llenarRubrica(obtenerOCrearSheet(wb, "RubricaProducto"), eval);
        llenarCotejo(obtenerOCrearSheet(wb, "ListaCotejoAtributo"), eval);

        try (FileOutputStream fos = new FileOutputStream(destino)) {
            wb.write(fos);
        }
        wb.close();

        return destino.getAbsolutePath();
    }

    // =========================================================================
    // Hoja 1 — ReporteProductoIntegrador
    // =========================================================================

    private void llenarPI(Sheet sheet, Evaluacion eval) {
        DatosInstrumento d = eval.getDatosInstrumento();
        if (d == null) return;

        // Encabezado
        // Fila 3 (idx 2): A3="Asignatura:", C3=asignatura, F3="Grupo", G3=grupo
        setStr(sheet, 2, 2, eval.getAsignatura());           // C3
        setStr(sheet, 2, 6, eval.getGrupo());                // G3
        // Fila 4 (idx 3): A4="Docente:", C4=profesor
        setStr(sheet, 3, 2, eval.getProfesor());             // C4
        // Fila 5 (idx 4): A5="Periodo:", C5=periodo, D5="Actividad", F5=actividad
        setStr(sheet, 4, 2, nvl(d.getPeriodo()));            // C5
        setStr(sheet, 4, 5, nvl(d.getActividad()));          // F5
        // Fila 6 (idx 5): A6="Atributo de egreso", C6=atributo
        setStr(sheet, 5, 2, nvl(d.getAtributo()));           // C6
        // Fila 7 (idx 6): A7="Criterio de desempenio", C7=criterio
        setStr(sheet, 6, 2, nvl(d.getCriterioDesempenio())); // C7

        // Filas 8-10 (idx 7-9): indicadores en C8, C9, C10
        List<String> indicadores = d.getIndicadores();
        for (int i = 0; i < 3; i++) {
            String ind = (indicadores != null && i < indicadores.size()) ? indicadores.get(i) : "";
            setStr(sheet, 7 + i, 2, ind); // C8, C9, C10
        }

        // Criterios: fila 18 a 23 (idx 17-22), calificacion en col F (5), observacion en col G (6)
        // Fila 17 (idx 16) es el encabezado: "Criterio de evaluacion / Cal. / Observaciones"
        // Fila 24 (idx 23) tiene AVERAGE(F18:F23) — la plantilla calcula el promedio automaticamente
        List<CriterioEvaluacion> criterios = d.getCriterios();
        for (int i = 0; i < 6; i++) {
            int fila = 17 + i; // filas 18-23 (idx 17-22)
            if (criterios != null && i < criterios.size()) {
                CriterioEvaluacion c = criterios.get(i);
                if (c.getCalificacion() > 0) {
                    setNum(sheet, fila, 5, c.getCalificacion()); // col F
                }
                if (!nvl(c.getObservacion()).isEmpty()) {
                    setStr(sheet, fila, 6, c.getObservacion()); // col G
                }
            }
        }
        // No se escribe el promedio: la plantilla tiene =AVERAGE(F18:F23) en F24

        // Subrayar nivel en G27-G30 segun promedio de criterios
        // G27=Excelente(>=9.5), G28=Bueno(>=8.5), G29=Regular(>=6.5), G30=NoAlcanza(<6.5)
        double promedio = calcularPromedio(criterios);
        if (promedio > 0) {
            int filaSubrayar = promedio >= 9.5 ? 26 : promedio >= 8.5 ? 27 : promedio >= 6.5 ? 28 : 29;
            for (int f = 26; f <= 29; f++) {
                Cell cell = obtenerOCrearCelda(sheet, f, 6);
                aplicarSubrayado(sheet.getWorkbook(), cell, f == filaSubrayar);
            }
        }
    }

    // =========================================================================
    // Hoja 2 — RubricaProducto  (hoja "maestra" del instrumento)
    // =========================================================================

    private void llenarRubrica(Sheet sheet, Evaluacion eval) {
        DatosInstrumento d = eval.getDatosInstrumento();
        if (d == null) return;

        // Encabezado
        // Fila 5 (idx 4): B5="Programa educativo:", E5=carrera (ya en plantilla), K5="Periodo:", M5=periodo
        setStr(sheet, 4, 12, nvl(d.getPeriodo()));             // M5 (col 12)
        // Fila 6 (idx 5): B6="Asignatura:", E6=asignatura
        setStr(sheet, 5,  4, eval.getAsignatura());            // E6
        // Fila 7 (idx 6): B7="Docente:", E7=profesor
        setStr(sheet, 6,  4, eval.getProfesor());              // E7
        // Fila 8 (idx 7): B8="Actividad:", E8=actividad, J8="Nivel de Atributo", L8=nivel
        setStr(sheet, 7,  4, nvl(d.getActividad()));           // E8
        setStr(sheet, 7, 11, nvl(d.getNivelDesempenio()));     // L8 (col 11)
        // Fila 9 (idx 8): B9="Atributo de egreso", E9=atributo
        setStr(sheet, 8,  4, nvl(d.getAtributo()));            // E9
        // Fila 10 (idx 9): B10="Criterio de desempenio", E10=criterio
        setStr(sheet, 9,  4, nvl(d.getCriterioDesempenio()));  // E10
        // Fila 11 (idx 10): B11="Indicadores", E11=indicador1
        List<String> indicadores = d.getIndicadores();
        for (int i = 0; i < 3; i++) {
            String ind = (indicadores != null && i < indicadores.size()) ? indicadores.get(i) : "";
            setStr(sheet, 10 + i, 4, ind); // E11, E12, E13
        }

        // Criterios rubrica: filas 16-22 (idx 15-21)
        // Fila 15 (idx 14) es el encabezado: "Criterio / Excelente 10 / Bueno 9 / Regular 8-7 / No alcanza 6-0"
        // Columnas de calificacion segun nivel:
        //   >= 9.5 -> G (col 6)  Excelente 10
        //   >= 8.5 -> I (col 8)  Bueno 9
        //   >= 6.5 -> K (col 10) Regular 8-7
        //   <  6.5 -> M (col 12) No Alcanza 6-0
        List<CriterioRubrica> criterios = eval.getCriteriosRubrica();
        for (int i = 0; i < 7; i++) {
            int fila = 15 + i; // filas 16-22 (idx 15-21)
            if (criterios != null && i < criterios.size()) {
                CriterioRubrica cr = criterios.get(i);
                setStr(sheet, fila, 1, nvl(cr.getDescripcion())); // col B
                double cal = cr.getCalificacion();
                if (cal > 0) {
                    int col = cal >= 9.5 ? 6 : cal >= 8.5 ? 8 : cal >= 6.5 ? 10 : 12;
                    setNum(sheet, fila, col, cal);
                }
            }
        }
        // No se escribe promedio: no hay formula de promedio definida para esta hoja en la plantilla
    }

    // =========================================================================
    // Hoja 3 — ListaCotejoAtributo
    // =========================================================================

    private void llenarCotejo(Sheet sheet, Evaluacion eval) {
        DatosInstrumento d = eval.getDatosInstrumento();
        if (d == null) return;

        // Encabezado
        // Fila 3 (idx 2): A3="Asignatura:", C3=asignatura, F3="Grupo", G3=grupo
        setStr(sheet, 2, 2, eval.getAsignatura());            // C3
        setStr(sheet, 2, 6, eval.getGrupo());                 // G3
        // Fila 4 (idx 3): A4="Docente:", C4=profesor
        setStr(sheet, 3, 2, eval.getProfesor());              // C4
        // Fila 5 (idx 4): A5="Periodo:", C5=periodo, D5="Nivel del Atributo", F5=nivel
        setStr(sheet, 4, 2, nvl(d.getPeriodo()));             // C5
        setStr(sheet, 4, 5, nvl(d.getNivelDesempenio()));     // F5
        // Fila 6 (idx 5): A6="Producto Integrador", C6=actividad
        setStr(sheet, 5, 2, nvl(d.getActividad()));           // C6
        // Fila 7 (idx 6): A7="Atributo de egreso", C7=atributo
        setStr(sheet, 6, 2, nvl(d.getAtributo()));            // C7
        // Fila 8 (idx 7): A8="Criterio de desempenio", C8=criterio
        setStr(sheet, 7, 2, nvl(d.getCriterioDesempenio()));  // C8

        // Filas 9-11 (idx 8-10): indicadores en C9, C10, C11
        List<String> indicadores = d.getIndicadores();
        for (int i = 0; i < 3; i++) {
            String ind = (indicadores != null && i < indicadores.size()) ? indicadores.get(i) : "";
            setStr(sheet, 8 + i, 2, ind); // C9, C10, C11
        }

        // Alumnos: filas 17-50 (idx 16-49), maximo 34 alumnos
        // Fila 13 (idx 12): A13="Alumno", D13="Escala de evaluacion"
        // Fila 14 (idx 13): D14="Excelente 10", E14="Bueno 9", F14="Regular 8-7", G14="No alcanza 6-0"
        // Filas 15-16 son separadores vacíos
        // Fila 16 (idx 15) = primer alumno  →  Fila 49 (idx 48) = alumno 34
        // Fila 51 (idx 50): A51="PROMEDIO FINAL" (ya en plantilla), G51=AVERAGEIF(D16:G50) (formula)
        // NO se escribe el promedio: la plantilla tiene su formula AVERAGEIF propia
        //
        // Columna de calificacion segun nivel:
        //   >= 9.5 -> D (col 3) Excelente 10
        //   >= 8.5 -> E (col 4) Bueno 9
        //   >= 6.5 -> F (col 5) Regular 8-7
        //   <  6.5 -> G (col 6) No Alcanza 6-0
        List<AlumnoCotejo> alumnos = (eval.getListaCotejo() != null)
                ? eval.getListaCotejo().getAlumnos() : null;

        if (alumnos != null) {
            int max = Math.min(alumnos.size(), 34);
            for (int i = 0; i < max; i++) {
                int fila = 15 + i; // filas 16-49 (idx 15-48)
                AlumnoCotejo a = alumnos.get(i);
                setStr(sheet, fila, 0, nvl(a.getNombre())); // col A
                double cal = a.getCalificacion();
                if (cal > 0) {
                    int col = cal >= 9.5 ? 3 : cal >= 8.5 ? 4 : cal >= 6.5 ? 5 : 6;
                    setNum(sheet, fila, col, cal);
                }
            }
        }
        // La celda A51 ya tiene "PROMEDIO FINAL" en la plantilla
        // La celda G51 ya tiene =AVERAGEIF(D16:G50,"<>""",D16:G50) en la plantilla
    }

    // =========================================================================
    // Helpers de escritura
    // =========================================================================

    private void setStr(Sheet sheet, int row, int col, String value) {
        Cell cell = obtenerOCrearCelda(sheet, row, col);
        if (cell.getCellType() == CellType.FORMULA) cell.setBlank();
        cell.setCellValue(value != null ? value : "");
    }

    private void setNum(Sheet sheet, int row, int col, double value) {
        Cell cell = obtenerOCrearCelda(sheet, row, col);
        if (cell.getCellType() == CellType.FORMULA) cell.setBlank();
        cell.setCellValue(value);
    }

    private Cell obtenerOCrearCelda(Sheet sheet, int row, int col) {
        Row r = sheet.getRow(row);
        if (r == null) r = sheet.createRow(row);
        Cell c = r.getCell(col);
        if (c == null) c = r.createCell(col);
        return c;
    }

    private Sheet obtenerOCrearSheet(Workbook wb, String nombre) {
        Sheet s = wb.getSheet(nombre);
        return s != null ? s : wb.createSheet(nombre);
    }

    // =========================================================================
    // Carga de plantilla
    // =========================================================================

    private Workbook cargarWorkbook() throws IOException {
        File plantilla = new File(rutaPlantilla);
        if (plantilla.exists()) {
            try (FileInputStream fis = new FileInputStream(plantilla)) {
                return WorkbookFactory.create(fis);
            }
        }
        System.err.println("[ExcelService] Plantilla no encontrada en: " + rutaPlantilla
                + ". Se crea workbook vacio.");
        return new XSSFWorkbook();
    }

    // =========================================================================
    // Utilidades
    // =========================================================================

    private String sanitize(String value) {
        return value != null
                ? value.replaceAll("[^a-zA-Z0-9]", "_").replaceAll("_+", "_").replaceAll("^_|_$", "")
                : "sin_nombre";
    }

    private String nvl(String value) {
        return value != null ? value : "";
    }

    private double calcularPromedio(List<CriterioEvaluacion> criterios) {
        if (criterios == null || criterios.isEmpty()) return 0;
        double suma = 0;
        int count = 0;
        for (CriterioEvaluacion c : criterios) {
            if (c.getCalificacion() > 0) {
                suma += c.getCalificacion();
                count++;
            }
        }
        return count > 0 ? suma / count : 0;
    }

    private void aplicarSubrayado(Workbook wb, Cell cell, boolean resaltar) {
        CellStyle newStyle = wb.createCellStyle();
        newStyle.cloneStyleFrom(cell.getCellStyle());
        if (resaltar) {
            newStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            newStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        } else {
            newStyle.setFillPattern(FillPatternType.NO_FILL);
        }
        cell.setCellStyle(newStyle);
    }
}
