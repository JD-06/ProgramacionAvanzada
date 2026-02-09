package modelo;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;


public class Mejercicio02 {
    
    
    private DefaultComboBoxModel<String> listacombo;
    private DefaultListModel<String> lista;
    
    private String[] equipos = {
        "Real Madrid",
        "Barcelona",
        "Manchester United",
        "Bayern Munich",
        "Juventus"
    };
    

    public Mejercicio02() {
        listacombo = new DefaultComboBoxModel<>();
        lista = new DefaultListModel<>();
        
        inicializarcombo();
        inicializarlistmodel();
    }
    
    

    private void inicializarcombo() {
        for (String equipo : equipos) {
            listacombo.addElement(equipo);
        }
    }
    

    private void inicializarlistmodel() {
        for (String equipo : equipos) {
            lista.addElement(equipo);
        }
    }
    
   
    public void agregarCombo(String info) {
        listacombo.addElement(info);
    }
    
    
    public void agregarLista(String info) {
        lista.addElement(info);
    }
    
   
    public String eliminarCombo(int pos1) {
        String elemento = listacombo.getElementAt(pos1);
        listacombo.removeElementAt(pos1);
        return elemento;
    }
    
   
    public String eliminarLista(int pos1) {
        String elemento = lista.getElementAt(pos1);
        lista.removeElementAt(pos1);
        return elemento;
    }
    
   
    public DefaultComboBoxModel<String> getListacombo() {
        return listacombo;
    }
    
   
    public DefaultListModel<String> getLista() {
        return lista;
    }
}
