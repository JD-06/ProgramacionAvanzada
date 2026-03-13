package sistemapos.modelo;

public class Producto {
    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private double precioCompra;
    private double precioVenta;
    private int stock;
    private int stockMinimo;
    private String estado; 

    public Producto(int id, String codigo, String nombre, String descripcion,
                    String categoria, double precioCompra, double precioVenta,
                    int stock, int stockMinimo, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.estado = estado;
    }

    
    public int getId()            { return id; }
    public String getCodigo()     { return codigo; }
    public String getNombre()     { return nombre; }
    public String getDescripcion(){ return descripcion; }
    public String getCategoria()  { return categoria; }
    public double getPrecioCompra(){ return precioCompra; }
    public double getPrecioVenta(){ return precioVenta; }
    public int getStock()         { return stock; }
    public int getStockMinimo()   { return stockMinimo; }
    public String getEstado()     { return estado; }

    
    public void setId(int id)                   { this.id = id; }
    public void setCodigo(String codigo)         { this.codigo = codigo; }
    public void setNombre(String nombre)         { this.nombre = nombre; }
    public void setDescripcion(String descripcion){ this.descripcion = descripcion; }
    public void setCategoria(String categoria)   { this.categoria = categoria; }
    public void setPrecioCompra(double pc)       { this.precioCompra = pc; }
    public void setPrecioVenta(double pv)        { this.precioVenta = pv; }
    public void setStock(int stock)              { this.stock = stock; }
    public void setStockMinimo(int sm)           { this.stockMinimo = sm; }
    public void setEstado(String estado)         { this.estado = estado; }

    @Override
    public String toString() {
        return id + "," + codigo + "," + nombre + "," +
               descripcion.replace(",", ";") + "," + categoria + "," +
               precioCompra + "," + precioVenta + "," +
               stock + "," + stockMinimo + "," + estado;
    }
}
