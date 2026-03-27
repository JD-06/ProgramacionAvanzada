package com.tienda.view;

import com.tienda.model.Producto;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class POSPanel extends JPanel {

    // Búsqueda
    public JTextField txtBuscar   = new JTextField(22);
    public JSpinner   spinCantidad;
    public JButton    btnAgregar  = ProductosPanel.accentBtn("Agregar  ▶");
    public JButton    btnQuitar   = ProductosPanel.dangerBtn("✕ Quitar");
    public JButton    btnLimpiar  = ProductosPanel.secBtn("🗑 Limpiar");

    // Tabla carrito
    public DefaultTableModel modelCarrito;
    public JTable tablaCarrito;

    // Pago
    public JComboBox<String> cbPago = new JComboBox<>(new String[]{"EFECTIVO", "TARJETA"});
    public JTextField txtPago = new JTextField(12);
    public JLabel lblCambio  = new JLabel("Cambio: $0.00");
    public JLabel lblTotal   = new JLabel("$0.00");
    public JButton btnFinalizar = ProductosPanel.accentBtn("✔  Finalizar Venta");

    // Lista de productos para búsqueda
    private List<Producto> productos;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    public JList<String> listProductos = new JList<>(listModel);

    public POSPanel() {
        setLayout(new BorderLayout(8, 8));
        setBackground(MainView.C_BG);
        setBorder(new EmptyBorder(12, 12, 12, 12));

        SpinnerNumberModel sm = new SpinnerNumberModel(1, 1, 9999, 1);
        spinCantidad = new JSpinner(sm);
        ((JSpinner.DefaultEditor) spinCantidad.getEditor()).getTextField().setColumns(5);

        add(buildLeft(), BorderLayout.WEST);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildRight(), BorderLayout.EAST);
    }

    // ── Panel izquierdo: búsqueda de producto ────────

    private JPanel buildLeft() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(MainView.C_BG);
        p.setPreferredSize(new Dimension(280, 0));

        JPanel search = new JPanel(new BorderLayout(4, 0));
        search.setBackground(MainView.C_BG);
        search.setBorder(BorderFactory.createTitledBorder("Buscar producto"));
        txtBuscar.setPreferredSize(new Dimension(0, 30));
        search.add(txtBuscar, BorderLayout.CENTER);
        p.add(search, BorderLayout.NORTH);

        listProductos.setFont(new Font("Arial", Font.PLAIN, 13));
        listProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listProductos.setFixedCellHeight(28);
        JScrollPane scroll = new JScrollPane(listProductos);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));
        p.add(scroll, BorderLayout.CENTER);

        JPanel addRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        addRow.setBackground(MainView.C_BG);
        addRow.add(new JLabel("Cantidad:"));
        addRow.add(spinCantidad);
        addRow.add(btnAgregar);
        p.add(addRow, BorderLayout.SOUTH);

        return p;
    }

    // ── Panel central: carrito ───────────────────────

    private JPanel buildCenter() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(MainView.C_BG);

        String[] cols = {"SKU", "Nombre", "Cant.", "Precio Unit.", "Subtotal"};
        modelCarrito = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) {
                if (c == 2) return Integer.class;
                if (c == 3 || c == 4) return Double.class;
                return String.class;
            }
        };
        tablaCarrito = new JTable(modelCarrito);
        tablaCarrito.setRowHeight(28);
        tablaCarrito.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaCarrito.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaCarrito.getTableHeader().setBackground(MainView.C_HEADER);
        tablaCarrito.getTableHeader().setForeground(Color.WHITE);
        tablaCarrito.setGridColor(new Color(0xDDE1E7));
        tablaCarrito.setShowVerticalLines(false);
        tablaCarrito.setDefaultRenderer(Object.class,  new ProductosPanel.StripedRenderer());
        tablaCarrito.setDefaultRenderer(Double.class,  new ProductosPanel.StripedRenderer());
        tablaCarrito.setDefaultRenderer(Integer.class, new ProductosPanel.StripedRenderer());

        int[] w = {70, 240, 50, 90, 90};
        for (int i = 0; i < w.length; i++)
            tablaCarrito.getColumnModel().getColumn(i).setPreferredWidth(w[i]);

        JScrollPane scroll = new JScrollPane(tablaCarrito);
        scroll.setBorder(BorderFactory.createTitledBorder("Carrito de compra"));
        p.add(scroll, BorderLayout.CENTER);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        btnRow.setBackground(MainView.C_BG);
        btnRow.add(btnQuitar);
        btnRow.add(btnLimpiar);
        p.add(btnRow, BorderLayout.SOUTH);

        return p;
    }

    // ── Panel derecho: totales y pago ────────────────

    private JPanel buildRight() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Color.WHITE);
        p.setPreferredSize(new Dimension(230, 0));
        p.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(new Color(0xCED4DA)),
            new EmptyBorder(16, 16, 16, 16)));

        // Total grande
        JPanel totalBox = new JPanel(new BorderLayout());
        totalBox.setBackground(MainView.C_ACCENT);
        totalBox.setBorder(new EmptyBorder(12, 12, 12, 12));
        JLabel lbl = new JLabel("TOTAL");
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        lbl.setForeground(Color.WHITE);
        totalBox.add(lbl, BorderLayout.NORTH);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 28));
        lblTotal.setForeground(Color.WHITE);
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        totalBox.add(lblTotal, BorderLayout.CENTER);
        p.add(totalBox, BorderLayout.NORTH);

        // Datos de pago
        JPanel pagoPanel = new JPanel(new GridBagLayout());
        pagoPanel.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        pagoPanel.add(bold("Método:"), g);
        g.gridx = 1; g.weightx = 1;
        cbPago.setFont(new Font("Arial", Font.PLAIN, 13));
        pagoPanel.add(cbPago, g);

        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        pagoPanel.add(bold("Pago:"), g);
        g.gridx = 1; g.weightx = 1;
        txtPago.setFont(new Font("Arial", Font.PLAIN, 14));
        pagoPanel.add(txtPago, g);

        g.gridx = 0; g.gridy = 2; g.gridwidth = 2;
        lblCambio.setFont(new Font("Arial", Font.BOLD, 13));
        lblCambio.setForeground(new Color(0x27AE60));
        pagoPanel.add(lblCambio, g);

        p.add(pagoPanel, BorderLayout.CENTER);

        // Botón finalizar
        btnFinalizar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 15));
        JPanel btnBox = new JPanel(new GridLayout(1, 1));
        btnBox.setBackground(Color.WHITE);
        btnBox.add(btnFinalizar);
        p.add(btnBox, BorderLayout.SOUTH);

        return p;
    }

    // ── API pública ─────────────────────────────────

    public void setProductos(List<Producto> lista) {
        this.productos = lista;
        actualizarListaProductos("");
    }

    public void actualizarListaProductos(String filtro) {
        listModel.clear();
        if (productos == null) return;
        for (Producto p : productos) {
            if (filtro.isEmpty() ||
                p.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                p.getSku().toLowerCase().contains(filtro.toLowerCase())) {
                listModel.addElement(p.getSku() + "  —  " + p.getNombre() +
                    "  ($" + String.format("%.2f", p.getPrecioVenta()) + ")");
            }
        }
    }

    /** Devuelve el SKU del producto seleccionado en la lista, o null. */
    public String getProductoSeleccionadoSku() {
        String val = listProductos.getSelectedValue();
        if (val == null) return null;
        return val.split("  —  ")[0].trim();
    }

    public void actualizarTotal(double total) {
        lblTotal.setText("$" + String.format("%.2f", total));
        calcularCambio(total);
    }

    public void calcularCambio(double total) {
        try {
            double pago = Double.parseDouble(txtPago.getText().trim());
            double cambio = pago - total;
            lblCambio.setText("Cambio: $" + String.format("%.2f", Math.max(0, cambio)));
            lblCambio.setForeground(cambio >= 0 ? new Color(0x27AE60) : Color.RED);
        } catch (NumberFormatException e) {
            lblCambio.setText("Cambio: $0.00");
        }
    }

    public void limpiarCarrito() {
        modelCarrito.setRowCount(0);
        lblTotal.setText("$0.00");
        lblCambio.setText("Cambio: $0.00");
        txtPago.setText("");
    }

    private JLabel bold(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Arial", Font.BOLD, 13));
        return l;
    }
}
