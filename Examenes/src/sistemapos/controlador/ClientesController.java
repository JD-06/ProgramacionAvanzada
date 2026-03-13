package sistemapos.controlador;

import sistemapos.modelo.Cliente;
import sistemapos.modelo.GestorClientes;
import sistemapos.vista.ClientesFrame;
import javax.swing.*;

public class ClientesController {

    private final ClientesFrame vista;
    private final GestorClientes gestor;

    public ClientesController(ClientesFrame vista, GestorClientes gestor) {
        this.vista = vista;
        this.gestor = gestor;
        recargarDatos();
        registrarEventos();
    }

    public void recargarDatos() {
        vista.modeloTabla.setRowCount(0);
        for (Cliente c : gestor.getLista()) {
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
            JOptionPane.showMessageDialog(vista, "Ingrese el nombre del socio.", "Validacion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        gestor.insertar(new Cliente(0, nombre, true));
        recargarDatos();
        vista.notificarActualizacion();
        JOptionPane.showMessageDialog(vista, "Socio guardado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
    }
}
