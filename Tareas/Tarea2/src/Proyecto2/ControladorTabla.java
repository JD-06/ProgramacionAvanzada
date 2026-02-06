package Proyecto2;

import javax.swing.*;
import java.awt.event.*;

public class ControladorTabla implements ActionListener {
    private VistaTabla vista;

    public ControladorTabla(VistaTabla vista) {
        this.vista = vista;
        this.vista.getBtnAgregar().addActionListener(this);
    }

    public void iniciar() {
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nombre = vista.getTxtNombre().getText();
        String edad = vista.getTxtEdad().getText();

        if (!nombre.isEmpty() && !edad.isEmpty()) {
 
            vista.getModeloTabla().addRow(new Object[]{nombre, edad});
            
   
            vista.getTxtNombre().setText("");
            vista.getTxtEdad().setText("");
        } else {
            JOptionPane.showMessageDialog(vista, "Llene todos los campos");
        }
    }
}