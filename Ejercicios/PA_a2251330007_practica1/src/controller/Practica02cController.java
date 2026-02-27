package controller;

import view.Practica02cView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Practica02cController implements ActionListener {
    private Practica02cView vista;

    public Practica02cController(Practica02cView vista) {
        this.vista = vista;
        this.vista.Bsalir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.Bsalir) {
            String cadena = " valor de Jtextfield " + vista.campoTexto.getText().trim();
            cadena += "\n valor de Jpasswordfield " + new String(vista.campoPassword.getPassword()).trim();
            cadena += "\n valor de Jtextarea " + vista.areaTexto.getText();
            cadena += "\n valor de Jformattedtextfiel " + vista.campoFormateado.getText().trim();
            cadena += "\n valor de spinner " + vista.spinner.getValue();
            cadena += "\n valor de slider " + vista.slider.getValue();
            if (vista.comboBox.getSelectedIndex() > -1)
                cadena += "\n valor de combo es " + vista.comboBox.getSelectedItem();

            JOptionPane.showMessageDialog(vista, cadena);
            java.awt.Container p = vista.Bsalir.getParent(); while(p!=null && !(p instanceof javax.swing.JInternalFrame) && !(p instanceof java.awt.Window)) p = p.getParent(); if(p instanceof javax.swing.JInternalFrame) ((javax.swing.JInternalFrame)p).dispose(); else if(p instanceof java.awt.Window) ((java.awt.Window)p).dispose(); else vista.dispose();
        }
    }
}

