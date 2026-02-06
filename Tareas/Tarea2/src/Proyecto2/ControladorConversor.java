package Proyecto2;

import javax.swing.*;
import java.awt.event.*;

public class ControladorConversor implements ActionListener {
    private VistaConversor vista;
    private ModeloConversor modelo;

    public ControladorConversor(VistaConversor vista, ModeloConversor modelo) {
        this.vista = vista;
        this.modelo = modelo;

        this.vista.getBtnConvertir().addActionListener(this);
        this.vista.getMenuSalir().addActionListener(this);
        this.vista.getMenuAcerca().addActionListener(this);
    }

    public void iniciar() {
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnConvertir()) {
            try {
                double dolares = Double.parseDouble(vista.getTxtDolares().getText());
                double pesos = modelo.convertirDolarAPeso(dolares);
                vista.getLblResultado().setText("Pesos: $" + String.format("%.2f", pesos));
            } catch (NumberFormatException ex) {
    
                JOptionPane.showMessageDialog(vista, "Por favor ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == vista.getMenuAcerca()) {
            JOptionPane.showMessageDialog(vista, "Versión 1.0 MVC");
        } else if (e.getSource() == vista.getMenuSalir()) {

            int confirm = JOptionPane.showConfirmDialog(vista, "¿Seguro que desea salir?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}