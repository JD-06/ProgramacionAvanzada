package com.saeae.view;

import com.saeae.model.AlumnoCotejo;
import com.saeae.model.Evaluacion;
import com.saeae.model.ListaCotejo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel — Instrumento 3: Lista de Cotejo de Atributos.
 *
 * Corresponde a la hoja "ListaCotejoAtributo" de examen.xlsx.
 *
 * Tabla de alumnos con dos columnas:
 *   Columna A  = Nombre del alumno (de Datosbase.xlsx)
 *   Columna B  = Calificacion (0-10) — el programa determina la columna del Excel:
 *                  >= 9.5  -> D (Excelente 10)
 *                  >= 8.5  -> E (Bueno 9)
 *                  >= 6.5  -> F (Regular 8-7)
 *                  <  6.5  -> G (No Alcanza 6-0)
 *
 * Fila 50 = PROMEDIO FINAL del grupo (promedio de todas las calificaciones)
 */
public class ListaCotejoPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable tabla;
    private JLabel lblPromedio;

    public ListaCotejoPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        setBackground(new Color(245, 245, 248));

        // Titulo
        JLabel lblTitulo = new JLabel("Instrumento 3: Lista de Cotejo de Atributos (ListaCotejoAtributo)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(0, 32, 96));

        JLabel lblAviso = new JLabel(
            "  Los alumnos se cargan automaticamente desde Datosbase.xlsx. " +
            "Ingresa la calificacion (0-10); el programa ubica la columna en el Excel.");
        lblAviso.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblAviso.setForeground(new Color(100, 100, 120));

        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(new Color(245, 245, 248));
        north.add(lblTitulo, BorderLayout.NORTH);
        north.add(lblAviso, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);

        // Tabla: solo 2 columnas
        String[] cols = {"Alumno (Col A)", "Calificacion 0-10"};

        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c == 1; }
            @Override public Class<?> getColumnClass(int c) {
                return c == 0 ? String.class : String.class;
            }
        };

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
                                c.setBackground(new Color(255, 204, 204)); // rojo suave — No Alcanza
                            } else {
                                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 248, 255));
                            }
                        } else {
                            c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 248, 255));
                        }
                    } catch (NumberFormatException e) {
                        c.setBackground(new Color(255, 230, 204)); // naranja suave — valor invalido
                    }
                } else if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 248, 255));
                }
                return c;
            }
        };

        // Confirmar (no cancelar) la edicion activa cuando el usuario hace clic fuera de la tabla
        tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        tabla.setRowHeight(24);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setGridColor(new Color(200, 200, 210));
        tabla.setSelectionBackground(new Color(51, 102, 187));
        tabla.setSelectionForeground(Color.WHITE);

        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(0, 32, 96));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setReorderingAllowed(false);

        tabla.getColumnModel().getColumn(0).setPreferredWidth(300);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(130);

        // Editor que confirma el valor cuando el campo pierde el foco
        // (sin esto, hacer clic en "Guardar" descarta el valor que se estaba escribiendo)
        tabla.getColumnModel().getColumn(1).setCellEditor(new CommittingCellEditor());

        tableModel.addTableModelListener(e -> actualizarPromedio());

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 200)));
        add(scroll, BorderLayout.CENTER);

        // Pie: promedio del grupo
        lblPromedio = new JLabel("Promedio del grupo: —");
        lblPromedio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPromedio.setForeground(new Color(0, 32, 96));

        JLabel lblLeyenda = new JLabel(
            "  >=9.5→Excelente(D) | >=8.5→Bueno(E) | >=6.5→Regular(F) | <6.5→NoAlcanza(G)");
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
    // Metodos de carga
    // =========================================================================

    /**
     * Carga la lista de alumnos desde Datosbase (nombre solo, calificacion en 0).
     * Preserva calificaciones si el alumno ya estaba en la tabla.
     */
    public void cargarAlumnos(List<AlumnoCotejo> alumnos) {
        // Guardar calificaciones actuales por nombre
        java.util.Map<String, String> calsActuales = new java.util.HashMap<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object nombre = tableModel.getValueAt(i, 0);
            Object cal    = tableModel.getValueAt(i, 1);
            if (nombre != null && cal != null && !cal.toString().isBlank()) {
                calsActuales.put(nombre.toString(), cal.toString());
            }
        }

        tableModel.setRowCount(0);
        for (AlumnoCotejo a : alumnos) {
            String calPrevia = calsActuales.get(a.getNombre());
            String calMostrar = (calPrevia != null) ? calPrevia
                : (a.getCalificacion() > 0 ? String.valueOf(a.getCalificacion()) : "");
            tableModel.addRow(new Object[]{a.getNombre(), calMostrar});
        }
    }

    // =========================================================================
    // Interfaz View
    // =========================================================================

    public void cargarDatos(Evaluacion eval) {
        if (eval.getListaCotejo() == null) return;
        List<AlumnoCotejo> alumnos = eval.getListaCotejo().getAlumnos();
        if (alumnos == null) return;
        tableModel.setRowCount(0);
        for (AlumnoCotejo a : alumnos) {
            String calMostrar = a.getCalificacion() > 0 ? String.valueOf(a.getCalificacion()) : "";
            tableModel.addRow(new Object[]{a.getNombre(), calMostrar});
        }
    }

    public ListaCotejo getListaCotejo() {
        if (tabla != null && tabla.isEditing()) tabla.getCellEditor().stopCellEditing();
        ListaCotejo lc = new ListaCotejo();
        List<AlumnoCotejo> alumnos = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            AlumnoCotejo a = new AlumnoCotejo();
            Object nombre = tableModel.getValueAt(i, 0);
            a.setNombre(nombre != null ? nombre.toString().trim() : "");
            a.setCalificacion(parseDouble(tableModel.getValueAt(i, 1)));
            if (!a.getNombre().isEmpty()) alumnos.add(a);
        }
        lc.setAlumnos(alumnos);
        return lc;
    }

    public void limpiar() {
        tableModel.setRowCount(0);
        lblPromedio.setText("Promedio del grupo: —");
    }

    // =========================================================================
    // Helpers
    // =========================================================================

    private void actualizarPromedio() {
        double suma = 0;
        int count = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            double v = parseDouble(tableModel.getValueAt(i, 1));
            if (v > 0) {
                suma += v;
                count++;
            }
        }
        if (count > 0) {
            lblPromedio.setText(String.format("Promedio del grupo: %.2f", suma / count));
        } else {
            lblPromedio.setText("Promedio del grupo: —");
        }
        tabla.repaint();
    }

    private double parseDouble(Object val) {
        if (val == null || val.toString().isBlank()) return 0;
        try { return Double.parseDouble(val.toString()); }
        catch (NumberFormatException e) { return 0; }
    }

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
