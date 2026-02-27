package model;

import java.util.ArrayList;

public class ListaInsumos {
    private ArrayList<Insumo> insumos;

    public ListaInsumos() {
        this.insumos = new ArrayList<>();
    }

    public boolean agregarInsumo(Insumo insumo) {
        if (buscarInsumoPorId(insumo.getIdProducto()) == null) {
            insumos.add(insumo);
            return true;
        }
        return false;
    }

    public boolean eliminarInsumoPorId(String id) {
        Insumo insumo = buscarInsumoPorId(id);
        if (insumo != null) {
            insumos.remove(insumo);
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder resultado = new StringBuilder();
        for (Insumo insumo : insumos) {
            resultado.append(insumo.toString()).append("\n");
        }
        return resultado.toString();
    }

    public String toLineas() {
        StringBuilder resultado = new StringBuilder();
        for (Insumo insumo : insumos) {
            resultado.append(insumo.getIdProducto()).append(",")
                     .append(insumo.getProducto()).append(",")
                     .append(insumo.getIdCategoria()).append("\n");
        }
        return resultado.toString();
    }

    private Insumo buscarInsumoPorId(String id) {
        for (Insumo insumo : insumos) {
            if (insumo.getIdProducto().equals(id)) {
                return insumo;
            }
        }
        return null;
    }

    public String buscarInsumo(String id) {
        for (Insumo insumo : insumos)
            if (insumo.getIdProducto().equals(id))
                return insumo.getProducto();
        return null;
    }

    public String[] idinsumos() {
        String[] datos = new String[this.insumos.size()];
        for (int i = 0; i < insumos.size(); i++) {
            datos[i] = insumos.get(i).getIdProducto();
        }
        return datos;
    }

    public void cargarInsumo(ArrayList<String[]> insumosString) {
        for (String[] ins : insumosString) {
            if (ins.length >= 3) {
                this.agregarInsumo(new Insumo(ins[0], ins[1], ins[2]));
            }
        }
    }
}
