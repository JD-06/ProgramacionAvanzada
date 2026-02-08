package ejemplos.layouts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorLayout {
    private LayoutModelo modelo;
    private LayoutVista vista;

    public ControladorLayout(LayoutModelo modelo, LayoutVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        inicializar();
        inicializarEventos();
    }

    private void inicializar() {
        vista.cargarLayouts(modelo.getLayoutsDisponibles());
        vista.setDescripcion(modelo.getDescripcion());
        vista.aplicarFlowLayout();
    }

    private void inicializarEventos() {
        vista.getBtnCambiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String layoutSeleccionado = vista.getLayoutSeleccionado();
                modelo.setLayoutActual(layoutSeleccionado);
                vista.setDescripcion(modelo.getDescripcion());
                aplicarLayout(layoutSeleccionado);
            }
        });
    }

    private void aplicarLayout(String layout) {
        switch (layout) {
            case "FlowLayout":
                vista.aplicarFlowLayout();
                break;
            case "BorderLayout":
                vista.aplicarBorderLayout();
                break;
            case "GridLayout":
                vista.aplicarGridLayout();
                break;
            case "BoxLayout":
                vista.aplicarBoxLayout();
                break;
            case "CardLayout":
                vista.aplicarCardLayout();
                break;
            case "GridBagLayout":
                vista.aplicarGridBagLayout();
                break;
        }
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}
