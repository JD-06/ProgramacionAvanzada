package com.saeae.view;

import com.saeae.model.CriterioRubrica;
import com.saeae.model.Evaluacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel — Instrumento 2: Rubrica de Evaluacion por Atributos.
 *
 * Corresponde a la hoja "RubricaProducto" de examen.xlsx.
 *
 * Tabla con 7 criterios (filas 15-21), dos columnas:
 *   Columna B  = Descripcion del criterio
 *   Calificacion (0-10) — el programa determina automaticamente la columna del Excel:
 *     >= 9.5  -> G (Excelente 10)
 *     >= 8.5  -> I (Bueno 9)
 *     >= 6.5  -> K (Regular 8-7)
 *     <  6.5  -> M (No Alcanza 6-0)
 */
public class RubricaPanel extends JPanel {

    private static final int NUM_CRITERIOS = 7;

    private DefaultTableModel tableModel;
    private JTable tabla;
    private JLabel lblPromedio;

    public RubricaPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        setBackground(new Color(245, 245, 248));

        // Titulo
        JLabel lblTitulo = new JLabel("Instrumento 2: Rubrica de Evaluacion por Atributos (RubricaProducto)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(0, 32, 96));

        JLabel lblAviso = new JLabel(
            "  Ingresa la calificacion (0-10) por criterio; el programa ubica la columna en el Excel.");
        lblAviso.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblAviso.setForeground(new Color(100, 100, 120));

        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(new Color(245, 245, 248));
        north.add(lblTitulo, BorderLayout.NORTH);
        north.add(lblAviso, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);

        // Tabla: 2 columnas
        String[] cols = {"Criterio (Col B)", "Calificacion 0-10"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return true; }
            @Override public Class<?> getColumnClass(int c) { return String.class; }
        };

        for (int i = 0; i < NUM_CRITERIOS; i++) {
            tableModel.addRow(new Object[]{"", ""});
        }

        tabla = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row) && col == 1) {
                    try {
                        Object val = getValueAt(row, col);
                        if (val != null && !val.toString().isBlank()) {
                            double cal = Double.parseDouble(val.toString());
                            if (cal < 6.5) {
                                c.setBackground(new Color(255, 204, 204)); // rojo — No Alcanza
                            } else {
                                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 248, 255));
                            }
                        } else {
                            c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 248, 255));
                        }
                    } catch (NumberFormatException e) {
                        c.setBackground(new Color(255, 230, 204)); // naranja — valor invalido
                    }
                } else if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 248, 255));
                }
                return c;
            }
        };

        // Confirmar (no cancelar) la edicion activa cuando el usuario hace clic fuera de la tabla
        tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        tabla.setRowHeight(38);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setGridColor(new Color(200, 200, 210));
        tabla.setSelectionBackground(new Color(51, 102, 187));
        tabla.setSelectionForeground(Color.WHITE);

        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tabla.getTableHeader().setBackground(new Color(0, 32, 96));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setReorderingAllowed(false);

        tabla.getColumnModel().getColumn(0).setPreferredWidth(380);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(1).setCellEditor(new CommittingCellEditor());

        tableModel.addTableModelListener(e -> actualizarPromedio());

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 200)));
        add(scroll, BorderLayout.CENTER);

        // Pie
        lblPromedio = new JLabel("Promedio de criterios: —");
        lblPromedio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPromedio.setForeground(new Color(0, 32, 96));

        JLabel lblLeyenda = new JLabel(
            "  >=9.5→Excelente(G) | >=8.5→Bueno(I) | >=6.5→Regular(K) | <6.5→NoAlcanza(M)");
        lblLeyenda.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblLeyenda.setForeground(new Color(100, 100, 120));

        JPanel south = new JPanel(new BorderLayout());
        south.setBackground(new Color(245, 245, 248));
        south.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        south.add(lblPromedio, BorderLayout.NORTH);
        south.add(lblLeyenda, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);
    }

    // =========================================================================
    // Interfaz View
    // =========================================================================

    public void cargarDatos(Evaluacion eval) {
        List<CriterioRubrica> criterios = eval.getCriteriosRubrica();
        if (criterios == null) return;

        for (int i = 0; i < Math.min(criterios.size(), NUM_CRITERIOS); i++) {
            CriterioRubrica cr = criterios.get(i);
            tableModel.setValueAt(nvl(cr.getDescripcion()), i, 0);
            tableModel.setValueAt(cr.getCalificacion() > 0 ? String.valueOf(cr.getCalificacion()) : "", i, 1);
        }
    }

    public List<CriterioRubrica> getCriteriosRubrica() {
        if (tabla != null && tabla.isEditing()) tabla.getCellEditor().stopCellEditing();
        List<CriterioRubrica> lista = new ArrayList<>();
        for (int i = 0; i < NUM_CRITERIOS; i++) {
            CriterioRubrica cr = new CriterioRubrica();
            cr.setDescripcion(celda(i, 0));
            cr.setCalificacion(parseDouble(tableModel.getValueAt(i, 1)));
            lista.add(cr);
        }
        return lista;
    }

    public void limpiar() {
        for (int i = 0; i < NUM_CRITERIOS; i++) {
            tableModel.setValueAt("", i, 0);
            tableModel.setValueAt("", i, 1);
        }
        lblPromedio.setText("Promedio de criterios: —");
    }

    // =========================================================================
    // Helpers
    // =========================================================================

    private void actualizarPromedio() {
        double suma = 0;
        int count = 0;
        for (int i = 0; i < NUM_CRITERIOS; i++) {
            double v = parseDouble(tableModel.getValueAt(i, 1));
            if (v > 0) { suma += v; count++; }
        }
        if (count > 0) {
            lblPromedio.setText(String.format("Promedio de criterios: %.2f", suma / count));
        } else {
            lblPromedio.setText("Promedio de criterios: —");
        }
        tabla.repaint();
    }

    private String celda(int row, int col) {
        Object v = tableModel.getValueAt(row, col);
        return v != null ? v.toString().trim() : "";
    }

    private double parseDouble(Object val) {
        if (val == null || val.toString().isBlank()) return 0;
        try { return Double.parseDouble(val.toString()); }
        catch (NumberFormatException e) { return 0; }
    }

    private String nvl(String s) { return s != null ? s : ""; }

    // =========================================================================
    // Editor personalizado: confirma el valor al perder el foco
    // =========================================================================

    static class CommittingCellEditor extends DefaultCellEditor {
        CommittingCellEditor() {
            super(new JTextField());
            setClickCountToStart(1);
            ((JTextField) getComponent()).addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (!e.isTemporary()) stopCellEditing();
                }
            });
        }
    }
}
