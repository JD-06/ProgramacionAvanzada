package controller;

import model.Categoria;
import model.Insumo;
import view.InsumoDialog;
import view.InsumoInternalFrame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsumoController {
    private final InsumoInternalFrame view;
    private final AppService service;

    public InsumoController(InsumoInternalFrame view, AppService service) {
        this.view = view;
        this.service = service;

        this.view.botonAgregar.addActionListener(e -> addInsumo());
        this.view.botonModificar.addActionListener(e -> editInsumo());
        this.view.botonEliminar.addActionListener(e -> deleteInsumo());
        this.view.botonCerrar.addActionListener(e -> this.view.dispose());

        refresh();
    }

    private void refresh() {
        Map<String, String> names = new HashMap<>();
        for (Categoria categoria : this.service.getCategorias()) {
            names.put(categoria.getId(), categoria.getNombre());
        }
        this.view.setInsumos(this.service.getInsumos(), names);
    }

    private void addInsumo() {
        List<Categoria> categorias = this.service.getCategorias();
        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(this.view, "Primero registra categorias");
            return;
        }
        InsumoDialog dialog = new InsumoDialog(
                SwingUtilities.getWindowAncestor(this.view),
                "Agregar Insumo",
                "",
                "",
                null,
                categorias,
                true
        );
        dialog.setVisible(true);
        if (!dialog.isAccepted()) {
            return;
        }
        boolean ok = this.service.addInsumo(new Insumo(dialog.getIdValue(), dialog.getNombreValue(), dialog.getCategoriaId()));
        if (!ok) {
            JOptionPane.showMessageDialog(this.view, "Id duplicado o categoria invalida");
            return;
        }
        refresh();
    }

    private void editInsumo() {
        String id = this.view.getSelectedId();
        if (id == null) {
            JOptionPane.showMessageDialog(this.view, "Selecciona un insumo");
            return;
        }
        InsumoDialog dialog = new InsumoDialog(
                SwingUtilities.getWindowAncestor(this.view),
                "Modificar Insumo",
                id,
                this.view.getSelectedNombre(),
                this.view.getSelectedIdCategoria(),
                this.service.getCategorias(),
                false
        );
        dialog.setVisible(true);
        if (!dialog.isAccepted()) {
            return;
        }
        this.service.updateInsumo(new Insumo(id, dialog.getNombreValue(), dialog.getCategoriaId()));
        refresh();
    }

    private void deleteInsumo() {
        String id = this.view.getSelectedId();
        if (id == null) {
            JOptionPane.showMessageDialog(this.view, "Selecciona un insumo");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this.view, "Eliminar insumo " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        this.service.deleteInsumo(id);
        refresh();
    }
}
