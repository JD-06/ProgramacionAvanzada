package sistemapos.persistencia;

import sistemapos.modelo.Producto;
import java.io.*;
import java.util.ArrayList;

/**
 * Persistencia de productos en CSV.
 *
 * Formato extendido (12 campos):
 * id,codigo,nombre,descripcion,categoria,precioCompra,precioVenta,
 * stock,stockMinimo,estado,tipo,imagenRuta
 *
 * Compatible con archivos antiguos de 10 campos (tipo e imagen se omiten → Unitario/"").
 */
public class ArchivoCSV {

    public static final String RUTA_POR_DEFECTO = "productos.csv";

    public static void exportarCSV(ArrayList<Producto> lista) {
        exportarCSV(lista, RUTA_POR_DEFECTO);
    }

    public static void exportarCSV(ArrayList<Producto> lista, String ruta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (Producto p : lista) {
                pw.println(p.toString()); // incluye tipo e imagenRuta
            }
        } catch (IOException e) {
            System.err.println("Error al exportar CSV: " + e.getMessage());
        }
    }

    public static ArrayList<Producto> importarCSV() {
        return importarCSV(RUTA_POR_DEFECTO);
    }

    public static ArrayList<Producto> importarCSV(String ruta) {
        ArrayList<Producto> lista = new ArrayList<>();
        File archivo = new File(ruta);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] c = linea.split(",");
                if (c.length < 10) continue; // linea invalida
                try {
                    int    id       = Integer.parseInt(c[0].trim());
                    String codigo   = c[1].trim();
                    String nombre   = c[2].trim();
                    String desc     = c[3].trim().replace(";", ",");
                    String cat      = c[4].trim();
                    double precCom  = Double.parseDouble(c[5].trim());
                    double precVen  = Double.parseDouble(c[6].trim());
                    int    stock    = Integer.parseInt(c[7].trim());
                    int    stockMin = Integer.parseInt(c[8].trim());
                    String estado   = c[9].trim();

                    // Campos nuevos (compatibilidad con archivos viejos)
                    String tipo   = c.length > 10 ? c[10].trim() : "Unitario";
                    String imagen = c.length > 11 ? c[11].trim().replace(";", ",") : "";

                    Producto p = Producto.crear(tipo, id, codigo, nombre, desc,
                                               cat, precCom, precVen,
                                               stock, stockMin, estado);
                    p.setImagenRuta(imagen);
                    lista.add(p);
                } catch (NumberFormatException e) {
                    System.err.println("Linea mal formada, se omite: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al importar CSV: " + e.getMessage());
        }
        return lista;
    }
}
