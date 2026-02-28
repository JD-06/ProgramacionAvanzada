package controller;

import model.Categoria;
import view.CategoriaDialog;
import view.CategoriaInternalFrame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class CategoriaController {
    private final CategoriaInternalFrame view;
    private final AppService service;

    public CategoriaController(CategoriaInternalFrame view, AppService service) {
        this.view = view;
        this.service = service;

        this.view.botonAgregar.addActionListener(e -> addCategoria());
        this.view.botonModificar.addActionListener(e -> editCategoria());
        this.view.botonEliminar.addActionListener(e -> deleteCategoria());
        this.view.botonCerrar.addActionListener(e -> this.view.dispose());

        refresh();
    }

    private void refresh() {
        this.view.setCategorias(this.service.getCategorias());
    }

    private void addCategoria() {
        CategoriaDialog dialog = new CategoriaDialog(
                SwingUtilities.getWindowAncestor(this.view),
                "Agregar Categoria",
                "",
                "",
                true
        );
        dialog.setVisible(true);
        if (!dialog.isAccepted()) {
            return;
        }
        boolean ok = this.service.addCategoria(new Categoria(dialog.getIdValue(), dialog.getNombreValue()));
        if (!ok) {
            JOptionPane.showMessageDialog(this.view, "Ya existe el id de la categoria");
            return;
        }
        refresh();
    }

    private void editCategoria() {
        String id = this.view.getSelectedId();
        if (id == null) {
            JOptionPane.showMessageDialog(this.view, "Selecciona una categoria");
            return;
        }
        CategoriaDialog dialog = new CategoriaDialog(
                SwingUtilities.getWindowAncestor(this.view),
                "Modificar Categoria",
                id,
                this.view.getSelectedNombre(),
                false
        );
        dialog.setVisible(true);
        if (!dialog.isAccepted()) {
            return;
        }
        this.service.updateCategoria(new Categoria(id, dialog.getNombreValue()));
        refresh();
    }

    private void deleteCategoria() {
        String id = this.view.getSelectedId();
        if (id == null) {
            JOptionPane.showMessageDialog(this.view, "Selecciona una categoria");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this.view, "Eliminar categoria " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        boolean ok = this.service.deleteCategoria(id);
        if (!ok) {
            JOptionPane.showMessageDialog(this.view, "No se puede eliminar una categoria con insumos asociados");
            return;
        }
        refresh();
    }
}
