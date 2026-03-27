package com.tienda.view;

import com.tienda.model.Proveedor;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProveedorDialog extends JDialog {

    private JTextField txtId        = new JTextField(10);
    private JTextField txtNombre    = new JTextField(25);
    private JTextField txtContacto  = new JTextField(20);
    private JTextField txtTelefono  = new JTextField(15);
    private JTextField txtEmail     = new JTextField(20);
    private JTextField txtCategoria = new JTextField(15);
    private JTextField txtDireccion = new JTextField(25);

    private JButton btnAceptar  = ProductosPanel.accentBtn("Guardar");
    private JButton btnCancelar = ProductosPanel.secBtn("Cancelar");

    private boolean aceptado = false;

    public ProveedorDialog(Frame owner, String titulo, Proveedor p) {
        super(owner, titulo, true);
        setSize(460, 380);
        setResizable(false);
        setLocationRelativeTo(owner);
        buildUI();
        if (p != null) cargar(p);
    }

    private void buildUI() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(new EmptyBorder(16, 20, 12, 20));
        main.setBackground(Color.WHITE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        Object[][] rows = {
            {"ID *",         txtId},
            {"Nombre *",     txtNombre},
            {"Contacto",     txtContacto},
            {"Teléfono",     txtTelefono},
            {"Email",        txtEmail},
            {"Categoría",    txtCategoria},
            {"Dirección",    txtDireccion},
        };

        for (int i = 0; i < rows.length; i++) {
            g.gridx = 0; g.gridy = i; g.weightx = 0;
            JLabel lbl = new JLabel(rows[i][0].toString());
            lbl.setFont(new Font("Arial", Font.BOLD, 12));
            form.add(lbl, g);
            g.gridx = 1; g.weightx = 1;
            form.add((Component) rows[i][1], g);
        }

        main.add(form, BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setBackground(Color.WHITE);
        btns.add(btnCancelar);
        btns.add(btnAceptar);
        main.add(btns, BorderLayout.SOUTH);

        btnAceptar.addActionListener(e -> {
            if (validar()) { aceptado = true; dispose(); }
        });
        btnCancelar.addActionListener(e -> dispose());

        setContentPane(main);
    }

    private boolean validar() {
        if (txtId.getText().trim().isEmpty())     { error("El ID es obligatorio."); return false; }
        if (txtNombre.getText().trim().isEmpty()) { error("El nombre es obligatorio."); return false; }
        return true;
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }

    private void cargar(Proveedor p) {
        txtId.setText(p.getId());
        txtNombre.setText(p.getNombre());
        txtContacto.setText(p.getContacto());
        txtTelefono.setText(p.getTelefono());
        txtEmail.setText(p.getEmail());
        txtCategoria.setText(p.getCategoria());
        txtDireccion.setText(p.getDireccion());
    }

    public boolean isAceptado() { return aceptado; }

    public Proveedor getProveedor() {
        Proveedor p = new Proveedor();
        p.setId(txtId.getText().trim());
        p.setNombre(txtNombre.getText().trim());
        p.setContacto(txtContacto.getText().trim());
        p.setTelefono(txtTelefono.getText().trim());
        p.setEmail(txtEmail.getText().trim());
        p.setCategoria(txtCategoria.getText().trim());
        p.setDireccion(txtDireccion.getText().trim());
        return p;
    }
}
