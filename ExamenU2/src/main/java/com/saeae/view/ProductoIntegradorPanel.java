package com.saeae.view;

import com.saeae.model.CriterioEvaluacion;
import com.saeae.model.DatosInstrumento;
import com.saeae.model.Evaluacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel — Instrumento 1: Producto Integrador de Aprendizaje.
 *
 * Corresponde a la hoja "ReporteProductoIntegrador" de examen.xlsx.
 *
 * Encabezado: asignatura (C3), grupo (G3), docente (C4), periodo (C5),
 *             actividad (E5), atributo (C6), criterioDesempenio (C7),
 *             indicadores x3 (C8-C10)
 *
 * Tabla de criterios: 6 filas (F17-F22=calificacion, G17-G22=observacion)
 * Promedio: formula AVERAGE(F17:F22) en F23
 */
public class ProductoIntegradorPanel extends JPanel {

    // -- Campos de encabezado --
    private JComboBox<String> cbPeriodo;
    private JTextField       txtActividad;
    private JComboBox<String> cbAtributo;       // selector cargado desde Datosbase.xlsx
    private JTextArea        txtAtributo;       // texto completo (editable)
    private JTextField       txtCriterioDesempenio;
    private JComboBox<String> cbNivelDesempenio; // nivel 1-4
    private JTextField[] txtIndicadores = new JTextField[3];

    // -- Tabla de criterios (6 filas) --
    private DefaultTableModel tablaCriteriosModel;
    private JTable tablaCriterios; // campo para poder confirmar el editor activo
    private JLabel lblPromedio;

    public ProductoIntegradorPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        setBackground(new Color(245, 245, 248));

