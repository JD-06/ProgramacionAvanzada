package sistemapos.modelo;

/**
 * Clase abstracta base para todos los tipos de producto del sistema.
 *
 * Jerarquia:
 *   Producto (abstract)
 *     ├─ ProductoUnitario   → ventas por piezas  (Abarrotes, Limpieza, Snacks…)
 *     ├─ ProductoPorPeso    → ventas por kg       (Frutas, Carnes, Salchichoneria…)
 *     └─ ProductoPorVolumen → ventas por litro    (Bebidas a granel)
 *
 * Metodo fabrica: Producto.crear(String tipo) para instanciar correctamente.
 */
public abstract class Producto {

    private int    id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private double precioCompra;
    private double precioVenta;
    private int    stock;
    private int    stockMinimo;
    private String estado;
    private String imagenRuta;   // ruta al archivo de imagen del producto

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public Producto(int id, String codigo, String nombre, String descripcion,
                    String categoria, double precioCompra, double precioVenta,
                    int stock, int stockMinimo, String estado) {
        this.id          = id;
        this.codigo      = codigo;
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.categoria   = categoria;
        this.precioCompra= precioCompra;
        this.precioVenta = precioVenta;
        this.stock       = stock;
        this.stockMinimo = stockMinimo;
        this.estado      = estado;
        this.imagenRuta  = "";
    }

    // =========================================================
    // METODOS ABSTRACTOS
    // =========================================================

    /** Calcula el subtotal segun la cantidad vendida. */
    public abstract double calcularSubtotal(double cantidad);

    /** Devuelve la unidad de venta: "unidad", "kg", "litro". */
    public abstract String getUnidadVenta();

    /** Valida si la cantidad ingresada es correcta para este tipo. */
    public abstract boolean cantidadValida(double cantidad);

    // =========================================================
    // METODO FABRICA
    // =========================================================

    /**
     * Crea la subclase correcta segun el tipo indicado.
     * Usar este metodo en lugar de new Producto(...).
     */
    public static Producto crear(String tipo, int id, String codigo, String nombre,
                                 String descripcion, String categoria,
                                 double precioCompra, double precioVenta,
                                 int stock, int stockMinimo, String estado) {
        Producto p;
        if (tipo == null) tipo = "Unitario";
        switch (tipo.trim()) {
            case "Por Peso (kg)":    p = new ProductoPorPeso(id, codigo, nombre, descripcion, categoria, precioCompra, precioVenta, stock, stockMinimo, estado); break;
            case "Por Volumen (lt)": p = new ProductoPorVolumen(id, codigo, nombre, descripcion, categoria, precioCompra, precioVenta, stock, stockMinimo, estado); break;
            default:                 p = new ProductoUnitario(id, codigo, nombre, descripcion, categoria, precioCompra, precioVenta, stock, stockMinimo, estado); break;
        }
        return p;
    }

    /** Devuelve el nombre del tipo para guardar en CSV. */
    public String getTipoProducto() {
        if (this instanceof ProductoPorPeso)    return "Por Peso (kg)";
        if (this instanceof ProductoPorVolumen) return "Por Volumen (lt)";
        return "Unitario";
    }

    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public int    getId()            { return id; }
    public String getCodigo()        { return codigo; }
    public String getNombre()        { return nombre; }
    public String getDescripcion()   { return descripcion; }
    public String getCategoria()     { return categoria; }
    public double getPrecioCompra()  { return precioCompra; }
    public double getPrecioVenta()   { return precioVenta; }
    public int    getStock()         { return stock; }
    public int    getStockMinimo()   { return stockMinimo; }
    public String getEstado()        { return estado; }
    public String getImagenRuta()    { return imagenRuta; }

    public void setId(int id)                    { this.id = id; }
    public void setCodigo(String codigo)          { this.codigo = codigo; }
    public void setNombre(String nombre)          { this.nombre = nombre; }
    public void setDescripcion(String d)          { this.descripcion = d; }
    public void setCategoria(String categoria)    { this.categoria = categoria; }
    public void setPrecioCompra(double pc)        { this.precioCompra = pc; }
    public void setPrecioVenta(double pv)         { this.precioVenta = pv; }
    public void setStock(int stock)               { this.stock = stock; }
    public void setStockMinimo(int sm)            { this.stockMinimo = sm; }
    public void setEstado(String estado)          { this.estado = estado; }
    public void setImagenRuta(String imagenRuta)  { this.imagenRuta = imagenRuta != null ? imagenRuta : ""; }

    /**
     * Serializa el producto al formato CSV extendido (12 campos).
     * id,codigo,nombre,descripcion,categoria,precioCompra,precioVenta,
     * stock,stockMinimo,estado,tipo,imagenRuta
     */
    @Override
    public String toString() {
        return id + "," + codigo + "," + nombre + "," +
               descripcion.replace(",", ";") + "," + categoria + "," +
               precioCompra + "," + precioVenta + "," +
               stock + "," + stockMinimo + "," + estado + "," +
               getTipoProducto() + "," + imagenRuta.replace(",", ";");
    }
}
