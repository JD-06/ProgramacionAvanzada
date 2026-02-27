package controller;

import model.*;
import view.Practica03bView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Practica03bController implements ActionListener {
    private Practica03bView vista;
    private ListaCategorias modeloCategorias;

    public Practica03bController(Practica03bView vista) {
        this.vista = vista;
        this.modeloCategorias = new ListaCategorias();

        this.vista.Bagregar.addActionListener(this);
        this.vista.Beliminar.addActionListener(this);
        this.vista.Bsalir.addActionListener(this);
    }

    public void volverAlInicio() {
        vista.Bagregar.setText("Agregar");
        vista.Bsalir.setText("Salir");
        vista.Beliminar.setEnabled(true);
        vista.Tid.setEditable(false);
        vista.Tcategoria.setEditable(false);
        vista.Tid.setText("");
        vista.Tcategoria.setText("");
    }

    private void altas() {
        if (vista.Bagregar.getText().equals("Agregar")) {
            vista.Bagregar.setText("Salvar");
            vista.Bsalir.setText("Cancelar");
            vista.Beliminar.setEnabled(false);
            vista.Tid.setEditable(true);
            vista.Tcategoria.setEditable(true);
        } else {
            String id = vista.Tid.getText().trim();
            String cat = vista.Tcategoria.getText().trim();
            if (!id.isEmpty() && !cat.isEmpty()) {
                modeloCategorias.agregarCategoria(new Categoria(id, cat));
                vista.Tareacategoria.setText(modeloCategorias.toString());
                volverAlInicio();
            }
        }
    }

    private void eliminar() {
        String[] opciones = modeloCategorias.idcategorias();
        if (opciones.length == 0) return;
        String id = (String) JOptionPane.showInputDialog(vista, "Seleccione ID:", "Eliminar", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        if (id != null && !id.isEmpty()) {
            modeloCategorias.eliminarCategoriaPorId(id);
            vista.Tareacategoria.setText(modeloCategorias.toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.Bagregar) altas();
        else if (e.getSource() == vista.Beliminar) eliminar();
        else if (e.getSource() == vista.Bsalir) {
            if (vista.Bsalir.getText().equals("Cancelar")) volverAlInicio();
            else java.awt.Container p = vista.Bsalir.getParent(); while(p!=null && !(p instanceof javax.swing.JInternalFrame) && !(p instanceof java.awt.Window)) p = p.getParent(); if(p instanceof javax.swing.JInternalFrame) ((javax.swing.JInternalFrame)p).dispose(); else if(p instanceof java.awt.Window) ((java.awt.Window)p).dispose(); else vista.dispose();
        }
    }
}

