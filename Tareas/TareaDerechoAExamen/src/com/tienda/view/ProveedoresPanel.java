package com.tienda.view;

import com.tienda.model.Proveedor;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class ProveedoresPanel extends JPanel {

    public DefaultTableModel model;
    public JTable tabla;
    public JTextField txtBuscar   = new JTextField(20);
    public JButton btnNuevo    = ProductosPanel.accentBtn("+ Nuevo");
    public JButton btnEditar   = ProductosPanel.secBtn("✏ Editar");
    public JButton btnEliminar = ProductosPanel.dangerBtn("✕ Eliminar");
    public JButton btnRefresh  = ProductosPanel.secBtn("↺ Actualizar");

    private TableRowSorter<DefaultTableModel> sorter;

    public ProveedoresPanel() {
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
        return p;
    }

    private JScrollPane buildTable() {
        String[] cols = {"ID", "Nombre", "Contacto", "Teléfono", "Email", "Categoría", "Dirección"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(model);
        tabla.setRowHeight(26);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(MainView.C_HEADER);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setGridColor(new Color(0xDDE1E7));
        tabla.setShowVerticalLines(false);
        tabla.setDefaultRenderer(Object.class, new ProductosPanel.StripedRenderer());

        int[] widths = {60, 180, 140, 110, 180, 120, 180};
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
        p.add(btnNuevo);
        p.add(btnEditar);
        p.add(btnEliminar);
        return p;
    }

    public void setProveedores(List<Proveedor> lista) {
        model.setRowCount(0);
        for (Proveedor p : lista) {
            model.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getContacto(),
                p.getTelefono(), p.getEmail(), p.getCategoria(), p.getDireccion()
            });
        }
    }

    public void filtrar(String texto) {
        if (texto == null || texto.trim().isEmpty()) sorter.setRowFilter(null);
        else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto.trim()));
    }

    public String getSelectedId() {
        int row = tabla.getSelectedRow();
        if (row < 0) return null;
        return (String) model.getValueAt(tabla.convertRowIndexToModel(row), 0);
    }
}
