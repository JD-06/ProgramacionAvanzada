package com.tienda.controller;

import com.tienda.model.Producto;
import com.tienda.util.DataStore;
import com.tienda.view.InventarioPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Controlador del módulo Inventario.
 * Permite ver el stock actual con alertas visuales y ajustarlo manualmente.
 */
public class InventarioController {

    private final Frame          owner;
    private final InventarioPanel panel;
    private final DataStore      store;
    private final Runnable       onChanged;

    public InventarioController(Frame owner, InventarioPanel panel,
                                DataStore store, Runnable onChanged) {
        this.owner     = owner;
        this.panel     = panel;
        this.store     = store;
        this.onChanged = onChanged;
        init();
    }

    public void refresh() {
        panel.setProductos(store.getProductos());
    }

    // ── Inicialización de eventos ────────────────────

    private void init() {
        panel.txtBuscar.getDocument().addDocumentListener(
            ProductosController.onChange(() -> panel.filtrar(panel.txtBuscar.getText())));

        panel.btnRefresh.addActionListener(e -> {
            store.cargar();
            refresh();
        });

        panel.btnAjustar.addActionListener(e -> ajustarStock());
    }

    // ── Ajuste de stock ──────────────────────────────

    private void ajustarStock() {
        String sku = panel.getSelectedSku();
        if (sku == null) {
            JOptionPane.showMessageDialog(owner, "Selecciona un producto primero.",
                "Sin selección", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Producto p = store.getProductos().stream()
            .filter(x -> x.getSku().equals(sku)).findFirst().orElse(null);
        if (p == null) return;

        // Diálogo de ajuste
        JPanel form = new JPanel(new java.awt.GridLayout(3, 2, 8, 6));
        form.add(new JLabel("Producto:"));
        form.add(new JLabel(p.getNombre()));
        form.add(new JLabel("Stock actual:"));
        form.add(new JLabel(String.valueOf(p.getStock()) + " " + p.getUnidad()));
        form.add(new JLabel("Nuevo stock:"));
        JTextField txtNuevo = new JTextField(String.valueOf(p.getStock()), 8);
        form.add(txtNuevo);

        int result = JOptionPane.showConfirmDialog(owner, form,
            "Ajustar Stock — " + sku, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) return;

        try {
            int nuevo = Integer.parseInt(txtNuevo.getText().trim());
            if (nuevo < 0) throw new NumberFormatException("negativo");
            p.setStock(nuevo);
            store.guardarProductos();
            refresh();
            onChanged.run();
            JOptionPane.showMessageDialog(owner,
                "Stock actualizado a " + nuevo + " " + p.getUnidad() + ".",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(owner,
                "Ingresa un número entero mayor o igual a 0.",
                "Valor inválido", JOptionPane.ERROR_MESSAGE);
        }
    }
}
