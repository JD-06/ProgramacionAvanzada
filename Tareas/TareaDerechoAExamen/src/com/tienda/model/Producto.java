package com.tienda.model;

public class Producto {
    private String sku;
    private String nombre;
    private String categoria;
    private double precioCompra;
    private double porcentajeGanancia;
    private double precioVenta;
    private int stock;
    private String unidad;
    private String imagenPath;

    public Producto() {}

    public Producto(String sku, String nombre, String categoria, double precioCompra, 
                    double porcentajeGanancia, double precioVenta, int stock, 
                    String unidad, String imagenPath) {
        this.sku = sku;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.porcentajeGanancia = porcentajeGanancia;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.unidad = unidad;
        this.imagenPath = imagenPath;
    }

    // Getters and Setters
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }
    public double getPorcentajeGanancia() { return porcentajeGanancia; }
    public void setPorcentajeGanancia(double porcentajeGanancia) { this.porcentajeGanancia = porcentajeGanancia; }
    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    public String getImagenPath() { return imagenPath; }
    public void setImagenPath(String imagenPath) { this.imagenPath = imagenPath; }
}