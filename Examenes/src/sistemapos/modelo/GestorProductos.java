package sistemapos.modelo;

import sistemapos.persistencia.ArchivoCSV;
import java.util.ArrayList;
import java.util.Iterator;

public class GestorProductos {

    private ArrayList<Producto> lista;
    private static int contadorId = 1;

    public GestorProductos() {
        lista = ArchivoCSV.importarCSV();
        // Ajustar el contador al mayor ID existente
        for (Producto p : lista) {
            if (p.getId() >= contadorId) {
                contadorId = p.getId() + 1;
            }
        }
    }

    // ── CRUD ─────────────────────────────────────────────────────────────

    /** Insertar: asigna ID automático y agrega el producto */
    public boolean insertar(Producto p) {
        p.setId(contadorId++);
        lista.add(p);
        ArchivoCSV.exportarCSV(lista);
        return true;
    }

    /** Consultar: busca por ID */
    public Producto buscarPorId(int id) {
        for (Producto p : lista) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    /** Consultar: busca por nombre (parcial, sin mayúsculas) */
    public ArrayList<Producto> buscarPorNombre(String nombre) {
        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto p : lista) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /** Modificar: actualiza los datos de un producto existente */
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
                ArchivoCSV.exportarCSV(lista);
                return true;
            }
        }
        return false;
    }

    /** Eliminar: quita el producto por ID usando Iterator */
    public boolean eliminar(int id) {
        Iterator<Producto> it = lista.iterator();
        while (it.hasNext()) {
            Producto p = it.next();
            if (p.getId() == id) {
                it.remove();
                ArchivoCSV.exportarCSV(lista);
                return true;
            }
        }
        return false;
    }

    /** Verificar duplicado de código */
    public boolean existeCodigo(String codigo, int idExcluir) {
        for (Producto p : lista) {
            if (p.getCodigo().equalsIgnoreCase(codigo) && p.getId() != idExcluir) {
                return true;
            }
        }
        return false;
    }

    /** Reducir stock al procesar una venta */
    public boolean reducirStock(int idProducto, int cantidad) {
        Producto p = buscarPorId(idProducto);
        if (p != null && p.getStock() >= cantidad) {
            p.setStock(p.getStock() - cantidad);
            ArchivoCSV.exportarCSV(lista);
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
