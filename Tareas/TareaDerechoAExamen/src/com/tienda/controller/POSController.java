package com.tienda.controller;

import com.tienda.model.ItemVenta;
import com.tienda.model.Producto;
import com.tienda.model.Venta;
import com.tienda.util.DataStore;
import com.tienda.util.TicketService;
import com.tienda.view.POSPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Controlador del módulo Punto de Venta (POS).
 *
 * Responsabilidades:
 *  - Gestión del carrito (agregar, quitar, limpiar)
 *  - Validación del pago
 *  - Descuento de stock al finalizar
 *  - Generación del ticket (TXT + JSON) mediante TicketService
 *  - Notificación al MainController para que refresque otros módulos
 */
public class POSController {

    private final Frame    owner;
    private final POSPanel panel;
    private final DataStore store;
    private final Runnable  onVentaFinalizada; // notifica al MainController

    /** Venta que está siendo armada actualmente. */
    private Venta ventaActual = new Venta();

    public POSController(Frame owner, POSPanel panel,
                         DataStore store, Runnable onVentaFinalizada) {
        this.owner              = owner;
        this.panel              = panel;
        this.store              = store;
        this.onVentaFinalizada  = onVentaFinalizada;
        init();
    }

    /** Recarga la lista de productos disponibles en el panel. */
    public void refresh() {
        panel.setProductos(store.getProductos());
    }

    // ── Inicialización de eventos ────────────────────

    private void init() {
        // Filtro de búsqueda en tiempo real
        panel.txtBuscar.getDocument().addDocumentListener(
            ProductosController.onChange(() ->
                panel.actualizarListaProductos(panel.txtBuscar.getText())));

        // Doble-click agrega al carrito
        panel.listProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) agregarAlCarrito();
            }
        });

        panel.btnAgregar.addActionListener(e  -> agregarAlCarrito());
        panel.btnQuitar.addActionListener(e   -> quitarDelCarrito());
        panel.btnLimpiar.addActionListener(e  -> limpiarCarrito());
        panel.btnFinalizar.addActionListener(e -> finalizarVenta());

        // Calcular cambio en tiempo real al escribir el monto de pago
        panel.txtPago.getDocument().addDocumentListener(
            ProductosController.onChange(() ->
                panel.calcularCambio(ventaActual.getTotal())));
    }

    // ── Operaciones del carrito ──────────────────────

    private void agregarAlCarrito() {
        String sku = panel.getProductoSeleccionadoSku();
        if (sku == null) {
            info("Selecciona un producto de la lista o búscalo por nombre / SKU.");
            return;
        }

        Producto prod = store.getProductos().stream()
            .filter(p -> p.getSku().equals(sku)).findFirst().orElse(null);
        if (prod == null) return;

        int cantidad = (int) panel.spinCantidad.getValue();

        // Verificar que no se pida más de lo disponible (considerando lo ya en carrito)
        int enCarrito = ventaActual.getItems().stream()
            .filter(i -> i.getProducto().getSku().equals(sku))
            .mapToInt(ItemVenta::getCantidad).sum();

        if (prod.getStock() < enCarrito + cantidad) {
            warn("Stock insuficiente.\nDisponible: " + prod.getStock() +
                 "  |  Ya en carrito: " + enCarrito);
            return;
        }

        ventaActual.agregarItem(prod, cantidad);
        refrescarTablaCarrito();
    }

    private void quitarDelCarrito() {
        int row = panel.tablaCarrito.getSelectedRow();
        if (row < 0) {
            info("Selecciona un artículo del carrito para quitarlo.");
            return;
        }
        ventaActual.eliminarItem(row);
        refrescarTablaCarrito();
    }

    private void limpiarCarrito() {
        if (ventaActual.getItems().isEmpty()) return;
        int ok = JOptionPane.showConfirmDialog(owner,
            "¿Limpiar el carrito?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            ventaActual = new Venta();
            panel.limpiarCarrito();
        }
    }

    // ── Finalización de venta ────────────────────────

    private void finalizarVenta() {
        if (ventaActual.getItems().isEmpty()) {
            info("El carrito está vacío.");
            return;
        }

        String metodo      = (String) panel.cbPago.getSelectedItem();
        double pagoRecibido = 0;

        if ("EFECTIVO".equalsIgnoreCase(metodo)) {
            try {
                pagoRecibido = Double.parseDouble(panel.txtPago.getText().trim());
            } catch (NumberFormatException e) {
                warn("Ingresa el monto de pago antes de finalizar.");
                return;
            }
            if (pagoRecibido < ventaActual.getTotal()) {
                warn(String.format("Pago insuficiente.%nTotal: $%.2f%nPago:  $%.2f",
                    ventaActual.getTotal(), pagoRecibido));
                return;
            }
        }

        ventaActual.setMetodoPago(metodo);

        // ── Descontar stock ──────────────────────────
        for (ItemVenta item : ventaActual.getItems()) {
            String sku = item.getProducto().getSku();
            store.getProductos().stream()
                .filter(p -> p.getSku().equals(sku)).findFirst()
                .ifPresent(p -> p.setStock(p.getStock() - item.getCantidad()));
        }

        // ── Persistir ────────────────────────────────
        store.getVentas().add(ventaActual);
        store.guardarProductos();
        store.guardarVentas();

        // ── Guardar ticket (TXT + JSON) ───────────────
        String base = TicketService.save(ventaActual, pagoRecibido);

        // ── Ticket en pantalla ────────────────────────
        double cambio = Math.max(0, pagoRecibido - ventaActual.getTotal());
        String pantalla =
            String.format("═══════════════════════════════%n") +
            String.format("  VENTA REGISTRADA  #%04d%n", ventaActual.getFolio()) +
            String.format("════════════════════════════════%n") +
            String.format("  Total     : $%.2f%n", ventaActual.getTotal()) +
            ("EFECTIVO".equalsIgnoreCase(metodo) ?
                String.format("  Pago      : $%.2f%n", pagoRecibido) +
                String.format("  Cambio    : $%.2f%n", cambio) : "") +
            String.format("  Método    : %s%n", metodo) +
            String.format("════════════════════════════════%n") +
            "  Ticket guardado en:\n" +
            "  " + base + ".txt\n" +
            "  " + base + ".json";

        JOptionPane.showMessageDialog(owner, pantalla,
            "Venta Finalizada", JOptionPane.INFORMATION_MESSAGE);

        // ── Resetear ─────────────────────────────────
        ventaActual = new Venta();
        panel.limpiarCarrito();
        onVentaFinalizada.run();
    }

    // ── Actualizar tabla carrito ─────────────────────

    private void refrescarTablaCarrito() {
        panel.modelCarrito.setRowCount(0);
        for (ItemVenta item : ventaActual.getItems()) {
            panel.modelCarrito.addRow(new Object[]{
                item.getProducto().getSku(),
                item.getProducto().getNombre(),
                item.getCantidad(),
                item.getPrecioUnitario(),
                item.getSubtotal()
            });
        }
        panel.actualizarTotal(ventaActual.getTotal());
    }

    // ── Utilería ─────────────────────────────────────

    private void info(String msg) {
        JOptionPane.showMessageDialog(owner, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void warn(String msg) {
        JOptionPane.showMessageDialog(owner, msg, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
