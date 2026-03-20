package sistemapos.exportacion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import sistemapos.modelo.ItemCarrito;
import sistemapos.modelo.Producto;
import sistemapos.modelo.RegistroCompra;

import java.io.*;
import java.util.ArrayList;

/**
 * Exporta datos del sistema a archivos JSON usando la libreria Google GSON.
 *
 * Requiere gson-x.x.x.jar en la carpeta lib/ del proyecto.
 *
 * Archivos generados en "Exportaciones/":
 *  - productos.json              (catalogo completo)
 *  - Tickets/ticket_{folio}.json (un archivo por venta — nombre = folio)
 *  - Tickets/tickets.json        (registro maestro de todos los tickets)
 */
public class ExportadorJSON {

    private static final String CARPETA         = "Exportaciones/";
    private static final String CARPETA_TICKETS = "Exportaciones/Tickets/";
    private static final Gson   GSON            = new GsonBuilder().setPrettyPrinting().create();

    // =========================================================
    // EXPORTAR PRODUCTOS
    // =========================================================

    public static String exportarProductos(ArrayList<Producto> lista) {
        new File(CARPETA).mkdirs();
        String ruta = CARPETA + "productos.json";

        JsonArray array = new JsonArray();
        for (Producto p : lista) {
            JsonObject obj = new JsonObject();
            obj.addProperty("id",           p.getId());
            obj.addProperty("codigo",       p.getCodigo());
            obj.addProperty("nombre",       p.getNombre());
            obj.addProperty("descripcion",  p.getDescripcion());
            obj.addProperty("categoria",    p.getCategoria());
            obj.addProperty("tipo",         p.getTipoProducto());
            obj.addProperty("unidadVenta",  p.getUnidadVenta());
            obj.addProperty("precioCompra", p.getPrecioCompra());
            obj.addProperty("precioVenta",  p.getPrecioVenta());
            obj.addProperty("stock",        p.getStock());
            obj.addProperty("stockMinimo",  p.getStockMinimo());
            obj.addProperty("estado",       p.getEstado());
            obj.addProperty("imagenRuta",   p.getImagenRuta() != null ? p.getImagenRuta() : "");
            array.add(obj);
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            pw.print(GSON.toJson(array));
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
        return ruta;
    }

    // =========================================================
    // EXPORTAR TICKET DE VENTA
    // El nombre del archivo JSON es el folio (timestamp del ticket TXT)
    // =========================================================

    /**
     * Guarda un ticket individual y actualiza el archivo maestro tickets.json.
     *
     * @param registro    Datos del registro de compra (incluye nombre del archivo TXT)
     * @param carrito     Items del carrito en el momento del pago
     * @param subtotal    Subtotal sin IVA
     * @param iva         Monto del IVA
     * @return Ruta del archivo JSON generado
     */
    public static String exportarTicket(RegistroCompra registro,
                                        ArrayList<ItemCarrito> carrito,
                                        double subtotal, double iva) {
        new File(CARPETA_TICKETS).mkdirs();

        // Folio = nombre del ticket TXT sin extension
        String folio = registro.getTicket()
                               .replace("ticket_", "")
                               .replace(".txt", "");
        String ruta = CARPETA_TICKETS + "ticket_" + folio + ".json";

        // Construir items
        JsonArray items = new JsonArray();
        for (ItemCarrito item : carrito) {
            Producto p = item.getProducto();
            JsonObject obj = new JsonObject();
            obj.addProperty("codigo",         p.getCodigo());
            obj.addProperty("nombre",         p.getNombre());
            obj.addProperty("categoria",      p.getCategoria());
            obj.addProperty("tipo",           p.getTipoProducto());
            obj.addProperty("unidadVenta",    p.getUnidadVenta());
            obj.addProperty("cantidad",       item.getCantidad());
            obj.addProperty("precioUnitario", p.getPrecioVenta());
            obj.addProperty("subtotalItem",   round2(item.getTotal()));
            items.add(obj);
        }

        // Construir ticket
        JsonObject ticket = new JsonObject();
        ticket.addProperty("folio",    folio);
        ticket.addProperty("fecha",    registro.getFechaHora());
        ticket.addProperty("cliente",  registro.getCliente());
        ticket.addProperty("cajero",   registro.getCajero());
        ticket.add       ("items",     items);
        ticket.addProperty("subtotal", round2(subtotal));
        ticket.addProperty("iva",      round2(iva));
        ticket.addProperty("total",    round2(registro.getTotal()));

        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            pw.print(GSON.toJson(ticket));
        } catch (IOException e) {
            return "Error al guardar ticket JSON: " + e.getMessage();
        }

        actualizarMaestroTickets(registro, folio, ruta);
        return ruta;
    }

    // =========================================================
    // MAESTRO tickets.json
    // =========================================================

    private static void actualizarMaestroTickets(RegistroCompra registro,
                                                  String folio, String rutaTicket) {
        String rutaMaestro = CARPETA_TICKETS + "tickets.json";

        // Leer array existente o crear uno nuevo
        JsonArray maestro = new JsonArray();
        File archivo = new File(rutaMaestro);
        if (archivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                StringBuilder sb = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) sb.append(linea);
                JsonArray existente = GSON.fromJson(sb.toString(), JsonArray.class);
                if (existente != null) maestro = existente;
            } catch (Exception ignored) {}
        }

        // Agregar nueva entrada
        JsonObject entrada = new JsonObject();
        entrada.addProperty("folio",   folio);
        entrada.addProperty("fecha",   registro.getFechaHora());
        entrada.addProperty("cliente", registro.getCliente());
        entrada.addProperty("cajero",  registro.getCajero());
        entrada.addProperty("total",   round2(registro.getTotal()));
        entrada.addProperty("archivo", rutaTicket);
        maestro.add(entrada);

        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaMaestro))) {
            pw.print(GSON.toJson(maestro));
        } catch (IOException e) {
            System.err.println("Error actualizando tickets.json: " + e.getMessage());
        }
    }

    // =========================================================
    // UTILIDADES
    // =========================================================

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
