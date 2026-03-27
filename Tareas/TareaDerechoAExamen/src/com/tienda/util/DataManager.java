package com.tienda.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.tienda.model.Producto;
import com.tienda.model.Proveedor;
import com.tienda.model.Venta;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la lectura/escritura de JSON (Gson) y CSV (OpenCSV).
 * El modo activo se configura en config/config.txt con la clave modo_lectura=json|csv.
 * Siempre se escribe en ambos formatos para mantener sincronía.
 */
public class DataManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // ═══════════════════════════════════════════════════
    //  PRODUCTOS
    // ═══════════════════════════════════════════════════

    public static List<Producto> loadProductos() {
        String mode = Config.get("modo_lectura");
        List<Producto> lista = new ArrayList<>();
        if (!"csv".equalsIgnoreCase(mode)) {
            lista = loadProductosJSON();
        }
        if (lista.isEmpty()) {
            lista = loadProductosCSV();
        }
        return lista;
    }

    // ── JSON (Gson) ──────────────────────────────────

    private static List<Producto> loadProductosJSON() {
        File file = new File("data/productos.json");
        if (!file.exists()) return new ArrayList<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            Type type = new TypeToken<List<Producto>>() {}.getType();
            List<Producto> list = GSON.fromJson(reader, type);
            return list != null ? list : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("[DataManager] Error al leer productos.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void saveProductosJSON(List<Producto> list) {
        File file = new File("data/productos.json");
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            GSON.toJson(list, writer);
        } catch (Exception e) {
            System.err.println("[DataManager] Error al guardar productos.json: " + e.getMessage());
        }
    }

    // ── CSV (OpenCSV) ────────────────────────────────

    private static List<Producto> loadProductosCSV() {
        File file = new File("data/productos.csv");
        if (!file.exists()) return new ArrayList<>();
        List<Producto> list = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))
                .withSkipLines(1) // omitir encabezado
                .build()) {
            String[] row;
            while ((row = reader.readNext()) != null) {
                if (row.length < 9) continue;
                Producto p = new Producto();
                p.setSku(row[0]);
                p.setNombre(row[1]);
                p.setCategoria(row[2]);
                p.setPrecioCompra(parseD(row[3]));
                p.setPorcentajeGanancia(parseD(row[4]));
                p.setPrecioVenta(parseD(row[5]));
                p.setStock((int) parseD(row[6]));
                p.setUnidad(row[7]);
                p.setImagenPath(row[8]);
                list.add(p);
            }
        } catch (Exception e) {
            System.err.println("[DataManager] Error al leer productos.csv: " + e.getMessage());
        }
        return list;
    }

    private static void saveProductosCSV(List<Producto> list) {
        File file = new File("data/productos.csv");
        try (CSVWriter writer = new CSVWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.writeNext(new String[]{
                "sku", "nombre", "categoria", "precio_compra",
                "porcentaje_ganancia", "precio_venta", "stock", "unidad", "imagenPath"
            });
            for (Producto p : list) {
                writer.writeNext(new String[]{
                    p.getSku(), p.getNombre(), p.getCategoria(),
                    String.valueOf(p.getPrecioCompra()),
                    String.valueOf(p.getPorcentajeGanancia()),
                    String.valueOf(p.getPrecioVenta()),
                    String.valueOf(p.getStock()),
                    p.getUnidad(),
                    p.getImagenPath() == null ? "" : p.getImagenPath()
                });
            }
        } catch (Exception e) {
            System.err.println("[DataManager] Error al guardar productos.csv: " + e.getMessage());
        }
    }

    /** Guarda en JSON y CSV simultáneamente. */
    public static void saveProductos(List<Producto> list) {
        saveProductosJSON(list);
        saveProductosCSV(list);
    }

    // ═══════════════════════════════════════════════════
    //  PROVEEDORES
    // ═══════════════════════════════════════════════════

    public static List<Proveedor> loadProveedores() {
        List<Proveedor> lista = loadProveedoresJSON();
        if (lista.isEmpty()) lista = loadProveedoresCSV();
        return lista;
    }

    private static List<Proveedor> loadProveedoresJSON() {
        File file = new File("data/proveedores.json");
        if (!file.exists()) return new ArrayList<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            Type type = new TypeToken<List<Proveedor>>() {}.getType();
            List<Proveedor> list = GSON.fromJson(reader, type);
            return list != null ? list : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("[DataManager] Error al leer proveedores.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static List<Proveedor> loadProveedoresCSV() {
        File file = new File("data/proveedores.csv");
        if (!file.exists()) return new ArrayList<>();
        List<Proveedor> list = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))
                .withSkipLines(1).build()) {
            String[] row;
            while ((row = reader.readNext()) != null) {
                if (row.length < 7) continue;
                Proveedor p = new Proveedor(row[0], row[1], row[2], row[3], row[4], row[5], row[6]);
                list.add(p);
            }
        } catch (Exception e) {
            System.err.println("[DataManager] Error al leer proveedores.csv: " + e.getMessage());
        }
        return list;
    }

    public static void saveProveedores(List<Proveedor> list) {
        // JSON
        File jsonFile = new File("data/proveedores.json");
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(jsonFile), StandardCharsets.UTF_8)) {
            GSON.toJson(list, writer);
        } catch (Exception e) {
            System.err.println("[DataManager] Error al guardar proveedores.json: " + e.getMessage());
        }
        // CSV
        File csvFile = new File("data/proveedores.csv");
        try (CSVWriter writer = new CSVWriter(
                new OutputStreamWriter(new FileOutputStream(csvFile), StandardCharsets.UTF_8))) {
            writer.writeNext(new String[]{"id","nombre","contacto","telefono","email","categoria","direccion"});
            for (Proveedor p : list) {
                writer.writeNext(new String[]{
                    p.getId(), p.getNombre(), p.getContacto(),
                    p.getTelefono(), p.getEmail(), p.getCategoria(), p.getDireccion()
                });
            }
        } catch (Exception e) {
            System.err.println("[DataManager] Error al guardar proveedores.csv: " + e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════
    //  VENTAS (historial ligero — sin items)
    // ═══════════════════════════════════════════════════

    public static List<Venta> loadVentas() {
        File file = new File("data/ventas.json");
        if (!file.exists()) return new ArrayList<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            Type type = new TypeToken<List<VentaResumen>>() {}.getType();
            List<VentaResumen> resumenes = GSON.fromJson(reader, type);
            if (resumenes == null) return new ArrayList<>();
            List<Venta> ventas = new ArrayList<>();
            for (VentaResumen r : resumenes) {
                ventas.add(new Venta(r.folio, r.fecha, r.total, r.metodoPago));
            }
            return ventas;
        } catch (Exception e) {
            System.err.println("[DataManager] Error al leer ventas.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void saveVentas(List<Venta> ventas) {
        // Convertir a resúmenes para no serializar items con referencias a Producto
        List<VentaResumen> resumenes = new ArrayList<>();
        for (Venta v : ventas) {
            resumenes.add(new VentaResumen(v.getFolio(), v.getFecha(), v.getTotal(), v.getMetodoPago()));
        }
        File file = new File("data/ventas.json");
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            GSON.toJson(resumenes, writer);
        } catch (Exception e) {
            System.err.println("[DataManager] Error al guardar ventas.json: " + e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════
    //  CLASES AUXILIARES INTERNAS
    // ═══════════════════════════════════════════════════

    /** DTO ligero para persistir el historial de ventas. */
    static class VentaResumen {
        int    folio;
        String fecha;
        double total;
        String metodoPago;

        VentaResumen(int folio, String fecha, double total, String metodoPago) {
            this.folio      = folio;
            this.fecha      = fecha;
            this.total      = total;
            this.metodoPago = metodoPago;
        }
    }

    // ── Utilería ─────────────────────────────────────

    private static double parseD(String s) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return 0; }
    }
}
