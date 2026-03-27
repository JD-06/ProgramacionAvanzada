package com.tienda.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tienda.model.ItemVenta;
import com.tienda.model.Venta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Genera y guarda los tickets de venta en dos formatos:
 *   data/tickets/ticket_XXXX.txt  — ticket legible
 *   data/tickets/ticket_XXXX.json — ticket estructurado (Gson)
 */
public class TicketService {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String DIR = "data/tickets";
    private static final int  WIDTH = 44; // ancho del ticket TXT

    // ── API pública ──────────────────────────────────

    /**
     * Guarda el ticket en TXT y JSON. Devuelve la ruta base (sin extensión).
     * @param venta        venta finalizada
     * @param pagoRecibido dinero entregado por el cliente (0 si es tarjeta)
     */
    public static String save(Venta venta, double pagoRecibido) {
        new File(DIR).mkdirs();
        String nombre = String.format("ticket_%04d", venta.getFolio());
        String base   = DIR + "/" + nombre;

        saveTxt(venta, pagoRecibido, base + ".txt");
        saveJson(venta, pagoRecibido, base + ".json");

        return base;
    }

    // ── Formato TXT ──────────────────────────────────

    private static void saveTxt(Venta venta, double pagoRecibido, String path) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(path), StandardCharsets.UTF_8))) {

            String linea  = "─".repeat(WIDTH);
            String doble  = "═".repeat(WIDTH);

            pw.println(doble);
            pw.println(center("TIENDA DE ABARROTES", WIDTH));
            pw.println(center("Ticket de Venta", WIDTH));
            pw.println(doble);
            pw.printf("  Folio   : #%04d%n", venta.getFolio());
            pw.printf("  Fecha   : %s%n", venta.getFecha());
            pw.printf("  Método  : %s%n", venta.getMetodoPago());
            pw.println(linea);
            pw.printf("  %-4s  %-22s %4s  %7s%n", "SKU", "Nombre", "Cant", "Total");
            pw.println(linea);

            for (ItemVenta item : venta.getItems()) {
                String nombre = truncate(item.getProducto().getNombre(), 22);
                pw.printf("  %-4s  %-22s  %3d  $%6.2f%n",
                    truncate(item.getProducto().getSku(), 4),
                    nombre,
                    item.getCantidad(),
                    item.getSubtotal());
            }

            pw.println(linea);
            pw.printf("  %35s $%6.2f%n", "SUBTOTAL:", venta.getTotal());
            pw.printf("  %35s $%6.2f%n", "TOTAL:", venta.getTotal());
            if ("EFECTIVO".equalsIgnoreCase(venta.getMetodoPago())) {
                double cambio = Math.max(0, pagoRecibido - venta.getTotal());
                pw.printf("  %35s $%6.2f%n", "PAGO RECIBIDO:", pagoRecibido);
                pw.printf("  %35s $%6.2f%n", "CAMBIO:", cambio);
            }
            pw.println(doble);
            pw.println(center("¡Gracias por su compra!", WIDTH));
            pw.println(doble);

        } catch (Exception e) {
            System.err.println("[TicketService] Error al guardar TXT: " + e.getMessage());
        }
    }

    // ── Formato JSON (Gson) ──────────────────────────

    private static void saveJson(Venta venta, double pagoRecibido, String path) {
        TicketData data = new TicketData();
        data.folio       = venta.getFolio();
        data.fecha       = venta.getFecha();
        data.metodoPago  = venta.getMetodoPago();
        data.total       = venta.getTotal();
        data.subtotal    = venta.getTotal();
        data.pagoRecibido = pagoRecibido;
        data.cambio      = "EFECTIVO".equalsIgnoreCase(venta.getMetodoPago())
                           ? Math.max(0, pagoRecibido - venta.getTotal()) : 0;

        data.items = new ArrayList<>();
        for (ItemVenta item : venta.getItems()) {
            TicketData.ItemData id = new TicketData.ItemData();
            id.sku           = item.getProducto().getSku();
            id.nombre        = item.getProducto().getNombre();
            id.categoria     = item.getProducto().getCategoria();
            id.cantidad      = item.getCantidad();
            id.precioUnitario = item.getPrecioUnitario();
            id.subtotal      = item.getSubtotal();
            data.items.add(id);
        }

        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(path), StandardCharsets.UTF_8)) {
            GSON.toJson(data, writer);
        } catch (Exception e) {
            System.err.println("[TicketService] Error al guardar JSON: " + e.getMessage());
        }
    }

    // ── DTO para el ticket JSON ───────────────────────

    static class TicketData {
        int    folio;
        String fecha;
        String metodoPago;
        List<ItemData> items;
        double subtotal;
        double total;
        double pagoRecibido;
        double cambio;

        static class ItemData {
            String sku;
            String nombre;
            String categoria;
            int    cantidad;
            double precioUnitario;
            double subtotal;
        }
    }

    // ── Utilería ─────────────────────────────────────

    private static String center(String s, int width) {
        if (s.length() >= width) return s;
        int pad = (width - s.length()) / 2;
        return " ".repeat(pad) + s;
    }

    private static String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}
