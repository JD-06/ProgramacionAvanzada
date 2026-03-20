package sistemapos.modelo;

import sistemapos.persistencia.ArchivoCSV;
import java.util.ArrayList;

public class GestorProductos {

    private static final String ESTADO_ACTIVO  = "Activo";
    private static final String ESTADO_AGOTADO = "Agotado";

    private ArrayList<Producto> lista;
    private static int contadorId = 1;
    private String rutaArchivo;

    public GestorProductos() {
        this(ArchivoCSV.RUTA_POR_DEFECTO);
    }

    public GestorProductos(String rutaArchivoInicial) {
        rutaArchivo = rutaArchivoInicial;
        lista = ArchivoCSV.importarCSV(rutaArchivo);
        normalizarListaEstadosPorStock();
        recalcularContadorId();
    }

    private void recalcularContadorId() {
        contadorId = 1;
        for (Producto p : lista) {
            if (p.getId() >= contadorId) contadorId = p.getId() + 1;
        }
    }

    public void cargarDesdeCSV(String rutaArchivoNuevo) {
        rutaArchivo = rutaArchivoNuevo;
        lista = ArchivoCSV.importarCSV(rutaArchivo);
        normalizarListaEstadosPorStock();
        recalcularContadorId();
    }

    public String getRutaArchivo() { return rutaArchivo; }

    private void normalizarEstadoPorStock(Producto producto) {
        producto.setEstado(producto.getStock() > 0 ? ESTADO_ACTIVO : ESTADO_AGOTADO);
    }

    private void normalizarListaEstadosPorStock() {
        for (Producto p : lista) normalizarEstadoPorStock(p);
    }

    public boolean insertar(Producto p) {
        p.setId(contadorId++);
        normalizarEstadoPorStock(p);
        lista.add(p);
        ArchivoCSV.exportarCSV(lista, rutaArchivo);
        return true;
    }

    public Producto buscarPorId(int id) {
        for (Producto p : lista) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public ArrayList<Producto> buscarPorNombre(String nombre) {
        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto p : lista) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                resultado.add(p);
        }
        return resultado;
    }

    /**
     * Reemplaza el objeto en la lista por el actualizado (admite cambio de tipo).
     */
    public boolean actualizar(Producto actualizado) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == actualizado.getId()) {
                normalizarEstadoPorStock(actualizado);
                lista.set(i, actualizado);
                ArchivoCSV.exportarCSV(lista, rutaArchivo);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(int id) {
        return lista.removeIf(p -> {
            if (p.getId() == id) {
                ArchivoCSV.exportarCSV(lista, rutaArchivo);
                return true;
            }
            return false;
        });
    }

    public boolean existeCodigo(String codigo, int idExcluir) {
        for (Producto p : lista) {
            if (p.getCodigo().equalsIgnoreCase(codigo) && p.getId() != idExcluir)
                return true;
        }
        return false;
    }

    public boolean reducirStock(int idProducto, int cantidad) {
        Producto p = buscarPorId(idProducto);
        if (p != null && cantidad > 0 && p.getStock() >= cantidad) {
            p.setStock(p.getStock() - cantidad);
            normalizarEstadoPorStock(p);
            ArchivoCSV.exportarCSV(lista, rutaArchivo);
            return true;
        }
        return false;
    }

    public boolean hayStockSuficiente(int idProducto, int cantidad) {
        Producto p = buscarPorId(idProducto);
        return p != null && cantidad > 0 && p.getStock() >= cantidad;
    }

    public ArrayList<Producto> getLista() { return lista; }

    public ArrayList<Producto> getActivos() {
        ArrayList<Producto> activos = new ArrayList<>();
        for (Producto p : lista) {
            if (p.getStock() > 0 && !p.getEstado().equalsIgnoreCase(ESTADO_AGOTADO))
                activos.add(p);
        }
        return activos;
    }
}
