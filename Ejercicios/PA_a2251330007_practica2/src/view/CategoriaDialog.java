package view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

public class CategoriaDialog extends JDialog {
    private final JTextField campoId;
    private final JTextField campoNombre;
    private boolean accepted;

    public CategoriaDialog(Window owner, String title, String id, String nombre, boolean editableId) {
        super(owner, title, ModalityType.APPLICATION_MODAL);
        setSize(380, 200);
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
        panel.add(new JLabel("Categoria"), gbc);

        this.campoNombre = new JTextField(nombre == null ? "" : nombre, 20);
        gbc.gridx = 1;
        panel.add(this.campoNombre, gbc);

        JButton botonGuardar = new JButton("Guardar");
        JButton botonCancelar = new JButton("Cancelar");
        botonGuardar.addActionListener(e -> onSave());
        botonCancelar.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(botonGuardar, gbc);
        gbc.gridx = 1;
        panel.add(botonCancelar, gbc);

        setContentPane(panel);
    }

    private void onSave() {
        if (getIdValue().isEmpty() || getNombreValue().isEmpty()) {
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
}
