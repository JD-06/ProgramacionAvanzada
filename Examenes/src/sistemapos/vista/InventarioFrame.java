package sistemapos.vista;

import sistemapos.controlador.InventarioController;
import sistemapos.modelo.GestorProductos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventarioFrame extends JInternalFrame {

    public JTextField    txtFiltroId, txtFiltroNombre;
    public JComboBox<String> cmbTipo;
    public JRadioButton  rbTodos, rbDisponible, rbAgotado;
    public ButtonGroup   bgEstado;
    public JButton       btnBuscar, btnLimpiarFiltros;

    public JButton       btnCrearNuevo, btnModificar, btnEliminar;

    public JTable            tabla;
    public DefaultTableModel modeloTabla;
    private InventarioController controller;

    public InventarioFrame(GestorProductos gestor) {
        super("Inventario", true, true, true, true);
        setSize(860, 480);
        setLocation(30, 30);
        initUI();
        controller = new InventarioController(this, gestor);
    }

    public void recargarDatos() {
        controller.recargarDatos();
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
        panel.setBorder(BorderFactory.createTitledBorder("Filtros y Busqueda"));
        panel.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        txtFiltroId     = new JTextField();
        txtFiltroNombre = new JTextField();
        cmbTipo = new JComboBox<>(new String[]{"Todos","Electronica","Ropa","Alimentos","Hogar","Otro"});

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
        gbc.gridx = 0; gbc.gridy = r; gbc.gridwidth = 1; gbc.weightx = 0;
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(txtFiltroId, gbc); r++;

        gbc.gridx = 0; gbc.gridy = r; gbc.weightx = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(txtFiltroNombre, gbc); r++;

        gbc.gridx = 0; gbc.gridy = r; gbc.weightx = 0;
        panel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(cmbTipo, gbc); r++;

        JPanel panelEstado = new JPanel(new GridLayout(3, 1));
        panelEstado.setBorder(BorderFactory.createTitledBorder("Estado"));
        panelEstado.add(rbTodos);
        panelEstado.add(rbDisponible);
        panelEstado.add(rbAgotado);
        gbc.gridx = 0; gbc.gridy = r; gbc.gridwidth = 2; gbc.weightx = 1;
        panel.add(panelEstado, gbc); r++;

        JPanel panelBtns = new JPanel(new GridLayout(2, 1, 0, 6));
        panelBtns.add(btnBuscar);
        panelBtns.add(btnLimpiarFiltros);
        gbc.gridx = 0; gbc.gridy = r; gbc.gridwidth = 2; gbc.weightx = 1;
        panel.add(panelBtns, gbc); r++;

        gbc.gridx = 0; gbc.gridy = r; gbc.gridwidth = 2; gbc.weighty = 1;
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

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelAcciones.setBorder(BorderFactory.createTitledBorder("Acciones de Seleccion"));
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
