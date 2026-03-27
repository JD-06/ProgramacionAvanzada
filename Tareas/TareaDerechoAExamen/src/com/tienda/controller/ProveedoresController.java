package com.tienda.controller;

import com.tienda.model.Proveedor;
import com.tienda.util.DataStore;
import com.tienda.view.ProveedorDialog;
import com.tienda.view.ProveedoresPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Controlador del módulo Proveedores.
 * CRUD completo con persistencia en JSON + CSV.
 */
public class ProveedoresController {

    private final Frame            owner;
    private final ProveedoresPanel panel;
    private final DataStore        store;

    public ProveedoresController(Frame owner, ProveedoresPanel panel, DataStore store) {
        this.owner = owner;
        this.panel = panel;
        this.store = store;
        init();
    }

    public void refresh() {
        panel.setProveedores(store.getProveedores());
    }

    // ── Inicialización de eventos ────────────────────

    private void init() {
        panel.txtBuscar.getDocument().addDocumentListener(
            ProductosController.onChange(() -> panel.filtrar(panel.txtBuscar.getText())));

        panel.btnRefresh.addActionListener(e -> {
            store.cargar();
            refresh();
        });

        panel.btnNuevo.addActionListener(e    -> nuevo());
        panel.btnEditar.addActionListener(e   -> editar());
        panel.btnEliminar.addActionListener(e -> eliminar());
    }

    // ── CRUD ─────────────────────────────────────────

    private void nuevo() {
        ProveedorDialog dlg = new ProveedorDialog(owner, "Nuevo Proveedor", null);
        dlg.setVisible(true);
        if (!dlg.isAceptado()) return;

        Proveedor nuevo = dlg.getProveedor();
        boolean dup = store.getProveedores().stream()
            .anyMatch(p -> p.getId().equalsIgnoreCase(nuevo.getId()));
        if (dup) {
            error("Ya existe un proveedor con el ID «" + nuevo.getId() + "».");
            return;
        }
        store.getProveedores().add(nuevo);
        store.guardarProveedores();
        refresh();
    }

    private void editar() {
        String id = panel.getSelectedId();
        if (id == null) { noSel(); return; }

        Proveedor orig = store.getProveedores().stream()
            .filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if (orig == null) return;

        ProveedorDialog dlg = new ProveedorDialog(owner, "Editar Proveedor — " + id, orig);
        dlg.setVisible(true);
        if (!dlg.isAceptado()) return;

        int idx = store.getProveedores().indexOf(orig);
        store.getProveedores().set(idx, dlg.getProveedor());
        store.guardarProveedores();
        refresh();
    }

    private void eliminar() {
        String id = panel.getSelectedId();
        if (id == null) { noSel(); return; }

        int ok = JOptionPane.showConfirmDialog(owner,
            "¿Eliminar el proveedor con ID «" + id + "»?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (ok == JOptionPane.YES_OPTION) {
            store.getProveedores().removeIf(p -> p.getId().equals(id));
            store.guardarProveedores();
            refresh();
        }
    }

    // ── Utilería ─────────────────────────────────────

    private void noSel() {
        JOptionPane.showMessageDialog(owner, "Selecciona un proveedor primero.",
            "Sin selección", JOptionPane.INFORMATION_MESSAGE);
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(owner, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
