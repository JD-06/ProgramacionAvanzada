package sistemapos.controlador;

import sistemapos.modelo.Cajero;
import sistemapos.modelo.Cliente;
import sistemapos.modelo.GestorCajeros;
import sistemapos.modelo.GestorClientes;
import sistemapos.modelo.GestorCompras;
import sistemapos.modelo.GestorProductos;
import sistemapos.modelo.ItemCarrito;
import sistemapos.modelo.Producto;
import sistemapos.modelo.RegistroCompra;
import sistemapos.vista.PuntoDeVentaFrame;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class VentaController {

    private static final double IVA = 0.16;

    private final PuntoDeVentaFrame vista;
    private final GestorProductos gestorProductos;
    private final GestorClientes gestorClientes;
    private final GestorCajeros gestorCajeros;
    private final GestorCompras gestorCompras;
    private final ArrayList<ItemCarrito> carrito = new ArrayList<>();

    public VentaController(
        PuntoDeVentaFrame vista,
        GestorProductos gestorProductos,
        GestorClientes gestorClientes,
        GestorCajeros gestorCajeros,
        GestorCompras gestorCompras
    ) {
        this.vista = vista;
        this.gestorProductos = gestorProductos;
        this.gestorClientes = gestorClientes;
        this.gestorCajeros = gestorCajeros;
        this.gestorCompras = gestorCompras;
        registrarEventos();
        recargarDatos();
    }

    public void recargarDatos() {
        carrito.clear();
        cargarProductosCombo();
        cargarClientesCombo();
        cargarCajerosCombo();
        actualizarTablaCarrito();
        cargarHistoriales();
        vista.txtCantidad.setText("1");
    }

    private void registrarEventos() {
        vista.btnAnadir.addActionListener(e -> anadirAlCarrito());
        vista.btnModificarItem.addActionListener(e -> modificarCantidadItem());
        vista.btnEliminarItem.addActionListener(e -> eliminarItem());
        vista.btnLimpiarCarrito.addActionListener(e -> limpiarCarrito());
        vista.btnProcesarPago.addActionListener(e -> procesarPago());
        vista.tablaSocios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirTicketDesdeTabla(vista.tablaSocios);
            }
        });
        vista.tablaNoClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirTicketDesdeTabla(vista.tablaNoClientes);
            }
        });
    }

    private void cargarProductosCombo() {
        vista.cmbProducto.removeAllItems();
        for (Producto p : gestorProductos.getActivos()) {
            vista.cmbProducto.addItem("[" + p.getCodigo() + "] " + p.getNombre()
                + "  $" + String.format("%.2f", p.getPrecioVenta()));
        }
    }

    private void cargarClientesCombo() {
        vista.cmbCliente.removeAllItems();
        vista.cmbCliente.addItem("Sin cliente");
        for (Cliente cliente : gestorClientes.getLista()) {
            String etiqueta = "[" + cliente.getId() + "] " + cliente.getNombre();
            if (cliente.isSocio()) {
                etiqueta += " (Socio)";
            }
            vista.cmbCliente.addItem(etiqueta);
        }
        vista.cmbCliente.setSelectedIndex(0);
    }

    private void cargarCajerosCombo() {
        vista.cmbCajero.removeAllItems();
        for (Cajero cajero : gestorCajeros.getLista()) {
            vista.cmbCajero.addItem("[" + cajero.getId() + "] " + cajero.getNombre());
        }
        if (vista.cmbCajero.getItemCount() == 0) {
            vista.cmbCajero.addItem("Sin cajero");
        }
        vista.cmbCajero.setSelectedIndex(0);
    }

    private void cargarHistoriales() {
        cargarTablaCompras(vista.modeloSocios, gestorCompras.getComprasSocios());
        cargarTablaCompras(vista.modeloNoClientes, gestorCompras.getComprasNoClientes());
    }

    private void cargarTablaCompras(DefaultTableModel modelo, ArrayList<RegistroCompra> lista) {
        modelo.setRowCount(0);
        for (RegistroCompra registro : lista) {
            modelo.addRow(new Object[]{
                registro.getFechaHora(),
                registro.getTicket(),
                registro.getCliente(),
                registro.getCajero(),
                String.format("$%.2f", registro.getTotal())
            });
        }
    }

    private void anadirAlCarrito() {
        int idxCombo = vista.cmbProducto.getSelectedIndex();
        if (idxCombo < 0) {
            avisar("Seleccione un producto.");
            return;
        }

        ArrayList<Producto> activos = gestorProductos.getActivos();
        if (idxCombo >= activos.size()) {
            return;
        }
        Producto producto = activos.get(idxCombo);

        int cantidad;
        try {
            cantidad = Integer.parseInt(vista.txtCantidad.getText().trim());
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            avisar("La cantidad debe ser un numero entero positivo.");
            return;
        }

        if (producto.getStock() < cantidad) {
            avisar("Stock insuficiente. Disponible: " + producto.getStock());
            return;
        }

        for (ItemCarrito item : carrito) {
            if (item.getProducto().getId() == producto.getId()) {
                int nuevaCantidad = item.getCantidad() + cantidad;
                if (!gestorProductos.hayStockSuficiente(producto.getId(), nuevaCantidad)) {
                    avisar("Stock insuficiente. Disponible: " + producto.getStock());
                    return;
                }
                item.setCantidad(nuevaCantidad);
                actualizarTablaCarrito();
                return;
            }
        }
        carrito.add(new ItemCarrito(producto, cantidad));
        actualizarTablaCarrito();
    }

    private void modificarCantidadItem() {
        int fila = vista.tablaCarrito.getSelectedRow();
        if (fila < 0) {
            avisar("Seleccione un item para modificar.");
            return;
        }
        String nuevaCant = JOptionPane.showInputDialog(vista, "Nueva cantidad:", vista.modeloCarrito.getValueAt(fila, 2));
        if (nuevaCant == null) {
            return;
        }
        try {
            int cantidad = Integer.parseInt(nuevaCant.trim());
            if (cantidad <= 0) {
                avisar("La cantidad debe ser mayor a cero.");
                return;
            }
            ItemCarrito item = carrito.get(fila);
            if (!gestorProductos.hayStockSuficiente(item.getProducto().getId(), cantidad)) {
                Producto actual = gestorProductos.buscarPorId(item.getProducto().getId());
                int disponible = actual == null ? 0 : actual.getStock();
                avisar("Stock insuficiente. Disponible: " + disponible);
                return;
            }
            item.setCantidad(cantidad);
            actualizarTablaCarrito();
        } catch (NumberFormatException ex) {
            avisar("Ingrese un numero valido.");
        }
    }

    private void eliminarItem() {
        int fila = vista.tablaCarrito.getSelectedRow();
        if (fila < 0) {
            avisar("Seleccione un item para eliminar.");
            return;
        }
        carrito.remove(fila);
        actualizarTablaCarrito();
    }

    private void limpiarCarrito() {
        carrito.clear();
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
        double iva = subtotal * IVA;
        double total = subtotal + iva;
        vista.txtSubtotal.setText(String.format("$%.2f", subtotal));
        vista.txtIva.setText(String.format("$%.2f", iva));
        vista.txtTotal.setText(String.format("$%.2f", total));
    }

    private void procesarPago() {
        if (carrito.isEmpty()) {
            avisar("El carrito esta vacio.");
            return;
        }
        if (gestorCajeros.getLista().isEmpty()) {
            avisar("No hay cajeros registrados. Registre al menos uno.");
            return;
        }

        Cajero cajero = obtenerCajeroSeleccionado();
        if (cajero == null) {
            avisar("Seleccione un cajero.");
            return;
        }
        Cliente cliente = obtenerClienteSeleccionado();

        int confirm = JOptionPane.showConfirmDialog(
            vista,
            "Total a cobrar: " + vista.txtTotal.getText() + "\nConfirmar pago?",
            "Procesar Pago",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        for (ItemCarrito item : carrito) {
            if (!gestorProductos.hayStockSuficiente(item.getProducto().getId(), item.getCantidad())) {
                Producto actual = gestorProductos.buscarPorId(item.getProducto().getId());
                int disponible = actual == null ? 0 : actual.getStock();
                avisar("No hay stock suficiente para " + item.getProducto().getNombre() + ". Disponible: " + disponible);
                cargarProductosCombo();
                return;
            }
        }

        for (ItemCarrito item : carrito) {
            if (!gestorProductos.reducirStock(item.getProducto().getId(), item.getCantidad())) {
                avisar("No se pudo completar la compra por stock insuficiente.");
                cargarProductosCombo();
                return;
            }
        }

        String nombreCliente = cliente == null ? "Sin cliente" : cliente.getNombre();
        String nombreCajero = cajero.getNombre();
        try {
            String archivo = guardarTicketTxt(nombreCliente, nombreCajero);
            double total = calcularTotalCarritoConIva();
            RegistroCompra registro = new RegistroCompra(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                archivo,
                nombreCliente,
                nombreCajero,
                total
            );
            if (cliente != null && cliente.isSocio()) {
                gestorCompras.registrarCompraSocio(registro);
            } else {
                gestorCompras.registrarCompraNoCliente(registro);
            }
            cargarHistoriales();
            JOptionPane.showMessageDialog(
                vista,
                "Pago procesado!\nTotal: " + String.format("$%.2f", total) + "\nTicket: " + archivo,
                "Exito",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                vista,
                "Pago procesado, pero no se pudo generar ticket: " + ex.getMessage(),
                "Exito parcial",
                JOptionPane.WARNING_MESSAGE
            );
        }

        carrito.clear();
        actualizarTablaCarrito();
        cargarProductosCombo();
    }

    private Cajero obtenerCajeroSeleccionado() {
        int idx = vista.cmbCajero.getSelectedIndex();
        if (idx < 0) {
            return null;
        }
        ArrayList<Cajero> lista = gestorCajeros.getLista();
        if (idx >= lista.size()) {
            return null;
        }
        return lista.get(idx);
    }

    private Cliente obtenerClienteSeleccionado() {
        int idx = vista.cmbCliente.getSelectedIndex();
        if (idx <= 0) {
            return null;
        }
        ArrayList<Cliente> lista = gestorClientes.getLista();
        int real = idx - 1;
        if (real < 0 || real >= lista.size()) {
            return null;
        }
        return lista.get(real);
    }

    private String guardarTicketTxt(String nombreCliente, String nombreCajero) throws IOException {
        LocalDateTime ahora = LocalDateTime.now();
        String timestamp = ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String archivo = "ticket_" + timestamp + ".txt";
        try (FileWriter fw = new FileWriter(archivo)) {
            fw.write("====================================\n");
            fw.write("          TICKET DE VENTA           \n");
            fw.write("====================================\n");
            fw.write("Fecha: " + ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
            fw.write("Cliente: " + nombreCliente + "\n");
            fw.write("Cajero:  " + nombreCajero + "\n");
            fw.write("------------------------------------\n");
            fw.write(String.format("%-15s %4s %8s %10s\n", "Producto", "Cant", "P.Unit", "Total"));
            fw.write("------------------------------------\n");
            for (ItemCarrito item : carrito) {
                fw.write(String.format(
                    "%-15s %4d %8.2f %10.2f\n",
                    item.getProducto().getNombre(),
                    item.getCantidad(),
                    item.getProducto().getPrecioVenta(),
                    item.getTotal()
                ));
            }
            fw.write("------------------------------------\n");
            fw.write(String.format("Subtotal: %s\n", vista.txtSubtotal.getText()));
            fw.write(String.format("IVA:      %s\n", vista.txtIva.getText()));
            fw.write(String.format("TOTAL:    %s\n", vista.txtTotal.getText()));
            fw.write("====================================\n");
        }
        return archivo;
    }

    private void abrirTicketDesdeTabla(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            return;
        }
        Object valor = tabla.getValueAt(fila, 1);
        if (valor == null) {
            avisar("No se encontro el ticket de esta compra.");
            return;
        }
        String ticket = valor.toString().trim();
        if (ticket.isEmpty()) {
            avisar("No se encontro el ticket de esta compra.");
            return;
        }
        Path ruta = Paths.get(ticket);
        if (!ruta.isAbsolute()) {
            ruta = Paths.get(System.getProperty("user.dir")).resolve(ruta).normalize();
        }
        if (!Files.exists(ruta)) {
            avisar("El archivo de ticket no existe: " + ruta.toString());
            return;
        }
        try {
            String contenido = new String(Files.readAllBytes(ruta), StandardCharsets.UTF_8);
            JTextArea area = new JTextArea(contenido);
            area.setEditable(false);
            area.setCaretPosition(0);
            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(640, 420));
            JOptionPane.showMessageDialog(
                vista,
                scroll,
                "Ticket completo - " + ticket,
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                vista,
                "No se pudo leer el ticket: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private double calcularTotalCarritoConIva() {
        double subtotal = 0;
        for (ItemCarrito item : carrito) {
            subtotal += item.getTotal();
        }
        return subtotal + (subtotal * IVA);
    }

    private void avisar(String msg) {
        JOptionPane.showMessageDialog(vista, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}
