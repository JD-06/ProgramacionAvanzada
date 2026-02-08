package ejemplos.jbutton;

import javax.swing.*;
import java.awt.*;

public class VistaBoton extends JFrame {
    private JLabel lblContador;
    private JLabel lblMensaje;
    private JButton btnIncrementar;
    private JButton btnDecrementar;
    private JButton btnResetear;
    private JButton btnMostrarHistorial;
    private JTextArea txtHistorial;
    private JScrollPane scrollHistorial;

    public VistaBoton() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("JButton y ActionListener - MVC");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 5, 5));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblContador = new JLabel("Contador: 0", SwingConstants.CENTER);
        lblContador.setFont(new Font("Arial", Font.BOLD, 24));
        lblContador.setForeground(new Color(0, 153, 76));

        lblMensaje = new JLabel("¡Haz click para comenzar!", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.ITALIC, 14));

        panelSuperior.add(lblContador);
        panelSuperior.add(lblMensaje);

        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnIncrementar = new JButton("➕ Incrementar");
        btnIncrementar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnIncrementar.setBackground(new Color(76, 175, 80));
        btnIncrementar.setForeground(Color.WHITE);
        btnIncrementar.setFocusPainted(false);

        btnDecrementar = new JButton("➖ Decrementar");
        btnDecrementar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnDecrementar.setBackground(new Color(244, 67, 54));
        btnDecrementar.setForeground(Color.WHITE);
        btnDecrementar.setFocusPainted(false);

        btnResetear = new JButton("🔄 Resetear");
        btnResetear.setFont(new Font("Arial", Font.PLAIN, 14));
        btnResetear.setBackground(new Color(255, 152, 0));
        btnResetear.setForeground(Color.WHITE);
        btnResetear.setFocusPainted(false);

        btnMostrarHistorial = new JButton("📋 Mostrar Historial");
        btnMostrarHistorial.setFont(new Font("Arial", Font.PLAIN, 14));
        btnMostrarHistorial.setBackground(new Color(33, 150, 243));
        btnMostrarHistorial.setForeground(Color.WHITE);
        btnMostrarHistorial.setFocusPainted(false);

        panelBotones.add(btnIncrementar);
        panelBotones.add(btnDecrementar);
        panelBotones.add(btnResetear);
        panelBotones.add(btnMostrarHistorial);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createTitledBorder("Historial de Acciones"));

        txtHistorial = new JTextArea(5, 30);
        txtHistorial.setEditable(false);
        txtHistorial.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scrollHistorial = new JScrollPane(txtHistorial);

        panelInferior.add(scrollHistorial, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    public JButton getBtnIncrementar() {
        return btnIncrementar;
    }

    public JButton getBtnDecrementar() {
        return btnDecrementar;
    }

    public JButton getBtnResetear() {
        return btnResetear;
    }

    public JButton getBtnMostrarHistorial() {
        return btnMostrarHistorial;
    }

    public void actualizarContador(String texto) {
        lblContador.setText(texto);
    }

    public void actualizarMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }

    public void actualizarHistorial(java.util.List<String> historial) {
        StringBuilder sb = new StringBuilder();
        for (String entrada : historial) {
            sb.append(entrada).append("\n");
        }
        txtHistorial.setText(sb.toString());
    }

    public void limpiarHistorial() {
        txtHistorial.setText("");
    }
}
