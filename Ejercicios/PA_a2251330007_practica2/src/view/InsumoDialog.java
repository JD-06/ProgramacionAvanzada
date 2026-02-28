package view;

import model.Categoria;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.List;

public class InsumoDialog extends JDialog {
    private final JTextField campoId;
    private final JTextField campoNombre;
    private final JComboBox<CategoriaItem> comboCategorias;
    private boolean accepted;

    public InsumoDialog(Window owner, String title, String id, String nombre, String selectedCategoriaId, List<Categoria> categorias, boolean editableId) {
        super(owner, title, ModalityType.APPLICATION_MODAL);
        setSize(420, 240);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Id"), gbc);

        this.campoId = new JTextField(id == null ? "" : id, 20);
        this.campoId.setEditable(editableId);
        gbc.gridx = 1;
        panel.add(this.campoId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Insumo"), gbc);

        this.campoNombre = new JTextField(nombre == null ? "" : nombre, 20);
        gbc.gridx = 1;
        panel.add(this.campoNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Categoria"), gbc);

        this.comboCategorias = new JComboBox<>();
        for (Categoria categoria : categorias) {
            this.comboCategorias.addItem(new CategoriaItem(categoria.getId(), categoria.getNombre()));
        }
        if (selectedCategoriaId != null) {
            for (int i = 0; i < this.comboCategorias.getItemCount(); i++) {
                CategoriaItem item = this.comboCategorias.getItemAt(i);
                if (item.id.equals(selectedCategoriaId)) {
                    this.comboCategorias.setSelectedIndex(i);
                    break;
                }
            }
        }
        gbc.gridx = 1;
        panel.add(this.comboCategorias, gbc);

        JButton botonGuardar = new JButton("Guardar");
        JButton botonCancelar = new JButton("Cancelar");
        botonGuardar.addActionListener(e -> onSave());
        botonCancelar.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(botonGuardar, gbc);
        gbc.gridx = 1;
        panel.add(botonCancelar, gbc);

        setContentPane(panel);
    }

    private void onSave() {
        if (getIdValue().isEmpty() || getNombreValue().isEmpty() || getCategoriaId() == null) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos");
            return;
        }
        this.accepted = true;
        dispose();
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getIdValue() {
        return this.campoId.getText().trim();
    }

    public String getNombreValue() {
        return this.campoNombre.getText().trim();
    }

    public String getCategoriaId() {
        CategoriaItem item = (CategoriaItem) this.comboCategorias.getSelectedItem();
        return item == null ? null : item.id;
    }

    private static class CategoriaItem {
        private final String id;
        private final String nombre;

        private CategoriaItem(String id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }
}
