package com.pawllu.entidades;

import java.util.List;

public class Venta {

    private Long id;
    private String fecha;
    private double descuento;
    private Cliente clienteId;
    private double total;
    private boolean estado;
    private List<DetalleVenta> detalles;

    public Venta() {
    }

    public Venta(Long id, String fecha, double descuento, Cliente clienteId, double total, boolean estado) {
        this.id = id;
        this.fecha = fecha;
        this.descuento = descuento;
        this.clienteId = clienteId;
        this.total = total;
        this.estado = estado;
    }

    public Venta(Long id, String fecha, double descuento, Cliente clienteId, double total, boolean estado, List<DetalleVenta> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.descuento = descuento;
        this.clienteId = clienteId;
        this.total = total;
        this.estado = estado;
        this.detalles = detalles;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

}
