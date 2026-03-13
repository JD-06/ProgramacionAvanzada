package sistemapos.vista;

import sistemapos.controlador.VentaController;
import sistemapos.modelo.GestorProductos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PuntoDeVentaFrame extends JInternalFrame {

    public JTextField txtIdCliente, txtNombreCliente, txtCajero;

    public JComboBox<String> cmbProducto;
    public JTextField        txtCantidad;
    public JButton           btnAnadir, btnModificarItem, btnEliminarItem;

    public JTable            tablaCarrito;
    public DefaultTableModel modeloCarrito;

    public JTextField txtSubtotal, txtIva, txtTotal;

    public JButton btnLimpiarCarrito, btnProcesarPago, btnExportarTicket;
    private VentaController controller;

    public PuntoDeVentaFrame(GestorProductos gestor) {
        super("Punto de Venta", true, true, true, true);
        setSize(800, 500);
        setLocation(50, 50);
        initUI();
        controller = new VentaController(this, gestor);
    }

    public void recargarDatos() {
        controller.recargarDatos();
    }

    private void initUI() {
        JPanel principal = new JPanel(new BorderLayout(8, 8));
        principal.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        principal.add(buildPanelCabecera(),   BorderLayout.NORTH);
        principal.add(buildPanelSeleccion(),  BorderLayout.WEST);
        principal.add(buildPanelTransaccion(),BorderLayout.CENTER);

        add(principal);
    }

    private JPanel buildPanelCabecera() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        txtIdCliente     = new JTextField(8);
        txtNombreCliente = new JTextField(14);
        txtCajero        = new JTextField(12);
        panel.add(new JLabel("ID Cliente:"));  panel.add(txtIdCliente);
        panel.add(new JLabel("Nombre:"));      panel.add(txtNombreCliente);
        panel.add(new JLabel("Cajero:"));      panel.add(txtCajero);
        return panel;
    }

    private JPanel buildPanelSeleccion() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("SELECCION DE PRODUCTO"));
        panel.setPreferredSize(new Dimension(220, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets    = new Insets(4, 4, 4, 4);
        gbc.fill      = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;

        cmbProducto = new JComboBox<>();
        txtCantidad = new JTextField("1");

        btnAnadir        = new JButton("Anadir a Carrito");
        btnModificarItem = new JButton("Modificar");
        btnEliminarItem  = new JButton("Eliminar");

        int r = 0;
        gbc.gridy = r++; panel.add(new JLabel("Producto:"), gbc);
        gbc.gridy = r++; panel.add(cmbProducto,             gbc);
        gbc.gridy = r++; panel.add(new JLabel("Cantidad:"), gbc);
        gbc.gridy = r++; panel.add(txtCantidad,             gbc);
        gbc.gridy = r++; panel.add(btnAnadir,               gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = r;   gbc.weightx = 0.5; panel.add(btnModificarItem, gbc);
        gbc.gridx = 1; gbc.gridy = r++; gbc.weightx = 0.5; panel.add(btnEliminarItem,  gbc);

        txtSubtotal = new JTextField(); txtSubtotal.setEditable(false);
        txtIva      = new JTextField(); txtIva.setEditable(false);
        txtTotal    = new JTextField(); txtTotal.setEditable(false);
        txtTotal.setFont(txtTotal.getFont().deriveFont(Font.BOLD, 14f));

        gbc.gridwidth = 2; gbc.gridx = 0;
        gbc.gridy = r++; panel.add(new JSeparator(), gbc);
        gbc.gridy = r++; panel.add(new JLabel("Subtotal:"), gbc);
        gbc.gridy = r++; panel.add(txtSubtotal,            gbc);
        gbc.gridy = r++; panel.add(new JLabel("IVA (16%):"),gbc);
        gbc.gridy = r++; panel.add(txtIva,                 gbc);
        gbc.gridy = r++; panel.add(new JLabel("Total a Pagar:"), gbc);
        gbc.gridy = r++;  panel.add(txtTotal,               gbc);

        btnLimpiarCarrito = new JButton("Limpiar Carrito");
        btnProcesarPago   = new JButton("Procesar Pago");
        btnExportarTicket = new JButton("Exportar Ticket");
        gbc.gridy = r++; panel.add(btnLimpiarCarrito, gbc);
        gbc.gridy = r++; panel.add(btnProcesarPago,   gbc);

        gbc.gridy = r; gbc.weighty = 1; panel.add(new JLabel(), gbc);

        return panel;
    }

    private JPanel buildPanelTransaccion() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createTitledBorder("Detalles Transaccion Actual"));

        String[] cols = {"Cod.", "Descripcion", "Cant.", "P.Unit.", "Total"};
        modeloCarrito = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaCarrito = new JTable(modeloCarrito);
        tablaCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(tablaCarrito), BorderLayout.CENTER);

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBtn.add(btnExportarTicket);
        panel.add(panelBtn, BorderLayout.SOUTH);

        return panel;
    }
}
