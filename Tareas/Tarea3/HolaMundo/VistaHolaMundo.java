package ejemplos.holamundo;

import javax.swing.*;
import java.awt.*;

public class VistaHolaMundo extends JFrame {
    private JLabel lblMensaje;
    private JTextField txtNombre;
    private JButton btnSaludar;
    private JButton btnReset;

    public VistaHolaMundo() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Hola Mundo - MVC");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelMensaje = new JPanel();
        lblMensaje = new JLabel("¡Hola Mundo con Swing!");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 18));
        lblMensaje.setForeground(new Color(0, 102, 204));
        panelMensaje.add(lblMensaje);

        JPanel panelCentral = new JPanel(new FlowLayout());
        JLabel lblNombre = new JLabel("Ingresa tu nombre:");
        txtNombre = new JTextField(15);
        panelCentral.add(lblNombre);
        panelCentral.add(txtNombre);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnSaludar = new JButton("Saludar");
        btnReset = new JButton("Reset");
        panelBotones.add(btnSaludar);
        panelBotones.add(btnReset);

        add(panelMensaje, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public JButton getBtnSaludar() {
        return btnSaludar;
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    public String getNombre() {
        return txtNombre.getText();
    }

    public void setMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }

    public void limpiarNombre() {
        txtNombre.setText("");
    }
}
