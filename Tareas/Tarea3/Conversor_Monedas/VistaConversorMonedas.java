package ejemplos.conversormonedas;

import javax.swing.*;
import java.awt.*;

public class VistaConversorMonedas extends JFrame {
    private JTextField txtCantidad;
    private JComboBox<String> cmbMonedaOrigen;
    private JComboBox<String> cmbMonedaDestino;
    private JLabel lblResultado;
    private JLabel lblTasaCambio;
    private JButton btnConvertir;
    private JButton btnIntercambiar;
    private JButton btnLimpiar;

    public VistaConversorMonedas() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Conversor de Monedas - MVC");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(33, 150, 243));
        JLabel lblTitulo = new JLabel("💱 CONVERSOR DE MONEDAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelCentral.add(new JLabel("Cantidad:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridwidth = 2;
        txtCantidad = new JTextField(15);
        txtCantidad.setFont(new Font("Arial", Font.PLAIN, 16));
        panelCentral.add(txtCantidad, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelCentral.add(new JLabel("De:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridwidth = 2;
        cmbMonedaOrigen = new JComboBox<>();
        cmbMonedaOrigen.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCentral.add(cmbMonedaOrigen, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.gridwidth = 2;
        btnIntercambiar = new JButton("⇅ Intercambiar");
        btnIntercambiar.setBackground(new Color(156, 39, 176));
        btnIntercambiar.setForeground(Color.WHITE);
        btnIntercambiar.setFocusPainted(false);
        panelCentral.add(btnIntercambiar, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 1;
        panelCentral.add(new JLabel("A:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.gridwidth = 2;
        cmbMonedaDestino = new JComboBox<>();
        cmbMonedaDestino.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCentral.add(cmbMonedaDestino, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 3;
        lblTasaCambio = new JLabel("Tasa de cambio: --", SwingConstants.CENTER);
        lblTasaCambio.setFont(new Font("Arial", Font.ITALIC, 12));
        lblTasaCambio.setForeground(new Color(100, 100, 100));
        panelCentral.add(lblTasaCambio, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 3;
        lblResultado = new JLabel("= 0.00", SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 28));
        lblResultado.setForeground(new Color(76, 175, 80));
        lblResultado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelCentral.add(lblResultado, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnConvertir = new JButton("💹 Convertir");
        btnConvertir.setFont(new Font("Arial", Font.BOLD, 14));
        btnConvertir.setBackground(new Color(76, 175, 80));
        btnConvertir.setForeground(Color.WHITE);
        btnConvertir.setFocusPainted(false);
        btnConvertir.setPreferredSize(new Dimension(150, 40));

        btnLimpiar = new JButton("🗑️ Limpiar");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLimpiar.setBackground(new Color(244, 67, 54));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setPreferredSize(new Dimension(150, 40));

        panelBotones.add(btnConvertir);
        panelBotones.add(btnLimpiar);

        add(panelTitulo, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void cargarMonedas(String[] monedas) {
        for (String moneda : monedas) {
            cmbMonedaOrigen.addItem(moneda);
            cmbMonedaDestino.addItem(moneda);
        }
        cmbMonedaOrigen.setSelectedItem("USD");
        cmbMonedaDestino.setSelectedItem("EUR");
    }

    public double getCantidad() throws NumberFormatException {
        return Double.parseDouble(txtCantidad.getText());
    }

    public String getMonedaOrigen() {
        return (String) cmbMonedaOrigen.getSelectedItem();
    }

    public String getMonedaDestino() {
        return (String) cmbMonedaDestino.getSelectedItem();
    }

    public void setResultado(double resultado, String moneda) {
        lblResultado.setText(String.format("= %.2f %s", resultado, moneda));
    }

    public void setTasaCambio(double tasa, String origen, String destino) {
        lblTasaCambio.setText(String.format("1 %s = %.4f %s", origen, tasa, destino));
    }

    public void intercambiarMonedas() {
        String temp = (String) cmbMonedaOrigen.getSelectedItem();
        cmbMonedaOrigen.setSelectedItem(cmbMonedaDestino.getSelectedItem());
        cmbMonedaDestino.setSelectedItem(temp);
    }

    public void limpiar() {
        txtCantidad.setText("");
        lblResultado.setText("= 0.00");
        lblTasaCambio.setText("Tasa de cambio: --");
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JButton getBtnConvertir() {
        return btnConvertir;
    }

    public JButton getBtnIntercambiar() {
        return btnIntercambiar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
}
