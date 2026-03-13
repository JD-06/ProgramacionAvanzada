package sistemapos.vista;

import sistemapos.controlador.CajerosController;
import sistemapos.modelo.GestorCajeros;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CajerosFrame extends JInternalFrame {

    public JTextField txtNombre;
    public JButton btnGuardar, btnLimpiar;
    public JTable tabla;
    public DefaultTableModel modeloTabla;
    private CajerosController controller;
    private Runnable onActualizacion;

    public CajerosFrame(GestorCajeros gestor, Runnable onActualizacion) {
        super("Cajeros", true, true, true, true);
        this.onActualizacion = onActualizacion;
        setSize(500, 400);
        setLocation(90, 90);
        initUI();
        controller = new CajerosController(this, gestor);
    }

    public void recargarDatos() {
        controller.recargarDatos();
    }

    public void notificarActualizacion() {
        if (onActualizacion != null) {
            onActualizacion.run();
        }
    }

    private void initUI() {
        JPanel principal = new JPanel(new BorderLayout(8, 8));
        principal.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        principal.add(buildPanelFormulario(), BorderLayout.NORTH);
        principal.add(buildPanelTabla(), BorderLayout.CENTER);
        add(principal);
    }

    private JPanel buildPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Alta de Cajero"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombre = new JTextField();
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(txtNombre, gbc);

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        panelBtn.add(btnGuardar);
        panelBtn.add(btnLimpiar);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(panelBtn, gbc);

        return panel;
    }

    private JPanel buildPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Listado de Cajeros"));
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }
}
