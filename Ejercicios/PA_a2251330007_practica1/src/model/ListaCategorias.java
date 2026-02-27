package model;

import java.util.ArrayList;
import javax.swing.JComboBox;

public class ListaCategorias {
    private ArrayList<Categoria> categorias;

    public ListaCategorias() {
        this.categorias = new ArrayList<>();
    }

    public void agregarCategoria(Categoria categoria) {
        if (buscarCategoriaPorId(categoria.getIdcategoria()) == null) {
            categorias.add(categoria);
        }
    }

    public void eliminarCategoriaPorId(String id) {
        Categoria categoria = buscarCategoriaPorId(id);
        if (categoria != null) {
            categorias.remove(categoria);
        }
    }

    public String toLineas() {
        StringBuilder resultado = new StringBuilder();
        for (Categoria categoria : categorias) {
            resultado.append(categoria.getIdcategoria()).append(",").append(categoria.getCategoria()).append("\n");
        }
        return resultado.toString();
    }

    public String toString() {
        StringBuilder resultado = new StringBuilder();
        for (Categoria categoria : categorias) {
            resultado.append(categoria.getIdcategoria()).append("\t\t").append(categoria.getCategoria()).append("\n");
        }
        return resultado.toString();
    }

    private Categoria buscarCategoriaPorId(String id) {
        for (Categoria categoria : categorias) {
            if (categoria.getIdcategoria().equals(id)) {
                return categoria;
            }
        }
        return null;
    }

    public String buscarCategoria(String id) {
        Categoria categoria = buscarCategoriaPorId(id);
        if (categoria != null) return categoria.getCategoria();
        return null;
    }

    public JComboBox<Categoria> agregarCategoriasAComboBox(JComboBox<Categoria> comboBox) {
        comboBox.removeAllItems();
        for (Categoria categoria : categorias)
            comboBox.addItem(categoria);
        return comboBox;
    }

    public Object[] CategoriasArreglo() {
        return this.categorias.toArray();
    }

    public String[] idcategorias() {
        String[] datos = new String[this.categorias.size()];
        for (int i = 0; i < categorias.size(); i++) {
            datos[i] = categorias.get(i).getIdcategoria();
        }
        return datos;
    }

    public void cargarCategorias(ArrayList<String[]> categoriasString) {
        for (String[] cat : categoriasString) {
            if (cat.length >= 2) {
                this.agregarCategoria(new Categoria(cat[0], cat[1]));
            }
        }
    }
}
