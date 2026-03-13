package sistemapos.modelo;

import sistemapos.persistencia.ArchivoAuxiliarCSV;
import java.util.ArrayList;

public class GestorClientes {

    private ArrayList<Cliente> lista;
    private int contadorId = 1;
    private String rutaArchivo;

    public GestorClientes() {
        this(ArchivoAuxiliarCSV.RUTA_SOCIOS);
    }

    public GestorClientes(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.lista = ArchivoAuxiliarCSV.importarClientes(rutaArchivo);
        recalcularContador();
    }

    private void recalcularContador() {
        contadorId = 1;
        for (Cliente c : lista) {
            if (c.getId() >= contadorId) {
                contadorId = c.getId() + 1;
            }
        }
    }

    public boolean insertar(Cliente cliente) {
        cliente.setId(contadorId++);
        lista.add(cliente);
        ArchivoAuxiliarCSV.exportarClientes(lista, rutaArchivo);
        return true;
    }

    public ArrayList<Cliente> getLista() {
        return lista;
    }
}
