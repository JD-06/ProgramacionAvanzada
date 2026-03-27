package com.tienda.view;

import com.tienda.model.Producto;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;

public class ProductosPanel extends JPanel {

    public DefaultTableModel model;
    public JTable tablaProductos;
    public JTextField txtBuscar   = new JTextField(20);
    public JComboBox<String> cbCategoria = new JComboBox<>();
    public JButton btnNuevo    = accentBtn("+ Nuevo");
    public JButton btnEditar   = secBtn("✏ Editar");
    public JButton btnEliminar = dangerBtn("✕ Eliminar");
    public JButton btnRefresh  = secBtn("↺ Recargar");

    private JLabel lblImagen;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductosPanel() {
        setLayout(new BorderLayout(8, 8));
        setBackground(MainView.C_BG);
        setBorder(new EmptyBorder(12, 12, 12, 12));

        add(buildTop(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildBottom(), BorderLayout.SOUTH);
    }

    // ── Top: búsqueda ────────────────────────────────

    private JPanel buildTop() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        p.setBackground(MainView.C_BG);

        p.add(new JLabel("Buscar:"));
        txtBuscar.setPreferredSize(new Dimension(220, 30));
        p.add(txtBuscar);

        p.add(new JLabel("Categoría:"));
        cbCategoria.addItem("Todas");
        cbCategoria.setPreferredSize(new Dimension(160, 30));
        p.add(cbCategoria);

        p.add(btnRefresh);
        return p;
    }

    // ── Center: tabla + imagen ───────────────────────

    private JSplitPane buildCenter() {
        String[] cols = {"SKU", "Nombre", "Categoría", "P.Compra", "% Gan.", "P.Venta", "Stock", "Unidad"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) {
                if (c == 3 || c == 4 || c == 5) return Double.class;
                if (c == 6) return Integer.class;
                return String.class;
            }
        };
        tablaProductos = new JTable(model);
        tablaProductos.setRowHeight(26);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaProductos.getTableHeader().setBackground(MainView.C_HEADER);
        tablaProductos.getTableHeader().setForeground(Color.WHITE);
        tablaProductos.setSelectionBackground(new Color(0xD5E8D4));
        tablaProductos.setSelectionForeground(Color.BLACK);
        tablaProductos.setGridColor(new Color(0xDDE1E7));
        tablaProductos.setShowVerticalLines(false);
        tablaProductos.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        // Ancho de columnas
        int[] widths = {70, 260, 140, 80, 60, 80, 60, 60};
        for (int i = 0; i < widths.length; i++)
            tablaProductos.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        // Colores alternos de filas
        tablaProductos.setDefaultRenderer(Object.class, new StripedRenderer());
        tablaProductos.setDefaultRenderer(Double.class, new StripedRenderer());
        tablaProductos.setDefaultRenderer(Integer.class, new StripedRenderer());

        sorter = new TableRowSorter<>(model);
        tablaProductos.setRowSorter(sorter);

