package Proyecto2;

import javax.swing.*;
import java.awt.*;

public class VistaTexto extends JFrame {
    private JTextField txtOrigen;
    private JTextField txtDestino;
    private JLabel lblContador;
    private JButton btnCopiar;

    public VistaTexto() {
        configurarVentana();
        iniciarComponentes();
    }

    private void configurarVentana() {
        setTitle("Copiador MVC");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);
    }

    private void iniciarComponentes() {
        txtOrigen = new JTextField(10);
        txtDestino = new JTextField(10);
        txtDestino.setEditable(false);
        
        btnCopiar = new JButton("Copiar y Contar");
        lblContador = new JLabel("Veces copiado: 0");

        add(new JLabel("Escribe:"));
        add(txtOrigen);
        add(btnCopiar);
        add(new JLabel("Copia:"));
        add(txtDestino);
        add(lblContador);
    }


    public JTextField getTxtOrigen() { return txtOrigen; }
    public JTextField getTxtDestino() { return txtDestino; }
    public JLabel getLblContador() { return lblContador; }
    public JButton getBtnCopiar() { return btnCopiar; }
}