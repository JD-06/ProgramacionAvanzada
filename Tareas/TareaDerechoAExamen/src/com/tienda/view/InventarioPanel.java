package com.tienda.view;

import com.tienda.model.Producto;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class InventarioPanel extends JPanel {

    public DefaultTableModel model;
    public JTable tabla;
    public JTextField txtBuscar = new JTextField(20);
    public JButton btnAjustar   = ProductosPanel.accentBtn("± Ajustar Stock");
    public JButton btnRefresh   = ProductosPanel.secBtn("↺ Actualizar");

    private TableRowSorter<DefaultTableModel> sorter;

    public InventarioPanel() {
        setLayout(new BorderLayout(8, 8));
        setBackground(MainView.C_BG);
        setBorder(new EmptyBorder(12, 12, 12, 12));

        add(buildTop(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        add(buildBottom(), BorderLayout.SOUTH);
    }

    private JPanel buildTop() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        p.setBackground(MainView.C_BG);
        p.add(new JLabel("Buscar:"));
        txtBuscar.setPreferredSize(new Dimension(220, 30));
        p.add(txtBuscar);
        p.add(btnRefresh);

        // Leyenda
        p.add(Box.createHorizontalStrut(20));
        p.add(colorLegend(new Color(0xFADADD), "Stock bajo (≤5)"));
        p.add(Box.createHorizontalStrut(6));
        p.add(colorLegend(new Color(0xFEF9E7), "Stock medio (≤15)"));
        return p;
    }

    private JScrollPane buildTable() {
        String[] cols = {"SKU", "Nombre", "Categoría", "Stock", "Unidad", "Estado"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) {
                if (c == 3) return Integer.class;
                return String.class;
            }
        };
        tabla = new JTable(model);
        tabla.setRowHeight(26);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(MainView.C_HEADER);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setGridColor(new Color(0xDDE1E7));
        tabla.setShowVerticalLines(false);

        // Renderer de colores según stock
        tabla.setDefaultRenderer(Object.class,  new StockColorRenderer());
        tabla.setDefaultRenderer(Integer.class, new StockColorRenderer());

        int[] widths = {70, 300, 140, 70, 70, 100};
        for (int i = 0; i < widths.length; i++)
            tabla.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        sorter = new TableRowSorter<>(model);
        tabla.setRowSorter(sorter);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0xCED4DA)));
        return scroll;
    }

    private JPanel buildBottom() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        p.setBackground(MainView.C_BG);
        p.add(btnAjustar);
        return p;
    }

    public void setProductos(List<Producto> lista) {
        model.setRowCount(0);
        for (Producto p : lista) {
            String estado;
            if (p.getStock() == 0)      estado = "SIN STOCK";
            else if (p.getStock() <= 5) estado = "CRÍTICO";
            else if (p.getStock() <= 15) estado = "BAJO";
            else                         estado = "OK";
            model.addRow(new Object[]{
                p.getSku(), p.getNombre(), p.getCategoria(),
                p.getStock(), p.getUnidad(), estado
            });
        }
    }

    public void filtrar(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto.trim()));
        }
    }

    public String getSelectedSku() {
        int row = tabla.getSelectedRow();
        if (row < 0) return null;
        return (String) model.getValueAt(tabla.convertRowIndexToModel(row), 0);
    }

    // ── Helpers ──────────────────────────────────────

    private static JLabel colorLegend(Color c, String text) {
        JLabel l = new JLabel(" " + text + " ");
        l.setOpaque(true);
        l.setBackground(c);
        l.setFont(new Font("Arial", Font.PLAIN, 11));
        l.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        return l;
    }

    // Renderer que colorea según estado de stock
    static class StockColorRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                                                       boolean foc, int row, int col) {
            super.getTableCellRendererComponent(t, v, sel, foc, row, col);
            if (!sel) {
                int modelRow = t.convertRowIndexToModel(row);
                String estado = (String) t.getModel().getValueAt(modelRow, 5);
                switch (estado) {
                    case "SIN STOCK": setBackground(new Color(0xFADADD)); break;
                    case "CRÍTICO":   setBackground(new Color(0xFADADD)); break;
                    case "BAJO":      setBackground(new Color(0xFEF9E7)); break;
                    default:          setBackground(row % 2 == 0 ? Color.WHITE : new Color(0xF2F3F4));
                }
            }
            setBorder(new EmptyBorder(0, 6, 0, 6));
            if (v instanceof Integer) setText(v.toString());
            return this;
        }
    }
}
