package sistemapos.modelo;

import sistemapos.persistencia.ArchivoAuxiliarCSV;
import java.util.ArrayList;

public class GestorCajeros {

    private ArrayList<Cajero> lista;
    private int contadorId = 1;
    private String rutaArchivo;

    public GestorCajeros() {
        this(ArchivoAuxiliarCSV.RUTA_CAJEROS);
    }

    public GestorCajeros(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.lista = ArchivoAuxiliarCSV.importarCajeros(rutaArchivo);
        recalcularContador();
    }

    private void recalcularContador() {
        contadorId = 1;
        for (Cajero c : lista) {
            if (c.getId() >= contadorId) {
                contadorId = c.getId() + 1;
            }
        }
    }

    public boolean insertar(Cajero cajero) {
        cajero.setId(contadorId++);
        lista.add(cajero);
        ArchivoAuxiliarCSV.exportarCajeros(lista, rutaArchivo);
        return true;
    }

    public ArrayList<Cajero> getLista() {
        return lista;
    }
}
