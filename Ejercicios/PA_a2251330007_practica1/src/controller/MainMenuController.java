package controller;

import view.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener {
    private MainMenuView vista;

    public MainMenuController(MainMenuView vista) {
        this.vista = vista;
        
        vista.m1_01.addActionListener(this);
        vista.m1_02.addActionListener(this);
        vista.m1_03.addActionListener(this);
        vista.m1_01b.addActionListener(this);
        vista.m2_a.addActionListener(this);
        vista.m2_b.addActionListener(this);
        vista.m2_b2.addActionListener(this);
        vista.m2_c.addActionListener(this);
        
        vista.m3_a.addActionListener(this);
        vista.m3_b.addActionListener(this);
        vista.m3_c.addActionListener(this);
        vista.m3_d.addActionListener(this);
    }

    private void abrirVentana(String titulo, java.awt.Container content, java.awt.Dimension size) {
        JInternalFrame iframe = new JInternalFrame(titulo, true, true, true, true);
        iframe.setContentPane(content);
        iframe.setSize(size);
        
        // Ajustar posicion para que no se superpongan exactamente
        int offset = 30 * vista.desktopPane.getAllFrames().length;
        iframe.setLocation(50 + offset, 50 + offset);
        
        iframe.setVisible(true);
        vista.desktopPane.add(iframe);
        
        try {
            iframe.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            // No hacer nada si falla al seleccionar
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.m1_01) {
            Practica01_01 v = new Practica01_01();
            abrirVentana("Practica 01_01", v.getContentPane(), v.getSize());
        } else if (e.getSource() == vista.m1_02) {
            Practica01_02 v = new Practica01_02();
            abrirVentana("Practica 01_02", v.getContentPane(), v.getSize());
        } else if (e.getSource() == vista.m1_03) {
            Practica01_03 v = new Practica01_03();
            abrirVentana("Practica 01_03", v.getContentPane(), v.getSize());
        } else if (e.getSource() == vista.m1_01b) {
            Practica01_01_b v = new Practica01_01_b();
            abrirVentana("Practica 01_01_b", v.getContentPane(), v.getSize());
        } else if (e.getSource() == vista.m2_a) {
            Practica02_a v = new Practica02_a();
            abrirVentana("Practica 02_a", v.getContentPane(), v.getSize());
        } else if (e.getSource() == vista.m2_b) {
            Practica02bView v = new Practica02bView("Eventos");
            new Practica02bController(v);
            abrirVentana("Practica 02_b", v.getContentPane(), v.getSize());
        } else if (e.getSource() == vista.m2_b2) {
            Practica02bView v = new Practica02bView("Nemonicos");
            new Practica02b2Controller(v);
            abrirVentana("Practica 02_b2", v.getContentPane(), v.getSize());
        } else if (e.getSource() == vista.m2_c) {
            Practica02cView v = new Practica02cView();
            new Practica02cController(v);
            abrirVentana("Practica 02_c", v.getContentPane(), v.getSize());
        } else if (e.getSource() == vista.m3_a) {
            Practica03View v = new Practica03View("Insumos");
            new Practica03aController(v);
            abrirVentana("Practica 03_a", v.getContentPane(), v.getSize());
        } else if (e.getSource() == vista.m3_b) {
            Practica03bView v = new Practica03bView("Categorias");
            new Practica03bController(v);
            abrirVentana("Practica 03_b", v.getContentPane(), v.getSize());
        } else if (e.getSource() == vista.m3_c) {
            Practica03bView v = new Practica03bView("Categorias con Archivo");
            new Practica03cController(v);
            abrirVentana("Practica 03_c", v.getContentPane(), v.getSize());
        } else if (e.getSource() == vista.m3_d) {
            Practica03View v = new Practica03View("Persistencia Completa");
            new Practica03dController(v);
            abrirVentana("Practica 03_d", v.getContentPane(), v.getSize());
        }
    }
}

