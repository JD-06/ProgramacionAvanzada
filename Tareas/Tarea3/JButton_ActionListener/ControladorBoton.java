package ejemplos.jbutton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorBoton {
    private BotonModelo modelo;
    private BotonVista vista;

    public ControladorBoton(BotonModelo modelo, BotonVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        inicializarEventos();
    }

    private void inicializarEventos() {

        vista.getBtnIncrementar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.incrementar();
                actualizarVista();
            }
        });

        vista.getBtnDecrementar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.decrementar();
                actualizarVista();
            }
        });

        vista.getBtnResetear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.resetear();
                actualizarVista();
                vista.limpiarHistorial();
            }
        });

        vista.getBtnMostrarHistorial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.actualizarHistorial(modelo.getHistorial());
            }
        });
    }

    private void actualizarVista() {
        vista.actualizarContador(modelo.getEstadoTexto());
        vista.actualizarMensaje(modelo.getMensajeMotivacional());
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}
