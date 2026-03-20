package sistemapos.modelo;

/**
 * Producto vendido por kilogramo (peso variable).
 *
 * Categorias tipicas: Frutas y Verduras, Carnes y Pescados,
 * Salchichoneria, Mascotas (alimento a granel).
 * La cantidad puede ser decimal (ej: 0.75 kg).
 */
public class ProductoPorPeso extends Producto {

    public ProductoPorPeso(int id, String codigo, String nombre, String descripcion,
                           String categoria, double precioCompra, double precioVenta,
                           int stock, int stockMinimo, String estado) {
        super(id, codigo, nombre, descripcion, categoria, precioCompra, precioVenta, stock, stockMinimo, estado);
    }

    /** Subtotal = precio por kg × cantidad en kg. */
    @Override
    public double calcularSubtotal(double cantidadKg) {
        return getPrecioVenta() * cantidadKg;
    }

    @Override
    public String getUnidadVenta() {
        return "kg";
    }

    /** Cantidad valida: cualquier valor positivo (incluye decimales). */
    @Override
    public boolean cantidadValida(double cantidad) {
        return cantidad > 0;
    }
}
