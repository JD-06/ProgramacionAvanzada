package sistemapos.vista;

import sistemapos.controlador.ProductosController;
import sistemapos.modelo.GestorProductos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductosFrame extends JInternalFrame {

    // ---- Formulario ----
    public JTextField txtId, txtCodigo, txtNombre, txtPrecioCompra,
                      txtPrecioVenta, txtStock, txtStockMin;
    public JTextArea  txtDescripcion;
    public JComboBox<String> cmbCategoria;
    public JComboBox<String> cmbTipo;         // tipo de producto (NUEVO)
    public JRadioButton rbActivo, rbDesactivado;
    public ButtonGroup  bgEstado;
    public JButton btnGuardar, btnLimpiar;

    // ---- Imagen (NUEVO) ----
    public JTextField txtImagenRuta;
    public JButton    btnSeleccionarImagen;
    public JLabel     lblImagenPreview;

    // ---- Catalogo ----
    public JTable  tabla;
    public DefaultTableModel modeloTabla;
    public JComboBox<String> cmbFiltro;
    public JButton btnBuscar, btnMostrarTodos;
    public JButton btnExportar;           // CSV (existente)
    public JButton btnExportarJSON;       // JSON (NUEVO)
    public JButton btnExportarExcel;      // Excel todos (NUEVO)
    public JButton btnExportarExcelCat;   // Excel por categoria (NUEVO)

    private ProductosController controller;

    /** Categorias del supermercado */
    private static final String[] CATEGORIAS = {
        "Abarrotes (Despensa)",
        "Bebidas",
        "Lacteos y Huevo",
        "Frutas y Verduras",
        "Carnes y Pescados",
        "Salchichoneria",
        "Panaderia y Tortilleria",
        "Limpieza del Hogar",
        "Cuidado Personal",
        "Snacks y Dulceria",
        "Mascotas"
    };

    /** Tipos de producto (mapea a la jerarquia de clases) */
    private static final String[] TIPOS = {
        "Unitario", "Por Peso (kg)", "Por Volumen (lt)"
    };

    public ProductosFrame(GestorProductos gestor) {
        super("Productos", true, true, true, true);
        setSize(1020, 620);
        setLocation(10, 10);
        initUI();
        controller = new ProductosController(this, gestor);
    }

    public void recargarDatos() {
        controller.recargarDatos();
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
        panel.setBorder(BorderFactory.createTitledBorder("Alta y Edicion"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        txtId           = new JTextField(); txtId.setEnabled(false);
        txtCodigo       = new JTextField();
        txtNombre       = new JTextField();
        txtDescripcion  = new JTextArea(2, 15);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        cmbCategoria    = new JComboBox<>(CATEGORIAS);
        cmbTipo         = new JComboBox<>(TIPOS);
        txtPrecioCompra = new JTextField();
        txtPrecioVenta  = new JTextField();
        txtStock        = new JTextField();
        txtStockMin     = new JTextField();

        rbActivo      = new JRadioButton("Activo", true);
        rbDesactivado = new JRadioButton("Desactivado");
        bgEstado      = new ButtonGroup();
        bgEstado.add(rbActivo);
        bgEstado.add(rbDesactivado);

        // Imagen
        txtImagenRuta        = new JTextField(); txtImagenRuta.setEditable(false);
        btnSeleccionarImagen = new JButton("...");
        btnSeleccionarImagen.setToolTipText("Seleccionar imagen del producto");
        lblImagenPreview = new JLabel("Sin imagen", SwingConstants.CENTER);
        lblImagenPreview.setPreferredSize(new Dimension(100, 100));
        lblImagenPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        btnGuardar = new JButton("Guardar Cambios");
        btnLimpiar = new JButton("Limpiar Formulario");

        int row = 0;
        agregarFila(panel, gbc, row++, "ID [Auto]:",           txtId);
        agregarFila(panel, gbc, row++, "Codigo:",              txtCodigo);
        agregarFila(panel, gbc, row++, "Nombre:",              txtNombre);

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panel.add(new JLabel("Descripcion:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(new JScrollPane(txtDescripcion), gbc); row++;

        agregarFila(panel, gbc, row++, "Categoria:",    cmbCategoria);
        agregarFila(panel, gbc, row++, "Tipo Venta:",   cmbTipo);
        agregarFila(panel, gbc, row++, "P. Compra:",    txtPrecioCompra);
        agregarFila(panel, gbc, row++, "P. Venta:",     txtPrecioVenta);
        agregarFila(panel, gbc, row++, "Stock:",        txtStock);
        agregarFila(panel, gbc, row++, "Stock Min.:",   txtStockMin);

        // Estado
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panel.add(new JLabel("Estado:"), gbc);
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelEstado.setBorder(BorderFactory.createEtchedBorder());
        panelEstado.add(rbActivo); panelEstado.add(rbDesactivado);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(panelEstado, gbc); row++;

        // Imagen - campo + boton
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panel.add(new JLabel("Imagen:"), gbc);
        JPanel pnlImg = new JPanel(new BorderLayout(4, 0));
        pnlImg.add(txtImagenRuta, BorderLayout.CENTER);
        pnlImg.add(btnSeleccionarImagen, BorderLayout.EAST);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(pnlImg, gbc); row++;

        // Preview de imagen
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; gbc.weightx = 1;
        JPanel pnlPreview = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlPreview.add(lblImagenPreview);
        panel.add(pnlPreview, gbc); row++;

        // Botones guardar / limpiar
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JPanel panelBtns = new JPanel(new FlowLayout());
        panelBtns.add(btnGuardar);
        panelBtns.add(btnLimpiar);
        panel.add(panelBtns, gbc);

        return panel;
    }

    private JPanel buildPanelCatalogo() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createTitledBorder("Catalogo de Productos"));

        String[] columnas = {"ID","Codigo","Nombre","Categoria","Tipo","Stock","P.Venta","Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel de busqueda y exportacion
        JPanel panelSur = new JPanel(new BorderLayout());

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.add(new JLabel("Buscar por:"));
        cmbFiltro = new JComboBox<>(new String[]{"Nombre","Categoria","Tipo","Estado"});
        panelFiltro.add(cmbFiltro);
        btnBuscar       = new JButton("Buscar");
        btnMostrarTodos = new JButton("Todos");
        panelFiltro.add(btnBuscar);
        panelFiltro.add(btnMostrarTodos);

        JPanel panelExport = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExportar        = new JButton("Guardar CSV");
        btnExportarJSON    = new JButton("Exportar JSON");
        btnExportarExcel   = new JButton("Excel: Lista");
        btnExportarExcelCat= new JButton("Excel: x Categoria");
        panelExport.add(btnExportar);
        panelExport.add(btnExportarJSON);
        panelExport.add(btnExportarExcel);
        panelExport.add(btnExportarExcelCat);

        panelSur.add(panelFiltro,  BorderLayout.WEST);
        panelSur.add(panelExport, BorderLayout.EAST);
        panel.add(panelSur, BorderLayout.SOUTH);

        return panel;
    }

    private void agregarFila(JPanel p, GridBagConstraints gbc,
                              int row, String label, JComponent comp) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0;
        p.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        p.add(comp, gbc);
    }
}
