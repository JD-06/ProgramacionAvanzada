package sistemapos.modelo;

/**
 * Producto vendido por litro (volumen variable).
 *
 * Categorias tipicas: Bebidas a granel, jugos frescos, aceite a granel.
 * La cantidad puede ser decimal (ej: 1.5 lt).
 */
public class ProductoPorVolumen extends Producto {

    public ProductoPorVolumen(int id, String codigo, String nombre, String descripcion,
                              String categoria, double precioCompra, double precioVenta,
                              int stock, int stockMinimo, String estado) {
        super(id, codigo, nombre, descripcion, categoria, precioCompra, precioVenta, stock, stockMinimo, estado);
    }

    /** Subtotal = precio por litro × cantidad en litros. */
    @Override
    public double calcularSubtotal(double cantidadLitros) {
        return getPrecioVenta() * cantidadLitros;
    }

    @Override
    public String getUnidadVenta() {
        return "litro";
    }

    /** Cantidad valida: cualquier valor positivo (incluye decimales). */
    @Override
    public boolean cantidadValida(double cantidad) {
        return cantidad > 0;
    }
}
