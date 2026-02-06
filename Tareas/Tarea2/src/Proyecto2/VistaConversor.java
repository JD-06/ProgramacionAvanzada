package Proyecto2;

import javax.swing.*;
import java.awt.*;

public class VistaConversor extends JFrame {
    private JTextField txtDolares;
    private JLabel lblResultado;
    private JButton btnConvertir;
    private JMenuItem menuSalir; 
    private JMenuItem menuAcerca;

    public VistaConversor() {
        configurarVentana();
        iniciarComponentes();
    }

    private void configurarVentana() {
        setTitle("Conversor MVC");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10)); 
        setLocationRelativeTo(null);
    }

    private void iniciarComponentes() {
        // Crear Menu (JMenuBar topic)
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOpciones = new JMenu("Opciones");
        menuSalir = new JMenuItem("Salir");
        menuAcerca = new JMenuItem("Acerca de");
        
        menuOpciones.add(menuAcerca);
        menuOpciones.addSeparator();
        menuOpciones.add(menuSalir);
        menuBar.add(menuOpciones);
        setJMenuBar(menuBar);
  
        add(new JLabel("Dólares ($):"));
        txtDolares = new JTextField();
        add(txtDolares);

        btnConvertir = new JButton("Convertir");
        add(btnConvertir);
        
        lblResultado = new JLabel("Pesos: $0.00");
        add(lblResultado);
    }

    // Getters
    public JTextField getTxtDolares() { return txtDolares; }
    public JLabel getLblResultado() { return lblResultado; }
    public JButton getBtnConvertir() { return btnConvertir; }
    public JMenuItem getMenuSalir() { return menuSalir; }
    public JMenuItem getMenuAcerca() { return menuAcerca; }
}