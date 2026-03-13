package sistemapos.modelo;

public class ItemCarrito {
    private Producto producto;
    private int cantidad;
    private double total;

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = producto.getPrecioVenta() * cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad()      { return cantidad; }
    public double getTotal()      { return total; }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.total = producto.getPrecioVenta() * cantidad;
    }
}
