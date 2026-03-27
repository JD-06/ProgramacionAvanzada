package com.tienda.view;

import com.tienda.model.Producto;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Diálogo para crear o editar un Producto.
 */
public class ProductoDialog extends JDialog {

    private JTextField txtSku       = new JTextField(15);
    private JTextField txtNombre    = new JTextField(25);
    private JTextField txtCategoria = new JTextField(15);
    private JTextField txtCompra    = new JTextField(10);
    private JTextField txtGanancia  = new JTextField(8);
    private JTextField txtVenta     = new JTextField(10);
    private JTextField txtStock     = new JTextField(8);
    private JTextField txtUnidad    = new JTextField(8);
    private JTextField txtImagen    = new JTextField(25);

    private JButton btnAceptar  = ProductosPanel.accentBtn("Guardar");
    private JButton btnCancelar = ProductosPanel.secBtn("Cancelar");
    private JButton btnElegirImg = ProductosPanel.secBtn("…");

    private boolean aceptado = false;

    public ProductoDialog(Frame owner, String titulo, Producto p) {
        super(owner, titulo, true);
        setSize(500, 420);
        setResizable(false);
        setLocationRelativeTo(owner);
        buildUI();
        if (p != null) cargar(p);
        // Calcular precio venta al cambiar compra o ganancia
        txtCompra.addActionListener(e -> calcularVenta());
        txtGanancia.addActionListener(e -> calcularVenta());
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
            {"SKU *", txtSku},
            {"Nombre *", txtNombre},
            {"Categoría *", txtCategoria},
            {"Precio Compra *", txtCompra},
            {"% Ganancia", txtGanancia},
            {"Precio Venta *", txtVenta},
            {"Stock", txtStock},
            {"Unidad", txtUnidad},
            {"Imagen (ruta)", buildImgRow()},
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

        // Botones
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setBackground(Color.WHITE);
        btns.add(btnCancelar);
        btns.add(btnAceptar);
        main.add(btns, BorderLayout.SOUTH);

        btnAceptar.addActionListener((ActionEvent e) -> {
            if (validar()) { aceptado = true; dispose(); }
        });
        btnCancelar.addActionListener(e -> dispose());
        btnElegirImg.addActionListener(e -> elegirImagen());

        setContentPane(main);
    }

    private JPanel buildImgRow() {
        JPanel p = new JPanel(new BorderLayout(4, 0));
        p.setBackground(Color.WHITE);
        p.add(txtImagen, BorderLayout.CENTER);
        p.add(btnElegirImg, BorderLayout.EAST);
        return p;
    }

    private void elegirImagen() {
        JFileChooser fc = new JFileChooser("imagenes");
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            txtImagen.setText(fc.getSelectedFile().getPath());
        }
    }

    private void calcularVenta() {
        try {
            double compra   = Double.parseDouble(txtCompra.getText().trim());
            double ganancia = Double.parseDouble(txtGanancia.getText().trim());
            double venta    = compra * (1 + ganancia / 100.0);
            txtVenta.setText(String.format("%.2f", venta));
        } catch (NumberFormatException ignored) {}
    }

    private boolean validar() {
        if (txtSku.getText().trim().isEmpty())      { error("El SKU es obligatorio."); return false; }
        if (txtNombre.getText().trim().isEmpty())   { error("El nombre es obligatorio."); return false; }
        if (txtCategoria.getText().trim().isEmpty()){ error("La categoría es obligatoria."); return false; }
        try { Double.parseDouble(txtCompra.getText().trim()); }
        catch (Exception e) { error("Precio de compra inválido."); return false; }
        try { Double.parseDouble(txtVenta.getText().trim()); }
        catch (Exception e) { error("Precio de venta inválido."); return false; }
        return true;
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }

    private void cargar(Producto p) {
        txtSku.setText(p.getSku());
        txtNombre.setText(p.getNombre());
        txtCategoria.setText(p.getCategoria());
        txtCompra.setText(String.valueOf(p.getPrecioCompra()));
        txtGanancia.setText(String.valueOf(p.getPorcentajeGanancia()));
        txtVenta.setText(String.valueOf(p.getPrecioVenta()));
        txtStock.setText(String.valueOf(p.getStock()));
        txtUnidad.setText(p.getUnidad());
        txtImagen.setText(p.getImagenPath());
    }

    public boolean isAceptado() { return aceptado; }

    public Producto getProducto() {
        Producto p = new Producto();
        p.setSku(txtSku.getText().trim());
        p.setNombre(txtNombre.getText().trim());
        p.setCategoria(txtCategoria.getText().trim());
        p.setPrecioCompra(parseD(txtCompra.getText()));
        p.setPorcentajeGanancia(parseD(txtGanancia.getText()));
        p.setPrecioVenta(parseD(txtVenta.getText()));
        p.setStock(parseInt(txtStock.getText()));
        p.setUnidad(txtUnidad.getText().trim());
        p.setImagenPath(txtImagen.getText().trim());
        return p;
    }

    private double parseD(String s) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return 0; }
    }
    private int parseInt(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return 0; }
    }
}
