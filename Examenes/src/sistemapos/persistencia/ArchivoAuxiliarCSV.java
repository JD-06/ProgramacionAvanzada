package sistemapos.persistencia;

import sistemapos.modelo.Cajero;
import sistemapos.modelo.Cliente;
import sistemapos.modelo.RegistroCompra;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ArchivoAuxiliarCSV {

    public static final String RUTA_SOCIOS = "socios.csv";
    public static final String RUTA_CAJEROS = "cajeros.csv";
    public static final String RUTA_COMPRAS_SOCIOS = "compras_socios.csv";
    public static final String RUTA_COMPRAS_NO_CLIENTES = "compras_no_clientes.csv";

    private static void asegurarArchivo(String ruta) {
        File archivo = new File(ruta);
        File carpeta = archivo.getParentFile();
        try {
            if (carpeta != null && !carpeta.exists()) {
                carpeta.mkdirs();
            }
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al preparar archivo CSV: " + e.getMessage());
        }
    }

    public static void exportarClientes(ArrayList<Cliente> lista, String ruta) {
        asegurarArchivo(ruta);
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (Cliente c : lista) {
                pw.println(c.toCsv());
            }
        } catch (IOException e) {
            System.err.println("Error al exportar clientes: " + e.getMessage());
        }
    }

    public static ArrayList<Cliente> importarClientes(String ruta) {
        ArrayList<Cliente> lista = new ArrayList<>();
        asegurarArchivo(ruta);
        File archivo = new File(ruta);

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] campos = linea.split(",", -1);
                if (campos.length < 3) continue;
                try {
                    int id = Integer.parseInt(campos[0].trim());
                    String nombre = campos[1].trim().replace(";", ",");
                    boolean socio = Boolean.parseBoolean(campos[2].trim());
                    lista.add(new Cliente(id, nombre, socio));
                } catch (NumberFormatException ex) {
                    System.err.println("Linea de cliente invalida: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al importar clientes: " + e.getMessage());
        }
        return lista;
    }

    public static void exportarCajeros(ArrayList<Cajero> lista, String ruta) {
        asegurarArchivo(ruta);
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (Cajero c : lista) {
                pw.println(c.toCsv());
            }
        } catch (IOException e) {
            System.err.println("Error al exportar cajeros: " + e.getMessage());
        }
    }

    public static ArrayList<Cajero> importarCajeros(String ruta) {
        ArrayList<Cajero> lista = new ArrayList<>();
        asegurarArchivo(ruta);
        File archivo = new File(ruta);

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] campos = linea.split(",", -1);
                if (campos.length < 2) continue;
                try {
                    int id = Integer.parseInt(campos[0].trim());
                    String nombre = campos[1].trim().replace(";", ",");
                    lista.add(new Cajero(id, nombre));
                } catch (NumberFormatException ex) {
                    System.err.println("Linea de cajero invalida: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al importar cajeros: " + e.getMessage());
        }
        return lista;
    }

    public static void exportarCompras(ArrayList<RegistroCompra> lista, String ruta) {
        asegurarArchivo(ruta);
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (RegistroCompra r : lista) {
                pw.println(r.toCsv());
            }
        } catch (IOException e) {
            System.err.println("Error al exportar compras: " + e.getMessage());
        }
    }

    public static ArrayList<RegistroCompra> importarCompras(String ruta) {
        ArrayList<RegistroCompra> lista = new ArrayList<>();
        asegurarArchivo(ruta);
        File archivo = new File(ruta);

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] campos = linea.split(",", -1);
                if (campos.length < 5) continue;
                try {
                    String fecha = campos[0].trim().replace(";", ",");
                    String ticket = campos[1].trim().replace(";", ",");
                    String cliente = campos[2].trim().replace(";", ",");
                    String cajero = campos[3].trim().replace(";", ",");
                    double total = Double.parseDouble(campos[4].trim());
                    lista.add(new RegistroCompra(fecha, ticket, cliente, cajero, total));
                } catch (NumberFormatException ex) {
                    System.err.println("Linea de compra invalida: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al importar compras: " + e.getMessage());
        }
        return lista;
    }
}
