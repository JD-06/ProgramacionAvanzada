package sistemapos.controlador;

import sistemapos.modelo.Cajero;
import sistemapos.modelo.GestorCajeros;
import sistemapos.vista.CajerosFrame;
import javax.swing.*;

public class CajerosController {

    private final CajerosFrame vista;
    private final GestorCajeros gestor;

    public CajerosController(CajerosFrame vista, GestorCajeros gestor) {
        this.vista = vista;
        this.gestor = gestor;
        recargarDatos();
        registrarEventos();
    }

    public void recargarDatos() {
        vista.modeloTabla.setRowCount(0);
        for (Cajero c : gestor.getLista()) {
            vista.modeloTabla.addRow(new Object[]{
                c.getId(),
                c.getNombre()
            });
        }
        vista.txtNombre.setText("");
    }

    private void registrarEventos() {
        vista.btnGuardar.addActionListener(e -> guardar());
        vista.btnLimpiar.addActionListener(e -> recargarDatos());
    }

    private void guardar() {
        String nombre = vista.txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el nombre del cajero.", "Validacion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        gestor.insertar(new Cajero(0, nombre));
        recargarDatos();
        vista.notificarActualizacion();
        JOptionPane.showMessageDialog(vista, "Cajero guardado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
    }
}
