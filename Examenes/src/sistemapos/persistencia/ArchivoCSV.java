package sistemapos.persistencia;

import sistemapos.modelo.Producto;
import java.io.*;
import java.util.ArrayList;

public class ArchivoCSV {

    public static final String RUTA_POR_DEFECTO = "productos.csv";

    public static void exportarCSV(ArrayList<Producto> lista) {
        exportarCSV(lista, RUTA_POR_DEFECTO);
    }

    public static void exportarCSV(ArrayList<Producto> lista, String ruta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (Producto p : lista) {
                pw.println(p.toString());
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
                String[] campos = linea.split(",");
                if (campos.length < 10) continue;
                try {
                    int id            = Integer.parseInt(campos[0].trim());
                    String codigo     = campos[1].trim();
                    String nombre     = campos[2].trim();
                    String descripcion= campos[3].trim().replace(";", ",");
                    String categoria  = campos[4].trim();
                    double precCom    = Double.parseDouble(campos[5].trim());
                    double precVen    = Double.parseDouble(campos[6].trim());
                    int stock         = Integer.parseInt(campos[7].trim());
                    int stockMin      = Integer.parseInt(campos[8].trim());
                    String estado     = campos[9].trim();
                    lista.add(new Producto(id, codigo, nombre, descripcion,
                                          categoria, precCom, precVen,
                                          stock, stockMin, estado));
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
