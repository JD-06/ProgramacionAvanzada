package view;

import model.Insumo;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Map;

public class InsumoInternalFrame extends JInternalFrame {
    public final JTable tabla;
    public final JButton botonAgregar;
    public final JButton botonModificar;
    public final JButton botonEliminar;
    public final JButton botonCerrar;
    private final DefaultTableModel modelo;

    public InsumoInternalFrame() {
        super("Insumos", true, true, true, true);
        setSize(760, 430);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.botonAgregar = new JButton("Agregar");
        this.botonModificar = new JButton("Modificar");
        this.botonEliminar = new JButton("Eliminar");
        this.botonCerrar = new JButton("Salir");
        panelBotones.add(this.botonAgregar);
        panelBotones.add(this.botonModificar);
        panelBotones.add(this.botonEliminar);
        panelBotones.add(this.botonCerrar);

        this.modelo = new DefaultTableModel(new Object[]{"Id", "Insumo", "Categoria", "IdCategoria"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tabla = new JTable(this.modelo);

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(this.tabla), BorderLayout.CENTER);
    }

    public void setInsumos(List<Insumo> insumos, Map<String, String> categorias) {
        this.modelo.setRowCount(0);
        for (Insumo insumo : insumos) {
            this.modelo.addRow(new Object[]{
                    insumo.getId(),
                    insumo.getNombre(),
                    categorias.getOrDefault(insumo.getIdCategoria(), ""),
                    insumo.getIdCategoria()
            });
        }
    }

    public String getSelectedId() {
        int row = this.tabla.getSelectedRow();
        return row >= 0 ? String.valueOf(this.modelo.getValueAt(row, 0)) : null;
    }

    public String getSelectedNombre() {
        int row = this.tabla.getSelectedRow();
        return row >= 0 ? String.valueOf(this.modelo.getValueAt(row, 1)) : null;
    }

    public String getSelectedIdCategoria() {
        int row = this.tabla.getSelectedRow();
        return row >= 0 ? String.valueOf(this.modelo.getValueAt(row, 3)) : null;
    }
}
