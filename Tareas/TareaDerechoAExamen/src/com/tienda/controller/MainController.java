package com.tienda.controller;

import com.tienda.util.DataStore;
import com.tienda.view.*;

/**
 * Controlador principal — orquesta los módulos del sistema.
 *
 * Responsabilidades:
 *  - Crear y conectar los sub-controladores
 *  - Gestionar la navegación entre paneles
 *  - Propagar cambios de datos entre módulos (callbacks)
 *
 * Cada módulo tiene su propio controlador dedicado:
 *   ProductosController  → CRUD de productos + previsualización imagen
 *   InventarioController → Vista de stock + ajuste manual
 *   ProveedoresController → CRUD de proveedores
 *   POSController         → Carrito, pago, ticket TXT/JSON
 *   ReportesController    → Estadísticas y tablas de resumen
 */
public class MainController {

    // Vista principal
    private final MainView view;

    // Almacén de datos compartido
    private final DataStore store = new DataStore();

    // Paneles
    private final ProductosPanel   pProductos;
    private final InventarioPanel  pInventario;
    private final ProveedoresPanel pProveedores;
    private final POSPanel         pPOS;
    private final ReportesPanel    pReportes;

    // Sub-controladores
    private final ProductosController   ctrlProductos;
    private final InventarioController  ctrlInventario;
    private final ProveedoresController ctrlProveedores;
    private final POSController         ctrlPOS;
    private final ReportesController    ctrlReportes;

    public MainController(MainView view) {
        this.view = view;

        // ── Cargar datos ─────────────────────────────
        store.cargar();

        // ── Crear paneles ────────────────────────────
        pProductos   = new ProductosPanel();
        pInventario  = new InventarioPanel();
        pProveedores = new ProveedoresPanel();
        pPOS         = new POSPanel();
        pReportes    = new ReportesPanel();

        view.addPanel(pProductos,   "PRODUCTOS");
        view.addPanel(pInventario,  "INVENTARIO");
        view.addPanel(pProveedores, "PROVEEDORES");
        view.addPanel(pPOS,         "POS");
        view.addPanel(pReportes,    "REPORTES");

        // ── Crear sub-controladores ──────────────────
        //
        // Cada sub-controlador recibe un callback "onChanged"
        // para avisar cuándo sus datos afectan a otros módulos.

        ctrlProductos = new ProductosController(
            view, pProductos, store,
            this::onProductosChanged   // cuando cambia productos → refrescar inventario/POS/reportes
        );

        ctrlInventario = new InventarioController(
            view, pInventario, store,
            this::onProductosChanged   // stock modificado → mismos efectos
        );

        ctrlProveedores = new ProveedoresController(
            view, pProveedores, store
            // proveedores no afectan otros módulos en esta versión
        );

        ctrlPOS = new POSController(
            view, pPOS, store,
            this::onVentaFinalizada    // venta → refrescar inventario, productos, reportes
        );

        ctrlReportes = new ReportesController(pReportes, store);

        // ── Cargar vistas iniciales ──────────────────
        ctrlProductos.refresh();
        ctrlInventario.refresh();
        ctrlProveedores.refresh();
        ctrlPOS.refresh();
        ctrlReportes.refresh();

        // ── Configurar navegación ────────────────────
        initNavegacion();

        // ── Módulo inicial ───────────────────────────
        view.showPanel("PRODUCTOS");
        view.setActiveBtn(view.btnProductos);
    }

    // ── Navegación ───────────────────────────────────

    private void initNavegacion() {
        view.btnProductos.addActionListener(e -> {
            view.showPanel("PRODUCTOS");
            view.setActiveBtn(view.btnProductos);
            ctrlProductos.refresh();
        });
        view.btnInventario.addActionListener(e -> {
            view.showPanel("INVENTARIO");
            view.setActiveBtn(view.btnInventario);
            ctrlInventario.refresh();
        });
        view.btnProveedores.addActionListener(e -> {
            view.showPanel("PROVEEDORES");
            view.setActiveBtn(view.btnProveedores);
            ctrlProveedores.refresh();
        });
        view.btnPOS.addActionListener(e -> {
            view.showPanel("POS");
            view.setActiveBtn(view.btnPOS);
            ctrlPOS.refresh();
        });
        view.btnReportes.addActionListener(e -> {
            view.showPanel("REPORTES");
            view.setActiveBtn(view.btnReportes);
            ctrlReportes.refresh();
        });
    }

    // ── Callbacks de propagación de cambios ──────────

    /**
     * Llamado cuando productos o stock cambian (CRUD o ajuste).
     * Refresca los módulos que dependen del catálogo de productos.
     */
    private void onProductosChanged() {
        ctrlInventario.refresh();
        ctrlPOS.refresh();
        ctrlReportes.refresh();
    }

    /**
     * Llamado cuando se finaliza una venta.
     * Refresca todos los módulos afectados por el stock descontado.
     */
    private void onVentaFinalizada() {
        ctrlProductos.refresh();
        ctrlInventario.refresh();
        ctrlReportes.refresh();
    }
}
