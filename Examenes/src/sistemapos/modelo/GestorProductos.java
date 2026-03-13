package sistemapos.modelo;

import sistemapos.persistencia.ArchivoCSV;
import java.util.ArrayList;
import java.util.Iterator;

public class GestorProductos {

    private ArrayList<Producto> lista;
    private static int contadorId = 1;
    private String rutaArchivo;

    public GestorProductos() {
        this(ArchivoCSV.RUTA_POR_DEFECTO);
    }

    public GestorProductos(String rutaArchivoInicial) {
        rutaArchivo = rutaArchivoInicial;
        lista = ArchivoCSV.importarCSV(rutaArchivo);
        recalcularContadorId();
    }

    private void recalcularContadorId() {
        contadorId = 1;
        for (Producto p : lista) {
            if (p.getId() >= contadorId) {
                contadorId = p.getId() + 1;
            }
        }
    }

    public void cargarDesdeCSV(String rutaArchivoNuevo) {
        rutaArchivo = rutaArchivoNuevo;
        lista = ArchivoCSV.importarCSV(rutaArchivo);
        recalcularContadorId();
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public boolean insertar(Producto p) {
        p.setId(contadorId++);
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
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    
    public boolean actualizar(Producto actualizado) {
        Iterator<Producto> it = lista.iterator();
        while (it.hasNext()) {
            Producto p = it.next();
            if (p.getId() == actualizado.getId()) {
                p.setCodigo(actualizado.getCodigo());
                p.setNombre(actualizado.getNombre());
                p.setDescripcion(actualizado.getDescripcion());
                p.setCategoria(actualizado.getCategoria());
                p.setPrecioCompra(actualizado.getPrecioCompra());
                p.setPrecioVenta(actualizado.getPrecioVenta());
                p.setStock(actualizado.getStock());
                p.setStockMinimo(actualizado.getStockMinimo());
                p.setEstado(actualizado.getEstado());
                ArchivoCSV.exportarCSV(lista, rutaArchivo);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(int id) {
        Iterator<Producto> it = lista.iterator();
        while (it.hasNext()) {
            Producto p = it.next();
            if (p.getId() == id) {
                it.remove();
                ArchivoCSV.exportarCSV(lista, rutaArchivo);
                return true;
            }
        }
        return false;
    }

    public boolean existeCodigo(String codigo, int idExcluir) {
        for (Producto p : lista) {
            if (p.getCodigo().equalsIgnoreCase(codigo) && p.getId() != idExcluir) {
                return true;
            }
        }
        return false;
    }

    public boolean reducirStock(int idProducto, int cantidad) {
        Producto p = buscarPorId(idProducto);
        if (p != null && p.getStock() >= cantidad) {
            p.setStock(p.getStock() - cantidad);
            ArchivoCSV.exportarCSV(lista, rutaArchivo);
            return true;
        }
        return false;
    }

    public ArrayList<Producto> getLista()          { return lista; }

    public ArrayList<Producto> getActivos() {
        ArrayList<Producto> activos = new ArrayList<>();
        for (Producto p : lista) {
            if (p.getEstado().equalsIgnoreCase("Activo")) activos.add(p);
        }
        return activos;
    }
}