        add(construirPanelEncabezado(), BorderLayout.NORTH);
        add(construirPanelCriterios(), BorderLayout.CENTER);
    }

    // =========================================================================
    // Construccion de sub-paneles
    // =========================================================================

    private JPanel construirPanelEncabezado() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 248));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 32, 96), 1),
                "Encabezado del Instrumento",
                0, 0, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 32, 96)));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Periodo escolar
        g.gridy = row++; g.gridx = 0;
        panel.add(etiqueta("Periodo escolar (C5):"), g);
        g.gridx = 1; g.fill = GridBagConstraints.NONE;
        cbPeriodo = new JComboBox<>(generarPeriodos());
        cbPeriodo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbPeriodo.setEditable(true); // permite escribir uno personalizado
        cbPeriodo.setSelectedItem(periodoActual());
        panel.add(cbPeriodo, g);

        // Actividad
        agregarCampo(panel, g, row++, "Actividad evaluada (E5):", txtActividad = new JTextField(28));

        // Selector de atributo (JComboBox — se puebla desde Datosbase.xlsx)
        g.gridy = row++; g.gridx = 0;
        panel.add(etiqueta("Atributo de egreso (C6):"), g);
        g.gridx = 1; g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1.0;
        cbAtributo = new JComboBox<>();
        cbAtributo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbAtributo.addItem("— Sin datos de Datosbase.xlsx —");
        panel.add(cbAtributo, g);
        g.fill = GridBagConstraints.NONE; g.weightx = 0; g.anchor = GridBagConstraints.WEST;

        // Texto completo del atributo seleccionado (editable para personalizacion)
        g.gridy = row++; g.gridx = 0; g.anchor = GridBagConstraints.NORTHWEST;
        panel.add(etiqueta("  Texto completo:"), g);
        g.gridx = 1; g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1.0;
        txtAtributo = new JTextArea(3, 35);
        txtAtributo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtAtributo.setLineWrap(true); txtAtributo.setWrapStyleWord(true);
        txtAtributo.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        panel.add(new JScrollPane(txtAtributo), g);
        g.fill = GridBagConstraints.NONE; g.weightx = 0; g.anchor = GridBagConstraints.WEST;

        // Al seleccionar un atributo del combo, llenar el text area automaticamente
        cbAtributo.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                String sel = (String) cbAtributo.getSelectedItem();
                if (sel != null && !sel.startsWith("—")) {
                    txtAtributo.setText(sel);
                }
            }
        });

        // Criterio de desempenio
        agregarCampo(panel, g, row++, "Criterio de desempenio (C7):", txtCriterioDesempenio = new JTextField(35));

        // Nivel de desempenio (J8 / F5 en el Excel)
        g.gridy = row++; g.gridx = 0;
        panel.add(etiqueta("Nivel de desempenio (J8):"), g);
        g.gridx = 1; g.fill = GridBagConstraints.NONE; g.weightx = 0;
        cbNivelDesempenio = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        cbNivelDesempenio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbNivelDesempenio.setSelectedItem("1");
        cbNivelDesempenio.setToolTipText("Nivel de desempenio del atributo (1=Basico, 2=En proceso, 3=Competente, 4=Destacado)");
        panel.add(cbNivelDesempenio, g);
        g.fill = GridBagConstraints.NONE; g.weightx = 0; g.anchor = GridBagConstraints.WEST;

        // Indicadores x3
        for (int i = 0; i < 3; i++) {
            txtIndicadores[i] = new JTextField(35);
            agregarCampo(panel, g, row++, "Indicador " + (i + 1) + " (C" + (8 + i) + "):", txtIndicadores[i]);
        }

        return panel;
    }

    private JPanel construirPanelCriterios() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.setBackground(new Color(245, 245, 248));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 32, 96), 1),
                "Criterios de Evaluacion (Filas 17-22 del Excel)",
                0, 0, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 32, 96)));

        String[] cols = {"N°", "Calificacion (0-10)", "Observacion"};
        tablaCriteriosModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c > 0; }
        };

        for (int i = 1; i <= 6; i++) {
            tablaCriteriosModel.addRow(new Object[]{String.valueOf(i), "0", ""});
        }

        tablaCriterios = new JTable(tablaCriteriosModel);
        // Confirmar (no cancelar) la edicion activa cuando el usuario hace clic fuera de la tabla
        tablaCriterios.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        tablaCriterios.setRowHeight(26);
        tablaCriterios.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaCriterios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaCriterios.getTableHeader().setBackground(new Color(0, 32, 96));
        tablaCriterios.getTableHeader().setForeground(Color.WHITE);
        tablaCriterios.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablaCriterios.getColumnModel().getColumn(1).setPreferredWidth(130);
        tablaCriterios.getColumnModel().getColumn(2).setPreferredWidth(350);
        tablaCriterios.getColumnModel().getColumn(1).setCellEditor(new CommittingCellEditor());

        lblPromedio = new JLabel("Promedio (F23): —");
        lblPromedio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPromedio.setForeground(new Color(0, 32, 96));

        tablaCriteriosModel.addTableModelListener(e -> actualizarPromedio());

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setBackground(new Color(245, 245, 248));
        south.add(lblPromedio);

        panel.add(new JScrollPane(tablaCriterios), BorderLayout.CENTER);
        panel.add(south, BorderLayout.SOUTH);
        return panel;
    }

    // =========================================================================
    // Interfaz View: cargar / obtener / limpiar
    // =========================================================================

    public void cargarDatos(Evaluacion eval) {
        DatosInstrumento d = eval.getDatosInstrumento();
        if (d == null) return;

        cbPeriodo.setSelectedItem(nvl(d.getPeriodo()));
        txtActividad.setText(nvl(d.getActividad()));
        txtAtributo.setText(nvl(d.getAtributo()));
        txtCriterioDesempenio.setText(nvl(d.getCriterioDesempenio()));
        cbNivelDesempenio.setSelectedItem(nvl(d.getNivelDesempenio()));

        List<String> inds = d.getIndicadores();
        for (int i = 0; i < 3; i++) {
            txtIndicadores[i].setText((inds != null && i < inds.size()) ? nvl(inds.get(i)) : "");
        }

        List<CriterioEvaluacion> criterios = d.getCriterios();
        for (int i = 0; i < 6; i++) {
            if (criterios != null && i < criterios.size()) {
                tablaCriteriosModel.setValueAt(String.valueOf(criterios.get(i).getCalificacion()), i, 1);
                tablaCriteriosModel.setValueAt(nvl(criterios.get(i).getObservacion()), i, 2);
            }
        }
    }

    /**
     * Puebla el combo con los atributos disponibles para la asignatura seleccionada.
     * Llamado automaticamente cuando cambia el combo de materia en MainView.
     */
    public void setAtributos(java.util.List<String> atributos) {
        cbAtributo.removeAllItems();
        if (atributos == null || atributos.isEmpty()) {
            cbAtributo.addItem("— Sin atributos para esta asignatura —");
            return;
        }
        for (String a : atributos) cbAtributo.addItem(a);
        // Seleccionar el primero y llenar el text area automaticamente
        cbAtributo.setSelectedIndex(0);
    }

    /** Establece el texto del atributo directamente (usado al cargar desde JSON). */
    public void setAtributo(String atributo) {
        txtAtributo.setText(nvl(atributo));
        // Intentar seleccionar el item correspondiente en el combo
        for (int i = 0; i < cbAtributo.getItemCount(); i++) {
            if (nvl(atributo).equals(cbAtributo.getItemAt(i))) {
                cbAtributo.setSelectedIndex(i);
                return;
            }
        }
    }

    public DatosInstrumento getDatos() {
        // Confirmar cualquier edicion activa en la tabla antes de leer valores
        if (tablaCriterios != null && tablaCriterios.isEditing()) {
            tablaCriterios.getCellEditor().stopCellEditing();
        }

        DatosInstrumento d = new DatosInstrumento();
        d.setFecha(LocalDate.now().toString());
        d.setPeriodo(cbPeriodo.getSelectedItem() != null ? cbPeriodo.getSelectedItem().toString().trim() : "");
        d.setActividad(txtActividad.getText().trim());
        d.setAtributo(txtAtributo.getText().trim());
        d.setCriterioDesempenio(txtCriterioDesempenio.getText().trim());
        d.setNivelDesempenio(cbNivelDesempenio.getSelectedItem() != null
                ? cbNivelDesempenio.getSelectedItem().toString() : "1");

        List<String> indicadores = new ArrayList<>();
        for (JTextField t : txtIndicadores) indicadores.add(t.getText().trim());
        d.setIndicadores(indicadores);

        List<CriterioEvaluacion> criterios = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            CriterioEvaluacion c = new CriterioEvaluacion(i + 1);
            try {
                c.setCalificacion(Double.parseDouble(tablaCriteriosModel.getValueAt(i, 1).toString().trim()));
            } catch (NumberFormatException e) {
                c.setCalificacion(0);
            }
            Object obs = tablaCriteriosModel.getValueAt(i, 2);
            c.setObservacion(obs != null ? obs.toString().trim() : "");
            criterios.add(c);
        }
        d.setCriterios(criterios);
        return d;
    }

    public void limpiar() {
        cbPeriodo.setSelectedItem(periodoActual());
        txtActividad.setText("");
        cbAtributo.setSelectedIndex(0);
        txtAtributo.setText("");
        txtCriterioDesempenio.setText("");
        cbNivelDesempenio.setSelectedItem("1");
        for (JTextField t : txtIndicadores) t.setText("");
        for (int i = 0; i < 6; i++) {
            tablaCriteriosModel.setValueAt("0", i, 1);
            tablaCriteriosModel.setValueAt("", i, 2);
        }
        lblPromedio.setText("Promedio (F23): —");
    }

    // =========================================================================
    // Helpers
    // =========================================================================

    private void agregarCampo(JPanel p, GridBagConstraints g, int row, String label, JTextField field) {
        g.gridy = row; g.gridx = 0;
        p.add(etiqueta(label), g);
        g.gridx = 1; g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1.0;
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        p.add(field, g);
        g.fill = GridBagConstraints.NONE; g.weightx = 0;
    }

    private JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return l;
    }

    private void actualizarPromedio() {
        double suma = 0; int count = 0;
        for (int i = 0; i < 6; i++) {
            Object v = tablaCriteriosModel.getValueAt(i, 1);
            if (v != null && !v.toString().isBlank()) {
                try { double cal = Double.parseDouble(v.toString()); suma += cal; count++; }
                catch (NumberFormatException ignored) {}
            }
        }
        lblPromedio.setText(count > 0
                ? String.format("Promedio (F23): %.2f", suma / count)
                : "Promedio (F23): —");
    }

    /** Genera la lista de periodos: año-1 a año+1, tres ciclos por año. */
    private static String[] generarPeriodos() {
        int anio = java.time.LocalDate.now().getYear();
        java.util.List<String> periodos = new java.util.ArrayList<>();
        for (int a = anio - 1; a <= anio + 1; a++) {
            periodos.add(a + "-1");
            periodos.add(a + "-2");
            periodos.add(a + "-3");
        }
        return periodos.toArray(new String[0]);
    }

    /** Devuelve el periodo actual segun el mes del sistema. */
    private static String periodoActual() {
        java.time.LocalDate hoy = java.time.LocalDate.now();
        int ciclo = hoy.getMonthValue() <= 4 ? 1 : hoy.getMonthValue() <= 8 ? 2 : 3;
        return hoy.getYear() + "-" + ciclo;
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
