package sistemapos.modelo;

/**
 * Producto vendido por piezas/unidades enteras.
 *
 * Categorias tipicas: Abarrotes (Despensa), Panaderia y Tortilleria,
 * Limpieza del Hogar, Cuidado Personal, Snacks y Dulceria,
 * Lacteos y Huevo, Mascotas (accesorios), Bebidas envasadas.
 */
public class ProductoUnitario extends Producto {

    public ProductoUnitario(int id, String codigo, String nombre, String descripcion,
                            String categoria, double precioCompra, double precioVenta,
                            int stock, int stockMinimo, String estado) {
        super(id, codigo, nombre, descripcion, categoria, precioCompra, precioVenta, stock, stockMinimo, estado);
    }

    /** Subtotal = precio unitario × cantidad entera. */
    @Override
    public double calcularSubtotal(double cantidad) {
        return getPrecioVenta() * (int) cantidad;
    }

    @Override
    public String getUnidadVenta() {
        return "unidad";
    }

    /** Cantidad valida: entero >= 1. */
    @Override
    public boolean cantidadValida(double cantidad) {
        return cantidad >= 1 && cantidad == Math.floor(cantidad);
    }
}
