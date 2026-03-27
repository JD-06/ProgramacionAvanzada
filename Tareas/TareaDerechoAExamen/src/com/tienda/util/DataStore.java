package com.tienda.util;

import com.tienda.model.Producto;
import com.tienda.model.Proveedor;
import com.tienda.model.Venta;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor compartido de datos en memoria.
 * Los controladores lo usan para leer/escribir el estado de la aplicación
 * sin acoplarse directamente entre sí.
 */
public class DataStore {

    private List<Producto>  productos   = new ArrayList<>();
    private List<Proveedor> proveedores = new ArrayList<>();
    private List<Venta>     ventas      = new ArrayList<>();

    // ── Carga desde disco ────────────────────────────

    public void cargar() {
        productos   = DataManager.loadProductos();
        proveedores = DataManager.loadProveedores();
        ventas      = DataManager.loadVentas();
        // Ajustar el folio para que el siguiente sea correcto
        ventas.stream()
            .mapToInt(Venta::getFolio)
            .max()
            .ifPresent(max -> Venta.setContadorFolio(max + 1));
    }

    // ── Persistencia ─────────────────────────────────

    public void guardarProductos()   { DataManager.saveProductos(productos); }
    public void guardarProveedores() { DataManager.saveProveedores(proveedores); }
    public void guardarVentas()      { DataManager.saveVentas(ventas); }

    // ── Acceso ───────────────────────────────────────

    public List<Producto>  getProductos()   { return productos; }
    public List<Proveedor> getProveedores() { return proveedores; }
    public List<Venta>     getVentas()      { return ventas; }
}
