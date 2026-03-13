package sistemapos.vista;

import sistemapos.controlador.ProductosController;
import sistemapos.modelo.GestorProductos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductosFrame extends JInternalFrame {

    // ── Campos del formulario ─────────────────────────────────────────────
    public JTextField txtId, txtCodigo, txtNombre, txtPrecioCompra,
                      txtPrecioVenta, txtStock, txtStockMin;
    public JTextArea  txtDescripcion;
    public JComboBox<String> cmbCategoria;
    public JRadioButton rbActivo, rbDesactivado;
    public ButtonGroup  bgEstado;
    public JButton btnGuardar, btnLimpiar;

    // ── Catálogo ──────────────────────────────────────────────────────────
    public JTable  tabla;
    public DefaultTableModel modeloTabla;
    public JComboBox<String> cmbFiltro;
    public JButton btnBuscar, btnMostrarTodos, btnExportar;

    public ProductosFrame(GestorProductos gestor) {
        super("Productos", true, true, true, true);
        setSize(900, 560);
        setLocation(10, 10);
        initUI();
        new ProductosController(this, gestor);
    }

    private void initUI() {
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 8, 0));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        panelPrincipal.add(buildPanelFormulario());
        panelPrincipal.add(buildPanelCatalogo());

        add(panelPrincipal);
    }

    private JPanel buildPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Alta y Edición"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(4, 4, 4, 4);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.anchor  = GridBagConstraints.WEST;

        txtId          = new JTextField();  txtId.setEnabled(false);
        txtCodigo      = new JTextField();
        txtNombre      = new JTextField();
        txtDescripcion = new JTextArea(3, 15);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        cmbCategoria   = new JComboBox<>(new String[]{"Electrónica","Ropa","Alimentos","Hogar","Otro"});
        txtPrecioCompra= new JTextField();
        txtPrecioVenta = new JTextField();
        txtStock       = new JTextField();
        txtStockMin    = new JTextField();

        rbActivo      = new JRadioButton("Activo", true);
        rbDesactivado = new JRadioButton("Desactivado");
        bgEstado      = new ButtonGroup();
        bgEstado.add(rbActivo);
        bgEstado.add(rbDesactivado);

        btnGuardar = new JButton("Guardar Cambios");
        btnLimpiar = new JButton("Limpiar Formulario");

        int row = 0;
        agregarFila(panel, gbc, row++, "ID [Auto]:",           txtId);
        agregarFila(panel, gbc, row++, "Código:",              txtCodigo);
        agregarFila(panel, gbc, row++, "Nombre del Producto:", txtNombre);

        // Descripción ocupa 2 filas
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(new JScrollPane(txtDescripcion), gbc); row++;

        agregarFila(panel, gbc, row++, "Categoría:",       cmbCategoria);
        agregarFila(panel, gbc, row++, "Precio Compra:",   txtPrecioCompra);
        agregarFila(panel, gbc, row++, "Precio Venta:",    txtPrecioVenta);
        agregarFila(panel, gbc, row++, "Stock Inicial:",   txtStock);
        agregarFila(panel, gbc, row++, "Stock Mín. Alerta:", txtStockMin);

        // Estado
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panel.add(new JLabel("Estado Actual:"), gbc);
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelEstado.setBorder(BorderFactory.createEtchedBorder());
        panelEstado.add(rbActivo);
        panelEstado.add(rbDesactivado);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(panelEstado, gbc); row++;

        // Botones
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; gbc.weightx = 1;
        JPanel panelBtns = new JPanel(new FlowLayout());
        panelBtns.add(btnGuardar);
        panelBtns.add(btnLimpiar);
        panel.add(panelBtns, gbc);

        return panel;
    }

    private JPanel buildPanelCatalogo() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createTitledBorder("Catálogo de Productos"));

        String[] columnas = {"ID", "Código", "Nombre", "Categoría", "Stock", "P.Venta", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel de filtros inferiores
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.add(new JLabel("Buscar por:"));
        cmbFiltro = new JComboBox<>(new String[]{"Nombre", "Categoría", "Estado"});
        panelFiltro.add(cmbFiltro);
        btnBuscar      = new JButton("Buscar");
        btnMostrarTodos= new JButton("Mostrar Todos");
        btnExportar    = new JButton("Exportar Lista");
        panelFiltro.add(btnBuscar);
        panelFiltro.add(btnMostrarTodos);
        panelFiltro.add(btnExportar);
        panel.add(panelFiltro, BorderLayout.SOUTH);

        return panel;
    }

    /** Utilidad para agregar fila label+componente con GridBagLayout */
    private void agregarFila(JPanel p, GridBagConstraints gbc,
                              int row, String label, JComponent comp) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0;
        p.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        p.add(comp, gbc);
    }
}
