package com.saeae;

import com.formdev.flatlaf.FlatLightLaf;
import com.saeae.controller.DatosbaseController;
import com.saeae.controller.EvaluacionController;
import com.saeae.controller.InstrumentoController;
import com.saeae.controller.ReporteController;
import com.saeae.view.MainView;

import javax.swing.*;
import java.awt.*;

/**
 * Punto de entrada de la aplicacion SAE-AE.
 *
 * Crea los 4 controladores especializados y los inyecta en la vista.
 *   EvaluacionController  — CRUD de evaluaciones.json
 *   DatosbaseController   — lectura de Datosbase.xlsx
 *   ReporteController     — generacion de Excel
 *   InstrumentoController — logica de negocio pura (sin I/O)
 */
public class Main {

    public static void main(String[] args) {
        // Look & Feel: FlatLaf moderno con tipografia institucional
        try {
            FlatLightLaf.setup();
            UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));
        } catch (Exception e) {
            System.err.println("FlatLaf no disponible. Usando L&F del sistema.");
        }

        // Lanzar en el Event Dispatch Thread de Swing
        SwingUtilities.invokeLater(() -> {
            // Instanciar los 4 controladores (responsabilidad unica cada uno)
            EvaluacionController  ctrlEval        = new EvaluacionController();
            DatosbaseController   ctrlDatos       = new DatosbaseController();
            ReporteController     ctrlReporte     = new ReporteController();
            InstrumentoController ctrlInstrumento = new InstrumentoController();

            // Inyectar en la vista
            MainView view = new MainView(ctrlEval, ctrlDatos, ctrlReporte, ctrlInstrumento);
            view.setVisible(true);
        });
    }
}