        // Listener selección → mostrar imagen
        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) showImageForSelected();
        });

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0xCED4DA)));

        // Panel imagen
        JPanel imgPanel = new JPanel(new BorderLayout());
        imgPanel.setBackground(Color.WHITE);
        imgPanel.setBorder(BorderFactory.createTitledBorder("Vista previa"));
        imgPanel.setPreferredSize(new Dimension(200, 0));
        lblImagen = new JLabel("Sin imagen", SwingConstants.CENTER);
        lblImagen.setFont(new Font("Arial", Font.ITALIC, 12));
        lblImagen.setForeground(Color.GRAY);
        imgPanel.add(lblImagen, BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, imgPanel);
        split.setResizeWeight(0.82);
        split.setBorder(null);
        return split;
    }

    // ── Bottom: botones ──────────────────────────────

    private JPanel buildBottom() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        p.setBackground(MainView.C_BG);
        p.add(btnNuevo);
        p.add(btnEditar);
        p.add(btnEliminar);
        return p;
    }

    // ── Datos ────────────────────────────────────────

    public void setProductos(List<Producto> lista) {
        model.setRowCount(0);
        java.util.Set<String> cats = new java.util.LinkedHashSet<>();
        for (Producto p : lista) {
            model.addRow(new Object[]{
                p.getSku(), p.getNombre(), p.getCategoria(),
                p.getPrecioCompra(), p.getPorcentajeGanancia(), p.getPrecioVenta(),
                p.getStock(), p.getUnidad()
            });
            if (p.getCategoria() != null && !p.getCategoria().isEmpty())
                cats.add(p.getCategoria());
        }
        // Reconstruir combo categorías
        String sel = (String) cbCategoria.getSelectedItem();
        cbCategoria.removeAllItems();
        cbCategoria.addItem("Todas");
        for (String c : cats) cbCategoria.addItem(c);
        if (sel != null) cbCategoria.setSelectedItem(sel);
    }

    public void filtrar(String texto, String categoria) {
        java.util.List<RowFilter<Object, Object>> filters = new java.util.ArrayList<>();
        if (texto != null && !texto.trim().isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + texto.trim()));
        }
        if (categoria != null && !categoria.equals("Todas")) {
            filters.add(RowFilter.regexFilter("(?i)^" + java.util.regex.Pattern.quote(categoria) + "$", 2));
        }
        if (filters.isEmpty()) sorter.setRowFilter(null);
        else sorter.setRowFilter(RowFilter.andFilter(filters));
    }

    public int getSelectedRow() { return tablaProductos.getSelectedRow(); }

    public String getSelectedSku() {
        int row = tablaProductos.getSelectedRow();
        if (row < 0) return null;
        int modelRow = tablaProductos.convertRowIndexToModel(row);
        return (String) model.getValueAt(modelRow, 0);
    }

    private void showImageForSelected() {
        int row = tablaProductos.getSelectedRow();
        if (row < 0) { lblImagen.setIcon(null); lblImagen.setText("Sin imagen"); return; }
        int mr = tablaProductos.convertRowIndexToModel(row);
        // La ruta de imagen no está en la tabla; la obtenemos del modelo completo
        // El controller la proporciona via setImagePath
    }

    public void setPreviewImage(String path) {
        if (path == null || path.isEmpty()) {
            lblImagen.setIcon(null); lblImagen.setText("Sin imagen"); return;
        }
        try {
            BufferedImage img = ImageIO.read(new File(path));
            if (img != null) {
                Image scaled = img.getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(scaled));
                lblImagen.setText("");
            } else {
                lblImagen.setIcon(null); lblImagen.setText("Imagen no encontrada");
            }
        } catch (Exception e) {
            lblImagen.setIcon(null); lblImagen.setText("Error al cargar imagen");
        }
    }

    // ── Helpers de estilo ────────────────────────────

    static JButton accentBtn(String t) { return styledBtn(t, MainView.C_ACCENT, Color.WHITE); }
    static JButton secBtn(String t)    { return styledBtn(t, new Color(0x5D6D7E), Color.WHITE); }
    static JButton dangerBtn(String t) { return styledBtn(t, new Color(0xE74C3C), Color.WHITE); }

    static JButton styledBtn(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setBackground(bg); b.setForeground(fg);
        b.setFont(new Font("Arial", Font.BOLD, 13));
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setBorder(new EmptyBorder(7, 16, 7, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    // ── Renderer con filas alternas ──────────────────

    static class StripedRenderer extends DefaultTableCellRenderer {
        private static final Color ROW_ODD  = Color.WHITE;
        private static final Color ROW_EVEN = new Color(0xF2F3F4);
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                                                       boolean foc, int row, int col) {
            super.getTableCellRendererComponent(t, v, sel, foc, row, col);
            if (!sel) setBackground(row % 2 == 0 ? ROW_ODD : ROW_EVEN);
            setBorder(new EmptyBorder(0, 6, 0, 6));
            if (v instanceof Double) setText(String.format("%.2f", v));
            return this;
        }
    }
}
