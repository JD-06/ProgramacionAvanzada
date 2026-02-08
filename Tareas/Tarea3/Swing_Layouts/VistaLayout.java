package ejemplos.layouts;

import javax.swing.*;
import java.awt.*;

public class VistaLayout extends JFrame {
    private JComboBox<String> cmbLayouts;
    private JPanel panelDemostracion;
    private JLabel lblDescripcion;
    private JButton btnCambiar;

    public VistaLayout() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Java Swing Layouts - MVC");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("Selecciona un Layout:");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));

        cmbLayouts = new JComboBox<>();
        cmbLayouts.setPreferredSize(new Dimension(200, 30));

        btnCambiar = new JButton("Cambiar Layout");
        btnCambiar.setBackground(new Color(33, 150, 243));
        btnCambiar.setForeground(Color.WHITE);
        btnCambiar.setFocusPainted(false);

        panelSuperior.add(lblTitulo);
        panelSuperior.add(cmbLayouts);
        panelSuperior.add(btnCambiar);

        lblDescripcion = new JLabel("", SwingConstants.CENTER);
        lblDescripcion.setFont(new Font("Arial", Font.ITALIC, 12));
        lblDescripcion.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panelDemostracion = new JPanel();
        panelDemostracion.setBorder(BorderFactory.createTitledBorder("Panel de Demostración"));
        panelDemostracion.setBackground(Color.WHITE);

        add(panelSuperior, BorderLayout.NORTH);
        add(lblDescripcion, BorderLayout.CENTER);
        add(panelDemostracion, BorderLayout.SOUTH);
    }

    public void cargarLayouts(String[] layouts) {
        for (String layout : layouts) {
            cmbLayouts.addItem(layout);
        }
    }

    public String getLayoutSeleccionado() {
        return (String) cmbLayouts.getSelectedItem();
    }

    public JButton getBtnCambiar() {
        return btnCambiar;
    }

    public void setDescripcion(String descripcion) {
        lblDescripcion.setText(descripcion);
    }

    public void aplicarFlowLayout() {
        panelDemostracion.removeAll();
        panelDemostracion.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        for (int i = 1; i <= 5; i++) {
            JButton btn = new JButton("Botón " + i);
            panelDemostracion.add(btn);
        }

        actualizarPanel();
    }

    public void aplicarBorderLayout() {
        panelDemostracion.removeAll();
        panelDemostracion.setLayout(new BorderLayout(5, 5));

        panelDemostracion.add(new JButton("NORTH"), BorderLayout.NORTH);
        panelDemostracion.add(new JButton("SOUTH"), BorderLayout.SOUTH);
        panelDemostracion.add(new JButton("EAST"), BorderLayout.EAST);
        panelDemostracion.add(new JButton("WEST"), BorderLayout.WEST);
        panelDemostracion.add(new JButton("CENTER"), BorderLayout.CENTER);

        actualizarPanel();
    }

    public void aplicarGridLayout() {
        panelDemostracion.removeAll();
        panelDemostracion.setLayout(new GridLayout(3, 3, 5, 5));

        for (int i = 1; i <= 9; i++) {
            panelDemostracion.add(new JButton("Celda " + i));
        }

        actualizarPanel();
    }

    public void aplicarBoxLayout() {
        panelDemostracion.removeAll();
        panelDemostracion.setLayout(new BoxLayout(panelDemostracion, BoxLayout.Y_AXIS));

        for (int i = 1; i <= 4; i++) {
            JButton btn = new JButton("Componente " + i);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelDemostracion.add(btn);
            panelDemostracion.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        actualizarPanel();
    }

    public void aplicarCardLayout() {
        panelDemostracion.removeAll();
        CardLayout cardLayout = new CardLayout();
        panelDemostracion.setLayout(cardLayout);

        for (int i = 1; i <= 3; i++) {
            JPanel carta = new JPanel();
            carta.setBackground(new Color(100 + i * 40, 150, 200));
            carta.add(new JLabel("Tarjeta " + i));
            panelDemostracion.add(carta, "Carta" + i);
        }

        JPanel controles = new JPanel();
        JButton btnAnterior = new JButton("< Anterior");
        JButton btnSiguiente = new JButton("Siguiente >");

        btnAnterior.addActionListener(e -> cardLayout.previous(panelDemostracion));
        btnSiguiente.addActionListener(e -> cardLayout.next(panelDemostracion));

        controles.add(btnAnterior);
        controles.add(btnSiguiente);
        panelDemostracion.add(controles, "Controles");

        cardLayout.show(panelDemostracion, "Carta1");

        actualizarPanel();
    }

    public void aplicarGridBagLayout() {
        panelDemostracion.removeAll();
        panelDemostracion.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        panelDemostracion.add(new JButton("Botón 1"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelDemostracion.add(new JButton("Botón 2 (ancho)"), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        panelDemostracion.add(new JButton("Botón 3 (alto)"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridheight = 1;
        panelDemostracion.add(new JButton("Botón 4"), gbc);

        actualizarPanel();
    }

    private void actualizarPanel() {
        panelDemostracion.revalidate();
        panelDemostracion.repaint();
    }
}
