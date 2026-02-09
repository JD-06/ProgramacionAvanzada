package modelo;

import java.util.ArrayList;


public class ListaInsumos {
    private ArrayList<Insumo> insumos;
    private int contadorId;

    public ListaInsumos() {
        this.insumos = new ArrayList<>();
        this.contadorId = 1;
    }
    

    public void insertar(String nombreInsumo, String categoria) {
        Insumo nuevoInsumo = new Insumo(contadorId, nombreInsumo, categoria);
        insumos.add(nuevoInsumo);
        contadorId++;
    }
    

    public ArrayList<Insumo> obtenerTodos() {
        return new ArrayList<>(insumos);
    }

    public Insumo buscarPorId(int id) {
        for (Insumo i : insumos) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
    

    public Insumo obtenerPorIndice(int indice) {
        if (indice >= 0 && indice < insumos.size()) {
            return insumos.get(indice);
        }
        return null;
    }
    

    public boolean actualizar(int id, String nuevoNombre, String nuevaCategoria) {
        Insumo insumo = buscarPorId(id);
        if (insumo != null) {
            insumo.setInsumo(nuevoNombre);
            insumo.setCategoria(nuevaCategoria);
            return true;
        }
        return false;
    }

    public boolean eliminar(int id) {
        Insumo insumo = buscarPorId(id);
        if (insumo != null) {
            insumos.remove(insumo);
            return true;
        }
        return false;
    }

    public boolean eliminarPorIndice(int indice) {
        if (indice >= 0 && indice < insumos.size()) {
            insumos.remove(indice);
            return true;
        }
        return false;
    }

    public int cantidad() {
        return insumos.size();
    }
    

    public boolean estaVacia() {
        return insumos.isEmpty();
    }
}
