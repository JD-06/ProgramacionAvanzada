package ejemplos.copiartexto;

import javax.swing.*;
import java.awt.*;

public class VistaCopiarTexto extends JFrame {
    private JTextArea txtOrigen;
    private JTextArea txtDestino;
    private JButton btnCopiar;
    private JButton btnInvertir;
    private JButton btnMayusculas;
    private JButton btnMinusculas;
    private JButton btnLimpiar;
    private JLabel lblEstadisticasOrigen;
    private JLabel lblEstadisticasDestino;

    public VistaCopiarTexto() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Copiar Texto entre Campos - MVC");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelOrigen = new JPanel(new BorderLayout(5, 5));
        panelOrigen.setBorder(BorderFactory.createTitledBorder("Texto Origen"));

        txtOrigen = new JTextArea();
        txtOrigen.setLineWrap(true);
        txtOrigen.setWrapStyleWord(true);
        txtOrigen.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollOrigen = new JScrollPane(txtOrigen);

        lblEstadisticasOrigen = new JLabel("Caracteres: 0 | Palabras: 0");
        lblEstadisticasOrigen.setHorizontalAlignment(SwingConstants.CENTER);

        panelOrigen.add(scrollOrigen, BorderLayout.CENTER);
        panelOrigen.add(lblEstadisticasOrigen, BorderLayout.SOUTH);

        JPanel panelDestino = new JPanel(new BorderLayout(5, 5));
        panelDestino.setBorder(BorderFactory.createTitledBorder("Texto Destino"));

        txtDestino = new JTextArea();
        txtDestino.setLineWrap(true);
        txtDestino.setWrapStyleWord(true);
        txtDestino.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDestino.setEditable(false);
        txtDestino.setBackground(new Color(240, 240, 240));
        JScrollPane scrollDestino = new JScrollPane(txtDestino);

        lblEstadisticasDestino = new JLabel("Caracteres: 0 | Palabras: 0");
        lblEstadisticasDestino.setHorizontalAlignment(SwingConstants.CENTER);

        panelDestino.add(scrollDestino, BorderLayout.CENTER);
        panelDestino.add(lblEstadisticasDestino, BorderLayout.SOUTH);

        panelPrincipal.add(panelOrigen);
        panelPrincipal.add(panelDestino);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        btnCopiar = new JButton("➡️ Copiar");
        btnInvertir = new JButton("🔄 Invertir");
        btnMayusculas = new JButton("⬆️ MAYÚSCULAS");
        btnMinusculas = new JButton("⬇️ minúsculas");
        btnLimpiar = new JButton("🗑️ Limpiar");

        estilizarBoton(btnCopiar, new Color(33, 150, 243));
        estilizarBoton(btnInvertir, new Color(156, 39, 176));
        estilizarBoton(btnMayusculas, new Color(255, 152, 0));
        estilizarBoton(btnMinusculas, new Color(76, 175, 80));
        estilizarBoton(btnLimpiar, new Color(244, 67, 54));

        panelBotones.add(btnCopiar);
        panelBotones.add(btnInvertir);
        panelBotones.add(btnMayusculas);
        panelBotones.add(btnMinusculas);
        panelBotones.add(btnLimpiar);

        add(panelPrincipal, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void estilizarBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
    }

    public String getTextoOrigen() {
        return txtOrigen.getText();
    }

    public void setTextoDestino(String texto) {
        txtDestino.setText(texto);
    }

    public JButton getBtnCopiar() {
        return btnCopiar;
    }

    public JButton getBtnInvertir() {
        return btnInvertir;
    }

    public JButton getBtnMayusculas() {
        return btnMayusculas;
    }

    public JButton getBtnMinusculas() {
        return btnMinusculas;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void actualizarEstadisticasOrigen(int caracteres, int palabras) {
        lblEstadisticasOrigen.setText("Caracteres: " + caracteres + " | Palabras: " + palabras);
    }

    public void actualizarEstadisticasDestino(int caracteres, int palabras) {
        lblEstadisticasDestino.setText("Caracteres: " + caracteres + " | Palabras: " + palabras);
    }

    public void limpiarCampos() {
        txtOrigen.setText("");
        txtDestino.setText("");
    }
}
