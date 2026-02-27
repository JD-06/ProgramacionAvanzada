package controller;

import view.Practica02bView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Practica02bController implements ActionListener {
    private Practica02bView vista;

    public Practica02bController(Practica02bView vista) {
        this.vista = vista;
        this.vista.Bsalir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vista.Bsalir) {
            JOptionPane.showMessageDialog(this.vista, "Hasta Luego");
            java.awt.Container p = this.vista.Bsalir.getParent(); while(p!=null && !(p instanceof javax.swing.JInternalFrame) && !(p instanceof java.awt.Window)) p = p.getParent(); if(p instanceof javax.swing.JInternalFrame) ((javax.swing.JInternalFrame)p).dispose(); else if(p instanceof java.awt.Window) ((java.awt.Window)p).dispose(); else this.java.awt.Container p = vista.Bsalir.getParent(); while(p!=null && !(p instanceof javax.swing.JInternalFrame) && !(p instanceof java.awt.Window)) p = p.getParent(); if(p instanceof javax.swing.JInternalFrame) ((javax.swing.JInternalFrame)p).dispose(); else if(p instanceof java.awt.Window) ((java.awt.Window)p).dispose(); else vista.dispose();
        }
    }
}

