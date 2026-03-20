package sistemapos.exportacion;

import sistemapos.modelo.Producto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Exporta datos del sistema a archivos Excel (.xlsx).
 * Requiere: poi-5.x.jar + poi-ooxml-5.x.jar en el classpath.
 *
 * Reportes:
 *  1. Listado completo de productos      → Exportaciones/Excel/productos.xlsx
 *  2. Productos agrupados por categoria  → Exportaciones/Excel/productos_por_categoria.xlsx
 */
public class ExportadorExcel {

    private static final String CARPETA = "Exportaciones/Excel/";

    // =========================================================
    // REPORTE 1: TODOS LOS PRODUCTOS
    // =========================================================

    public static String exportarProductos(ArrayList<Producto> productos) throws IOException {
        new File(CARPETA).mkdirs();
        String ruta = CARPETA + "productos.xlsx";

        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            Sheet hoja = wb.createSheet("Productos");
            CellStyle cabStyle = crearEstiloCabecera(wb);

            String[] cols = {"ID", "Codigo", "Nombre", "Categoria", "Tipo",
                             "Unidad Venta", "P.Compra", "P.Venta",
                             "Stock", "Stock Min.", "Estado", "Imagen"};
            crearCabecera(hoja, cabStyle, cols);

            int fila = 1;
            for (Producto p : productos) {
                Row row = hoja.createRow(fila++);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getCodigo());
                row.createCell(2).setCellValue(p.getNombre());
                row.createCell(3).setCellValue(p.getCategoria());
                row.createCell(4).setCellValue(p.getTipoProducto());
                row.createCell(5).setCellValue(p.getUnidadVenta());
                row.createCell(6).setCellValue(p.getPrecioCompra());
                row.createCell(7).setCellValue(p.getPrecioVenta());
                row.createCell(8).setCellValue(p.getStock());
                row.createCell(9).setCellValue(p.getStockMinimo());
                row.createCell(10).setCellValue(p.getEstado());
                row.createCell(11).setCellValue(p.getImagenRuta() != null ? p.getImagenRuta() : "");
            }

            for (int i = 0; i < cols.length; i++) hoja.autoSizeColumn(i);

            try (FileOutputStream fos = new FileOutputStream(ruta)) {
                wb.write(fos);
            }
        }
        return ruta;
    }

    // =========================================================
    // REPORTE 2: PRODUCTOS POR CATEGORIA (una hoja por categoria)
    // =========================================================

    public static String exportarProductosPorCategoria(ArrayList<Producto> productos) throws IOException {
        new File(CARPETA).mkdirs();
        String ruta = CARPETA + "productos_por_categoria.xlsx";

        // Agrupar por categoria
        Map<String, ArrayList<Producto>> agrupados = new LinkedHashMap<>();
        for (Producto p : productos) {
            String cat = p.getCategoria() != null ? p.getCategoria() : "Sin Categoria";
            agrupados.computeIfAbsent(cat, k -> new ArrayList<>()).add(p);
        }

        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            CellStyle cabStyle = crearEstiloCabecera(wb);
            String[] cols = {"ID", "Codigo", "Nombre", "Tipo", "Unidad",
                             "P.Venta", "Stock", "Estado"};

            for (Map.Entry<String, ArrayList<Producto>> entrada : agrupados.entrySet()) {
                // Nombre de hoja max 31 chars (limite de Excel)
                String nombreHoja = entrada.getKey();
                if (nombreHoja.length() > 31) nombreHoja = nombreHoja.substring(0, 31);

                Sheet hoja = wb.createSheet(nombreHoja);
                crearCabecera(hoja, cabStyle, cols);

                int fila = 1;
                for (Producto p : entrada.getValue()) {
                    Row row = hoja.createRow(fila++);
                    row.createCell(0).setCellValue(p.getId());
                    row.createCell(1).setCellValue(p.getCodigo());
                    row.createCell(2).setCellValue(p.getNombre());
                    row.createCell(3).setCellValue(p.getTipoProducto());
                    row.createCell(4).setCellValue(p.getUnidadVenta());
                    row.createCell(5).setCellValue(p.getPrecioVenta());
                    row.createCell(6).setCellValue(p.getStock());
                    row.createCell(7).setCellValue(p.getEstado());
                }

                for (int i = 0; i < cols.length; i++) hoja.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(ruta)) {
                wb.write(fos);
            }
        }
        return ruta;
    }

    // =========================================================
    // UTILIDADES
    // =========================================================

    private static void crearCabecera(Sheet hoja, CellStyle estilo, String[] columnas) {
        Row cabecera = hoja.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            Cell celda = cabecera.createCell(i);
            celda.setCellValue(columnas[i]);
            celda.setCellStyle(estilo);
        }
    }

    private static CellStyle crearEstiloCabecera(XSSFWorkbook wb) {
        CellStyle estilo = wb.createCellStyle();
        Font fuente = wb.createFont();
        fuente.setBold(true);
        estilo.setFont(fuente);
        estilo.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderTop(BorderStyle.THIN);
        return estilo;
    }
}
