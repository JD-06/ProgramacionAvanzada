package controller;

import model.*;
import view.Practica03View;
import utils.Archivotxt;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Practica03dController implements ActionListener {
    private Practica03View vista;
    private ListaInsumos modeloInsumos;
    private ListaCategorias modeloCategorias;
    private Archivotxt archivoInsumos;
    private Archivotxt archivoCategorias;

    public Practica03dController(Practica03View vista) {
        this.vista = vista;
        this.modeloInsumos = new ListaInsumos();
        this.modeloCategorias = new ListaCategorias();
        this.archivoInsumos = new Archivotxt("insumos.txt");
        this.archivoCategorias = new Archivotxt("categorias.txt");

        this.vista.Bagregar.addActionListener(this);
        this.vista.Beliminar.addActionListener(this);
        this.vista.Bsalir.addActionListener(this);

        inicializarDatos();
    }

    private void inicializarDatos() {
        ArrayList<String[]> datosCat = archivoCategorias.cargar();
        if (datosCat.isEmpty()) {
            modeloCategorias.agregarCategoria(new Categoria("01", "Materiales"));
            modeloCategorias.agregarCategoria(new Categoria("02", "Mano de Obra"));
            modeloCategorias.agregarCategoria(new Categoria("03", "Maquinaria y Equipo"));
            modeloCategorias.agregarCategoria(new Categoria("04", "Servicios"));
            archivoCategorias.guardar(modeloCategorias.toLineas());
        } else {
            modeloCategorias.cargarCategorias(datosCat);
        }
        modeloCategorias.agregarCategoriasAComboBox(vista.ComboCategoria);

        ArrayList<String[]> datosIns = archivoInsumos.cargar();
        modeloInsumos.cargarInsumo(datosIns);
        vista.areaProductos.setText(modeloInsumos.toString());
    }

    public void volverAlInicio() {
        vista.Bagregar.setText("Agregar");
        vista.Bsalir.setText("Salir");
        vista.Beliminar.setEnabled(true);
        vista.Tid.setEditable(false);
        vista.Tinsumo.setEditable(false);
        vista.Tid.setText("");
        vista.Tinsumo.setText("");
        vista.ComboCategoria.setSelectedIndex(0);
    }

    private void altas() {
        if (vista.Bagregar.getText().equals("Agregar")) {
            vista.Bagregar.setText("Salvar");
            vista.Bsalir.setText("Cancelar");
            vista.Beliminar.setEnabled(false);
            vista.Tid.setEditable(true);
            vista.Tinsumo.setEditable(true);
        } else {
            String id = vista.Tid.getText().trim();
            String insumo = vista.Tinsumo.getText().trim();
            if (!id.isEmpty() && !insumo.isEmpty()) {
                Categoria cat = (Categoria) vista.ComboCategoria.getSelectedItem();
                if (modeloInsumos.agregarInsumo(new Insumo(id, insumo, cat.getIdcategoria()))) {
                    vista.areaProductos.setText(modeloInsumos.toString());
                    archivoInsumos.guardar(modeloInsumos.toLineas());
                    volverAlInicio();
                } else {
                    JOptionPane.showMessageDialog(vista, "ID duplicado");
                }
            }
        }
    }

    private void eliminar() {
        String[] opciones = modeloInsumos.idinsumos();
        if (opciones.length == 0) return;
        String id = (String) JOptionPane.showInputDialog(vista, "Seleccione ID:", "Eliminar", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        if (id != null && !id.isEmpty()) {
            if (modeloInsumos.eliminarInsumoPorId(id)) {
                vista.areaProductos.setText(modeloInsumos.toString());
                archivoInsumos.guardar(modeloInsumos.toLineas());
            }
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

