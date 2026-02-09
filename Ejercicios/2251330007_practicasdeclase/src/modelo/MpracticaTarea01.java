package modelo;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

public class MpracticaTarea01 {
  
    private DefaultComboBoxModel<String> modeloCategoria;
    private DefaultListModel<Insumo> modeloLista;
    private ListaInsumos listaInsumos;

    private String[] categorias = {
        "Materiales",
        "Mano de Obra",
        "Herramienta",
        "Servicios"
    };
    

    public MpracticaTarea01() {
        modeloCategoria = new DefaultComboBoxModel<>();
        modeloLista = new DefaultListModel<>();
        listaInsumos = new ListaInsumos();
        
        inicializarCategorias();
    }
    

    private void inicializarCategorias() {
        for (String cat : categorias) {
            modeloCategoria.addElement(cat);
        }
    }
    

    public void agregarInsumo(String nombreInsumo, String categoria) {
        listaInsumos.insertar(nombreInsumo, categoria);
        actualizarListaVisual();
    }
    

    public boolean eliminarInsumo(int indice) {
        boolean resultado = listaInsumos.eliminarPorIndice(indice);
        if (resultado) {
            actualizarListaVisual();
        }
        return resultado;
    }
    

    private void actualizarListaVisual() {
        modeloLista.clear();
        for (Insumo i : listaInsumos.obtenerTodos()) {
            modeloLista.addElement(i);
        }
    }
    
 
    public Insumo obtenerInsumoPorIndice(int indice) {
        return listaInsumos.obtenerPorIndice(indice);
    }
    

    public DefaultComboBoxModel<String> getModeloCategoria() {
        return modeloCategoria;
    }

    public DefaultListModel<Insumo> getModeloLista() {
        return modeloLista;
    }

    public ListaInsumos getListaInsumos() {
        return listaInsumos;
    }
}
