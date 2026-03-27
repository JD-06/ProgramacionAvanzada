package com.tienda.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private static int contadorFolio = 1;

    private int folio;
    private String fecha;
    private List<ItemVenta> items = new ArrayList<>();
    private double total;
    private String metodoPago; // EFECTIVO, TARJETA

    public Venta() {
        this.folio = contadorFolio++;
        this.fecha = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.metodoPago = "EFECTIVO";
    }

    public Venta(int folio, String fecha, double total, String metodoPago) {
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
        this.metodoPago = metodoPago;
        if (folio >= contadorFolio) contadorFolio = folio + 1;
    }

    public void agregarItem(Producto p, int cantidad) {
        for (ItemVenta item : items) {
            if (item.getProducto().getSku().equals(p.getSku())) {
                item.setCantidad(item.getCantidad() + cantidad);
                recalcularTotal();
                return;
            }
        }
        items.add(new ItemVenta(p, cantidad));
        recalcularTotal();
    }

    public void eliminarItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            recalcularTotal();
        }
    }

    private void recalcularTotal() {
        total = items.stream().mapToDouble(ItemVenta::getSubtotal).sum();
    }

    public void setTotal(double total) { this.total = total; }
    public double getTotal() { return total; }
    public int getFolio() { return folio; }
    public String getFecha() { return fecha; }
    public List<ItemVenta> getItems() { return items; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public static void setContadorFolio(int val) { contadorFolio = val; }
}
