package com.tienda.controller;

import com.tienda.model.Producto;
import com.tienda.util.DataStore;
import com.tienda.view.ProductoDialog;
import com.tienda.view.ProductosPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Controlador del módulo Productos.
 * Gestiona el CRUD completo y la vista previa de imágenes.
 */
public class ProductosController {

    private final Frame         owner;
    private final ProductosPanel panel;
    private final DataStore     store;
    private final Runnable      onChanged; // notifica al MainController

    public ProductosController(Frame owner, ProductosPanel panel,
                               DataStore store, Runnable onChanged) {
        this.owner     = owner;
        this.panel     = panel;
        this.store     = store;
        this.onChanged = onChanged;
        init();
    }

    /** Carga / recarga los datos del panel. */
    public void refresh() {
        panel.setProductos(store.getProductos());
    }

    // ── Inicialización de eventos ────────────────────

    private void init() {
        // Búsqueda y filtro en tiempo real
        panel.txtBuscar.getDocument().addDocumentListener(onChange(() ->
            panel.filtrar(panel.txtBuscar.getText(),
                          (String) panel.cbCategoria.getSelectedItem())));

        panel.cbCategoria.addActionListener(e ->
            panel.filtrar(panel.txtBuscar.getText(),
                          (String) panel.cbCategoria.getSelectedItem()));

        // Vista previa de imagen al seleccionar fila
        panel.tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            String sku = panel.getSelectedSku();
            if (sku == null) return;
            store.getProductos().stream()
                .filter(p -> p.getSku().equals(sku)).findFirst()
                .ifPresent(p -> panel.setPreviewImage(p.getImagenPath()));
        });

        // CRUD
        panel.btnRefresh.addActionListener(e  -> recargar());
        panel.btnNuevo.addActionListener(e    -> nuevo());
        panel.btnEditar.addActionListener(e   -> editar());
        panel.btnEliminar.addActionListener(e -> eliminar());
    }

    // ── Operaciones ──────────────────────────────────

    private void recargar() {
        store.cargar();
        refresh();
    }

    private void nuevo() {
        ProductoDialog dlg = new ProductoDialog(owner, "Nuevo Producto", null);
        dlg.setVisible(true);
        if (!dlg.isAceptado()) return;

        Producto nuevo = dlg.getProducto();
        boolean duplicado = store.getProductos().stream()
            .anyMatch(p -> p.getSku().equalsIgnoreCase(nuevo.getSku()));

        if (duplicado) {
            error("Ya existe un producto con el SKU «" + nuevo.getSku() + "».");
            return;
        }
        store.getProductos().add(nuevo);
        store.guardarProductos();
        refresh();
        onChanged.run();
    }

    private void editar() {
        String sku = panel.getSelectedSku();
        if (sku == null) { noSel(); return; }

        Producto orig = store.getProductos().stream()
            .filter(p -> p.getSku().equals(sku)).findFirst().orElse(null);
        if (orig == null) return;

        ProductoDialog dlg = new ProductoDialog(owner, "Editar Producto — " + sku, orig);
        dlg.setVisible(true);
        if (!dlg.isAceptado()) return;

        int idx = store.getProductos().indexOf(orig);
        store.getProductos().set(idx, dlg.getProducto());
        store.guardarProductos();
        refresh();
        onChanged.run();
    }

    private void eliminar() {
        String sku = panel.getSelectedSku();
        if (sku == null) { noSel(); return; }

        int ok = JOptionPane.showConfirmDialog(owner,
            "¿Eliminar el producto con SKU «" + sku + "»?\nEsta acción no se puede deshacer.",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (ok == JOptionPane.YES_OPTION) {
            store.getProductos().removeIf(p -> p.getSku().equals(sku));
            store.guardarProductos();
            refresh();
            onChanged.run();
        }
    }

    // ── Utilería ─────────────────────────────────────

    private void noSel() {
        JOptionPane.showMessageDialog(owner, "Selecciona un producto primero.",
            "Sin selección", JOptionPane.INFORMATION_MESSAGE);
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(owner, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    static DocumentListener onChange(Runnable r) {
        return new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { r.run(); }
            public void removeUpdate(DocumentEvent e)  { r.run(); }
            public void changedUpdate(DocumentEvent e) { r.run(); }
        };
    }
}
