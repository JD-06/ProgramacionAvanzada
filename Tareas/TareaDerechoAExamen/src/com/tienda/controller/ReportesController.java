package com.tienda.controller;

import com.tienda.util.DataStore;
import com.tienda.view.ReportesPanel;

/**
 * Controlador del módulo Reportes.
 * Solo lectura: alimenta las estadísticas y tablas con los datos actuales.
 */
public class ReportesController {

    private final ReportesPanel panel;
    private final DataStore     store;

    public ReportesController(ReportesPanel panel, DataStore store) {
        this.panel = panel;
        this.store = store;
        init();
    }

    public void refresh() {
        panel.setData(store.getProductos(), store.getVentas());
    }

    // ── Inicialización de eventos ────────────────────

    private void init() {
        panel.btnRefresh.addActionListener(e -> refresh());
    }
}
