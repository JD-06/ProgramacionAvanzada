package ejemplos.calculadoragrid;

import javax.swing.*;
import java.awt.*;

public class VistaCalculadoraGrid extends JFrame {
    private JTextField txtDisplay;
    private JButton[] botonesNumeros;
    private JButton btnSumar, btnRestar, btnMultiplicar, btnDividir;
    private JButton btnIgual, btnLimpiar, btnBorrar, btnPunto;
    private JButton btnPorcentaje, btnSigno;

    public VistaCalculadoraGrid() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Calculadora GridLayout - MVC");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(new Color(40, 40, 40));

        JPanel panelDisplay = new JPanel(new BorderLayout());
        panelDisplay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelDisplay.setBackground(new Color(40, 40, 40));

        txtDisplay = new JTextField("0");
        txtDisplay.setFont(new Font("Digital-7", Font.BOLD, 48));
        txtDisplay.setEditable(false);
        txtDisplay.setHorizontalAlignment(JTextField.RIGHT);
        txtDisplay.setBackground(new Color(60, 60, 60));
        txtDisplay.setForeground(Color.WHITE);
        txtDisplay.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelDisplay.add(txtDisplay, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(5, 4, 5, 5));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBotones.setBackground(new Color(40, 40, 40));

        botonesNumeros = new JButton[10];
        for (int i = 0; i < 10; i++) {
            botonesNumeros[i] = crearBotonNumero(String.valueOf(i));
        }

        btnLimpiar = crearBotonFuncion("C", new Color(244, 67, 54));
        btnBorrar = crearBotonFuncion("⌫", new Color(255, 152, 0));
        btnPorcentaje = crearBotonFuncion("%", new Color(100, 100, 100));
        btnDividir = crearBotonOperacion("÷");

        btnMultiplicar = crearBotonOperacion("×");
        btnRestar = crearBotonOperacion("-");
        btnSumar = crearBotonOperacion("+");
        btnIgual = crearBotonFuncion("=", new Color(76, 175, 80));

        btnSigno = crearBotonFuncion("±", new Color(100, 100, 100));
        btnPunto = crearBotonFuncion(".", new Color(100, 100, 100));

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnBorrar);
        panelBotones.add(btnPorcentaje);
        panelBotones.add(btnDividir);

        panelBotones.add(botonesNumeros[7]);
        panelBotones.add(botonesNumeros[8]);
        panelBotones.add(botonesNumeros[9]);
        panelBotones.add(btnMultiplicar);

        panelBotones.add(botonesNumeros[4]);
        panelBotones.add(botonesNumeros[5]);
        panelBotones.add(botonesNumeros[6]);
        panelBotones.add(btnRestar);

        panelBotones.add(botonesNumeros[1]);
        panelBotones.add(botonesNumeros[2]);
        panelBotones.add(botonesNumeros[3]);
        panelBotones.add(btnSumar);

        panelBotones.add(btnSigno);
        panelBotones.add(botonesNumeros[0]);
        panelBotones.add(btnPunto);
        panelBotones.add(btnIgual);

        add(panelDisplay, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }

    private JButton crearBotonNumero(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 24));
        boton.setBackground(new Color(80, 80, 80));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(100, 100, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(80, 80, 80));
            }
        });

        return boton;
    }

    private JButton crearBotonOperacion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 24));
        boton.setBackground(new Color(255, 152, 0));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(255, 170, 50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(255, 152, 0));
            }
        });

        return boton;
    }

    private JButton crearBotonFuncion(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 24));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }

    public void actualizarDisplay(String texto) {
        txtDisplay.setText(texto);
    }

    public JButton[] getBotonesNumeros() {
        return botonesNumeros;
    }

    public JButton getBtnSumar() { return btnSumar; }
    public JButton getBtnRestar() { return btnRestar; }
    public JButton getBtnMultiplicar() { return btnMultiplicar; }
    public JButton getBtnDividir() { return btnDividir; }
    public JButton getBtnIgual() { return btnIgual; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnBorrar() { return btnBorrar; }
    public JButton getBtnPunto() { return btnPunto; }
    public JButton getBtnPorcentaje() { return btnPorcentaje; }
    public JButton getBtnSigno() { return btnSigno; }
}
