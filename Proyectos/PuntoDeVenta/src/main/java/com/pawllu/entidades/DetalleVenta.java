/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pawllu.entidades;

/**
 *
 * @author TDAVI
 */
public class DetalleVenta {

    private Long id;
    private double precio;
    private int cantidad;
    private Long idVenta;
    private Producto productoId;

    public DetalleVenta() {
    }

    public DetalleVenta(Long id, double precio, int cantidad, Long idVenta, Producto productoId) {
        this.id = id;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idVenta = idVenta;
        this.productoId = productoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public Producto getProductoId() {
        return productoId;
    }

    public void setProductoId(Producto productoId) {
        this.productoId = productoId;
    }

}
