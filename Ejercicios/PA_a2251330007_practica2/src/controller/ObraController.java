package controller;

import model.Obra;
import view.ObraDialog;
import view.ObraInternalFrame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ObraController {
    private final ObraInternalFrame view;
    private final AppService service;

    public ObraController(ObraInternalFrame view, AppService service) {
        this.view = view;
        this.service = service;

        this.view.botonAgregar.addActionListener(e -> addObra());
        this.view.botonModificar.addActionListener(e -> editObra());
        this.view.botonEliminar.addActionListener(e -> deleteObra());
        this.view.botonCerrar.addActionListener(e -> this.view.dispose());

        refresh();
    }

    private void refresh() {
        this.view.setObras(this.service.getObras());
    }

    private void addObra() {
        ObraDialog dialog = new ObraDialog(
                SwingUtilities.getWindowAncestor(this.view),
                "Agregar Obra",
                this.service.generateNextObraId(),
                "",
                ""
        );
        dialog.setVisible(true);
        if (!dialog.isAccepted()) {
            return;
        }
        boolean ok = this.service.addObra(new Obra(dialog.getIdValue(), dialog.getNombreValue(), dialog.getDescripcionValue()));
        if (!ok) {
            JOptionPane.showMessageDialog(this.view, "No se pudo guardar la obra");
            return;
        }
        refresh();
    }

    private void editObra() {
        String id = this.view.getSelectedId();
        if (id == null) {
            JOptionPane.showMessageDialog(this.view, "Selecciona una obra");
            return;
        }
        ObraDialog dialog = new ObraDialog(
                SwingUtilities.getWindowAncestor(this.view),
                "Modificar Obra",
                id,
                this.view.getSelectedNombre(),
                this.view.getSelectedDescripcion()
        );
        dialog.setVisible(true);
        if (!dialog.isAccepted()) {
            return;
        }
        this.service.updateObra(new Obra(id, dialog.getNombreValue(), dialog.getDescripcionValue()));
        refresh();
    }

    private void deleteObra() {
        String id = this.view.getSelectedId();
        if (id == null) {
            JOptionPane.showMessageDialog(this.view, "Selecciona una obra");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this.view, "Eliminar obra " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        this.service.deleteObra(id);
        refresh();
    }
}
