package sistemapos.controlador;

import sistemapos.modelo.GestorProductos;
import sistemapos.modelo.Producto;
import sistemapos.vista.InventarioFrame;
import javax.swing.*;
import java.util.ArrayList;

public class InventarioController {

    private final InventarioFrame vista;
    private final GestorProductos gestor;

    public InventarioController(InventarioFrame vista, GestorProductos gestor) {
        this.vista  = vista;
        this.gestor = gestor;
        recargarDatos();
        registrarEventos();
    }

    public void recargarDatos() {
        vista.txtFiltroId.setText("");
        vista.txtFiltroNombre.setText("");
        vista.cmbTipo.setSelectedIndex(0);
        vista.rbTodos.setSelected(true);
        cargarTabla(gestor.getLista());
    }

    private void registrarEventos() {

        vista.btnBuscar.addActionListener(e -> buscar());
        vista.btnLimpiarFiltros.addActionListener(e -> recargarDatos());

        vista.btnCrearNuevo.addActionListener(e ->
            JOptionPane.showMessageDialog(vista,
                "Abre la ventana de Productos para crear un nuevo registro.",
                "Informacion", JOptionPane.INFORMATION_MESSAGE));

        vista.btnModificar.addActionListener(e -> modificar());
        vista.btnEliminar.addActionListener(e  -> eliminar());
    }

    private void buscar() {
        String txtId     = vista.txtFiltroId.getText().trim();
        String txtNombre = vista.txtFiltroNombre.getText().trim();
        String tipo      = (String) vista.cmbTipo.getSelectedItem();
        String estadoFiltro = vista.rbDisponible.isSelected() ? "Activo"
                            : vista.rbAgotado.isSelected()    ? "Agotado"
                            : "Todos";

        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto p : gestor.getLista()) {
            if (!txtId.isEmpty()) {
                try {
                    if (p.getId() != Integer.parseInt(txtId)) continue;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vista,
                        "El ID debe ser un numero entero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (!txtNombre.isEmpty() &&
                !p.getNombre().toLowerCase().contains(txtNombre.toLowerCase())) continue;
            if (!"Todos".equals(tipo) && !p.getCategoria().equalsIgnoreCase(tipo)) continue;
            boolean disponible = p.getStock() > 0 && !p.getEstado().equalsIgnoreCase("Agotado");
            if ("Activo".equals(estadoFiltro) && !disponible) continue;
            if ("Agotado".equals(estadoFiltro) && disponible) continue;

            resultado.add(p);
        }
        cargarTabla(resultado);
    }

    private void modificar() {
        int fila = vista.tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista,
                "Seleccione un registro en la tabla para modificarlo.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) vista.modeloTabla.getValueAt(fila, 0);
        Producto p = gestor.buscarPorId(id);
        if (p == null) return;

        String nuevoStock = JOptionPane.showInputDialog(vista,
            "Producto: " + p.getNombre() + "\nStock actual: " + p.getStock() + "\nNuevo stock:",
            "Modificar Stock", JOptionPane.QUESTION_MESSAGE);
        if (nuevoStock == null || nuevoStock.isBlank()) return;

        try {
            p.setStock(Integer.parseInt(nuevoStock.trim()));
            gestor.actualizar(p);
            cargarTabla(gestor.getLista());
            JOptionPane.showMessageDialog(vista, "Stock actualizado correctamente.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                "El stock debe ser un numero entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista,
                "Seleccione un registro para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id     = (int) vista.modeloTabla.getValueAt(fila, 0);
        String nom = (String) vista.modeloTabla.getValueAt(fila, 1);

        int conf = JOptionPane.showConfirmDialog(vista,
            "Esta seguro de eliminar el producto \"" + nom + "\" (ID: " + id + ")?",
            "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (conf == JOptionPane.YES_OPTION) {
            gestor.eliminar(id);
            cargarTabla(gestor.getLista());
            JOptionPane.showMessageDialog(vista, "Producto eliminado correctamente.");
        }
    }

    public void cargarTabla(ArrayList<Producto> lista) {
        vista.modeloTabla.setRowCount(0);
        for (Producto p : lista) {
            String estadoTabla = p.getStock() > 0 && !p.getEstado().equalsIgnoreCase("Agotado")
                ? "Disponible"
                : "Agotado";
            vista.modeloTabla.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getCategoria(),
                p.getStock(), String.format("$%.2f", p.getPrecioVenta()), estadoTabla
            });
        }
    }
}
