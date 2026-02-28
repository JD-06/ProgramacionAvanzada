package view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

public class ObraDialog extends JDialog {
    private final String id;
    private final JTextField campoNombre;
    private final JTextArea campoDescripcion;
    private boolean accepted;

    public ObraDialog(Window owner, String title, String id, String nombre, String descripcion) {
        super(owner, title, ModalityType.APPLICATION_MODAL);
        this.id = id;
        setSize(450, 290);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Obra"), gbc);

        this.campoNombre = new JTextField(nombre == null ? "" : nombre, 24);
        gbc.gridx = 1;
        panel.add(this.campoNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Descripcion"), gbc);

        this.campoDescripcion = new JTextArea(descripcion == null ? "" : descripcion, 5, 24);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(this.campoDescripcion), gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
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
        if (getNombreValue().isEmpty() || getDescripcionValue().isEmpty()) {
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
        return this.id;
    }

    public String getNombreValue() {
        return this.campoNombre.getText().trim();
    }

    public String getDescripcionValue() {
        return this.campoDescripcion.getText().trim();
    }
}
