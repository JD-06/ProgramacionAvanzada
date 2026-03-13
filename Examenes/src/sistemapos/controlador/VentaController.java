package sistemapos.controlador;

import sistemapos.modelo.GestorProductos;
import sistemapos.modelo.ItemCarrito;
import sistemapos.modelo.Producto;
import sistemapos.vista.PuntoDeVentaFrame;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class VentaController {

    private static final double IVA = 0.16;

    private final PuntoDeVentaFrame vista;
    private final GestorProductos   gestor;
    private final ArrayList<ItemCarrito> carrito = new ArrayList<>();

    public VentaController(PuntoDeVentaFrame vista, GestorProductos gestor) {
        this.vista  = vista;
        this.gestor = gestor;
        registrarEventos();
        recargarDatos();
    }

    public void recargarDatos() {
        carrito.clear();
        cargarProductosCombo();
        actualizarTablaCarrito();
        vista.txtCantidad.setText("1");
    }

    private void cargarProductosCombo() {
        vista.cmbProducto.removeAllItems();
        for (Producto p : gestor.getActivos()) {
            vista.cmbProducto.addItem("[" + p.getCodigo() + "] " + p.getNombre()
                                     + "  $" + String.format("%.2f", p.getPrecioVenta()));
        }
    }

    private void registrarEventos() {

        vista.btnAnadir.addActionListener(e -> anadirAlCarrito());

        vista.btnModificarItem.addActionListener(e -> {
            int fila = vista.tablaCarrito.getSelectedRow();
            if (fila < 0) { avisar("Seleccione un item para modificar."); return; }
            String nuevaCant = JOptionPane.showInputDialog(vista,
                "Nueva cantidad:", vista.modeloCarrito.getValueAt(fila, 2));
            if (nuevaCant == null) return;
            try {
                int cant = Integer.parseInt(nuevaCant.trim());
                if (cant <= 0) { avisar("La cantidad debe ser mayor a cero."); return; }
                carrito.get(fila).setCantidad(cant);
                actualizarTablaCarrito();
            } catch (NumberFormatException ex) {
                avisar("Ingrese un numero valido.");
            }
        });

        vista.btnEliminarItem.addActionListener(e -> {
            int fila = vista.tablaCarrito.getSelectedRow();
            if (fila < 0) { avisar("Seleccione un item para eliminar."); return; }
            carrito.remove(fila);
            actualizarTablaCarrito();
        });

        vista.btnLimpiarCarrito.addActionListener(e -> {
            carrito.clear();
            actualizarTablaCarrito();
        });

        vista.btnProcesarPago.addActionListener(e -> procesarPago());

        vista.btnExportarTicket.addActionListener(e -> exportarTicket());
    }

    private void anadirAlCarrito() {
        int idxCombo = vista.cmbProducto.getSelectedIndex();
        if (idxCombo < 0) { avisar("Seleccione un producto."); return; }

        ArrayList<Producto> activos = gestor.getActivos();
        if (idxCombo >= activos.size()) return;
        Producto prod = activos.get(idxCombo);

        int cantidad;
        try {
            cantidad = Integer.parseInt(vista.txtCantidad.getText().trim());
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            avisar("La cantidad debe ser un numero entero positivo.");
            return;
        }

        if (prod.getStock() < cantidad) {
            avisar("Stock insuficiente. Disponible: " + prod.getStock());
            return;
        }

        for (ItemCarrito item : carrito) {
            if (item.getProducto().getId() == prod.getId()) {
                item.setCantidad(item.getCantidad() + cantidad);
                actualizarTablaCarrito();
                return;
            }
        }
        carrito.add(new ItemCarrito(prod, cantidad));
        actualizarTablaCarrito();
    }

    private void actualizarTablaCarrito() {
        vista.modeloCarrito.setRowCount(0);
        double subtotal = 0;
        for (ItemCarrito item : carrito) {
            vista.modeloCarrito.addRow(new Object[]{
                item.getProducto().getCodigo(),
                item.getProducto().getNombre(),
                item.getCantidad(),
                String.format("$%.2f", item.getProducto().getPrecioVenta()),
                String.format("$%.2f", item.getTotal())
            });
            subtotal += item.getTotal();
        }
        double iva   = subtotal * IVA;
        double total = subtotal + iva;
        vista.txtSubtotal.setText(String.format("$%.2f", subtotal));
        vista.txtIva.setText(String.format("$%.2f", iva));
        vista.txtTotal.setText(String.format("$%.2f", total));
    }

    private void procesarPago() {
        if (carrito.isEmpty()) { avisar("El carrito esta vacio."); return; }

        int confirm = JOptionPane.showConfirmDialog(vista,
            "Total a cobrar: " + vista.txtTotal.getText() +
            "\nConfirmar pago?",
            "Procesar Pago", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        for (ItemCarrito item : carrito) {
            gestor.reducirStock(item.getProducto().getId(), item.getCantidad());
        }
        try {
            String archivo = guardarTicketTxt();
            JOptionPane.showMessageDialog(vista,
                "Pago procesado!\nTotal: " + vista.txtTotal.getText() + "\nTicket: " + archivo,
                "Exito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(vista,
                "Pago procesado!\nTotal: " + vista.txtTotal.getText() +
                    "\nNo se pudo generar ticket: " + ex.getMessage(),
                "Exito parcial", JOptionPane.WARNING_MESSAGE);
        }

        carrito.clear();
        actualizarTablaCarrito();
        cargarProductosCombo();
        vista.txtIdCliente.setText("");
        vista.txtNombreCliente.setText("");
    }

    private void exportarTicket() {
        if (carrito.isEmpty()) { avisar("El carrito esta vacio."); return; }

        try {
            String archivo = guardarTicketTxt();
            JOptionPane.showMessageDialog(vista,
                "Ticket exportado: " + archivo, "Exportar", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(vista,
                "Error al exportar ticket: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String guardarTicketTxt() throws IOException {
        LocalDateTime ahora = LocalDateTime.now();
        String timestamp = ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String archivo = "ticket_" + timestamp + ".txt";
        try (FileWriter fw = new FileWriter(archivo)) {
            fw.write("====================================\n");
            fw.write("          TICKET DE VENTA           \n");
            fw.write("====================================\n");
            fw.write("Fecha: " + ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
            fw.write("Cliente: " + vista.txtNombreCliente.getText() + "\n");
            fw.write("Cajero:  " + vista.txtCajero.getText() + "\n");
            fw.write("------------------------------------\n");
            fw.write(String.format("%-15s %4s %8s %10s\n",
                "Producto","Cant","P.Unit","Total"));
            fw.write("------------------------------------\n");
            for (ItemCarrito item : carrito) {
                fw.write(String.format("%-15s %4d %8.2f %10.2f\n",
                    item.getProducto().getNombre(),
                    item.getCantidad(),
                    item.getProducto().getPrecioVenta(),
                    item.getTotal()));
            }
            fw.write("------------------------------------\n");
            fw.write(String.format("Subtotal: %s\n", vista.txtSubtotal.getText()));
            fw.write(String.format("IVA:      %s\n", vista.txtIva.getText()));
            fw.write(String.format("TOTAL:    %s\n", vista.txtTotal.getText()));
            fw.write("====================================\n");
        }
        return archivo;
    }

    private void avisar(String msg) {
        JOptionPane.showMessageDialog(vista, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}
