package ejemplos.dialogos;

import javax.swing.*;
import java.awt.*;

public class VistaDialogos extends JFrame {
    private JButton btnMensajeSimple;
    private JButton btnMensajeInfo;
    private JButton btnMensajeAdvertencia;
    private JButton btnMensajeError;
    private JButton btnMensajePregunta;
    private JButton btnDialogoInput;
    private JButton btnDialogoOpcion;
    private JButton btnDialogoConfirmacion;
    private JButton btnDialogoPersonalizado;
    private JButton btnMostrarResumen;
    private JTextArea txtHistorial;
    private JLabel lblEstado;

    public VistaDialogos() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Ventanas de Diálogo - MVC");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(63, 81, 181));
        JLabel lblTitulo = new JLabel("📋 EJEMPLOS DE DIÁLOGOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        JPanel panelBotones = new JPanel(new GridLayout(5, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        btnMensajeSimple = crearBoton("💬 Mensaje Simple", new Color(156, 39, 176));
        btnMensajeInfo = crearBoton("ℹ️ Mensaje Información", new Color(33, 150, 243));
        btnMensajeAdvertencia = crearBoton("⚠️ Mensaje Advertencia", new Color(255, 152, 0));
        btnMensajeError = crearBoton("❌ Mensaje Error", new Color(244, 67, 54));
        btnMensajePregunta = crearBoton("❓ Mensaje Pregunta", new Color(0, 150, 136));

        btnDialogoInput = crearBoton("✏️ Diálogo Input", new Color(76, 175, 80));
        btnDialogoOpcion = crearBoton("📝 Diálogo Opción", new Color(103, 58, 183));
        btnDialogoConfirmacion = crearBoton("✔️ Confirmación Ok/Cancel", new Color(233, 30, 99));
        btnDialogoPersonalizado = crearBoton("🎨 Diálogo Personalizado", new Color(0, 188, 212));
        btnMostrarResumen = crearBoton("📊 Mostrar Resumen", new Color(121, 85, 72));

        panelBotones.add(btnMensajeSimple);
        panelBotones.add(btnMensajeInfo);
        panelBotones.add(btnMensajeAdvertencia);
        panelBotones.add(btnMensajeError);
        panelBotones.add(btnMensajePregunta);
        panelBotones.add(btnDialogoInput);
        panelBotones.add(btnDialogoOpcion);
        panelBotones.add(btnDialogoConfirmacion);
        panelBotones.add(btnDialogoPersonalizado);
        panelBotones.add(btnMostrarResumen);

        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        JLabel lblHistorialTitulo = new JLabel("Historial de Acciones:");
        lblHistorialTitulo.setFont(new Font("Arial", Font.BOLD, 12));

        txtHistorial = new JTextArea(8, 30);
        txtHistorial.setEditable(false);
        txtHistorial.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollHistorial = new JScrollPane(txtHistorial);

        lblEstado = new JLabel("Listo");
        lblEstado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panelInferior.add(lblHistorialTitulo, BorderLayout.NORTH);
        panelInferior.add(scrollHistorial, BorderLayout.CENTER);
        panelInferior.add(lblEstado, BorderLayout.SOUTH);

        add(panelTitulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    public void mostrarMensajeSimple(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarMensajeInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarMensajeAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public int mostrarMensajePregunta(String pregunta) {
        return JOptionPane.showConfirmDialog(this, pregunta, "Pregunta",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    public String mostrarDialogoInput(String mensaje, String valorInicial) {
        return JOptionPane.showInputDialog(this, mensaje, valorInicial);
    }

    public String mostrarDialogoOpcion(String mensaje, String[] opciones) {
        return (String) JOptionPane.showInputDialog(this, mensaje, "Selecciona una opción",
            JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
    }

    public int mostrarDialogoConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    public Object[] mostrarDialogoPersonalizado() {
        JTextField txtNombre = new JTextField(15);
        JTextField txtEdad = new JTextField(15);
        JComboBox<String> cmbColor = new JComboBox<>(new String[]{"Rojo", "Azul", "Verde", "Amarillo"});

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Edad:"));
        panel.add(txtEdad);
        panel.add(new JLabel("Color Favorito:"));
        panel.add(cmbColor);

        int resultado = JOptionPane.showConfirmDialog(this, panel,
            "Ingresa tus datos", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            return new Object[]{txtNombre.getText(), txtEdad.getText(), cmbColor.getSelectedItem()};
        }
        return null;
    }

    public void actualizarHistorial(java.util.List<String> historial) {
        StringBuilder sb = new StringBuilder();
        for (String accion : historial) {
            sb.append(accion).append("\n");
        }
        txtHistorial.setText(sb.toString());
        txtHistorial.setCaretPosition(txtHistorial.getDocument().getLength());
    }

    public void setEstado(String estado) {
        lblEstado.setText(estado);
    }

    public JButton getBtnMensajeSimple() { return btnMensajeSimple; }
    public JButton getBtnMensajeInfo() { return btnMensajeInfo; }
    public JButton getBtnMensajeAdvertencia() { return btnMensajeAdvertencia; }
    public JButton getBtnMensajeError() { return btnMensajeError; }
    public JButton getBtnMensajePregunta() { return btnMensajePregunta; }
    public JButton getBtnDialogoInput() { return btnDialogoInput; }
    public JButton getBtnDialogoOpcion() { return btnDialogoOpcion; }
    public JButton getBtnDialogoConfirmacion() { return btnDialogoConfirmacion; }
    public JButton getBtnDialogoPersonalizado() { return btnDialogoPersonalizado; }
    public JButton getBtnMostrarResumen() { return btnMostrarResumen; }
}
