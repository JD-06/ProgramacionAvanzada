package sistemapos.vista;

import sistemapos.controlador.InventarioController;
import sistemapos.modelo.GestorProductos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventarioFrame extends JInternalFrame {

    // ── Filtros ───────────────────────────────────────────────────────────
    public JTextField    txtFiltroId, txtFiltroNombre;
    public JComboBox<String> cmbTipo;
    public JRadioButton  rbTodos, rbDisponible, rbAgotado;
    public ButtonGroup   bgEstado;
    public JButton       btnBuscar, btnLimpiarFiltros;

    // ── Acciones ──────────────────────────────────────────────────────────
    public JButton       btnCrearNuevo, btnModificar, btnEliminar;

    // ── Tabla ─────────────────────────────────────────────────────────────
    public JTable            tabla;
    public DefaultTableModel modeloTabla;

    public InventarioFrame(GestorProductos gestor) {
        super("Inventario", true, true, true, true);
        setSize(860, 480);
        setLocation(30, 30);
        initUI();
        new InventarioController(this, gestor);
    }

    private void initUI() {
        JPanel principal = new JPanel(new BorderLayout(8, 8));
        principal.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        principal.add(buildPanelFiltros(), BorderLayout.WEST);
        principal.add(buildPanelVista(),   BorderLayout.CENTER);

        add(principal);
    }

    private JPanel buildPanelFiltros() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Filtros y Búsqueda"));
        panel.setPreferredSize(new Dimension(210, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(4, 4, 4, 4);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.gridwidth = 2;

        txtFiltroId     = new JTextField();
        txtFiltroNombre = new JTextField();
        cmbTipo = new JComboBox<>(new String[]{"Todos","Electrónica","Ropa","Alimentos","Hogar","Otro"});

        rbTodos      = new JRadioButton("Todos",      true);
        rbDisponible = new JRadioButton("Disponible");
        rbAgotado    = new JRadioButton("Agotado");
        bgEstado     = new ButtonGroup();
        bgEstado.add(rbTodos);
        bgEstado.add(rbDisponible);
        bgEstado.add(rbAgotado);

        btnBuscar       = new JButton("Buscar");
        btnLimpiarFiltros = new JButton("Limpiar Filtros");

        int r = 0;
        gbc.gridy = r++; panel.add(new JLabel("ID:"),     gbc);
        gbc.gridy = r++; panel.add(txtFiltroId,            gbc);
        gbc.gridy = r++; panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridy = r++; panel.add(txtFiltroNombre,        gbc);
        gbc.gridy = r++; panel.add(new JLabel("Tipo:"),   gbc);
        gbc.gridy = r++; panel.add(cmbTipo,                gbc);

        JPanel panelEstado = new JPanel(new GridLayout(3, 1));
        panelEstado.setBorder(BorderFactory.createTitledBorder("Estado"));
        panelEstado.add(rbTodos);
        panelEstado.add(rbDisponible);
        panelEstado.add(rbAgotado);
        gbc.gridy = r++; panel.add(panelEstado, gbc);

        JPanel panelBtns = new JPanel(new GridLayout(1, 2, 4, 0));
        panelBtns.add(btnBuscar);
        panelBtns.add(btnLimpiarFiltros);
        gbc.gridy = r++; panel.add(panelBtns, gbc);

        // Relleno para empujar hacia arriba
        gbc.gridy = r; gbc.weighty = 1;
        panel.add(new JLabel(), gbc);

        return panel;
    }

    private JPanel buildPanelVista() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createTitledBorder("Vista de Inventario"));

        String[] cols = {"ID", "Nombre", "Tipo", "Cantidad", "Precio", "Estado"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Acciones
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelAcciones.setBorder(BorderFactory.createTitledBorder("Acciones de Selección"));
        btnCrearNuevo = new JButton("Crear Nuevo");
        btnModificar  = new JButton("Modificar");
        btnEliminar   = new JButton("Eliminar");
        panelAcciones.add(btnCrearNuevo);
        panelAcciones.add(btnModificar);
        panelAcciones.add(btnEliminar);
        panel.add(panelAcciones, BorderLayout.SOUTH);

        return panel;
    }
}
