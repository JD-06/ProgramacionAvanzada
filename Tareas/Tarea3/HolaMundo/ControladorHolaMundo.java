package ejemplos.holamundo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorHolaMundo {
    private HolaMundoModelo modelo;
    private HolaMundoVista vista;

    public ControladorHolaMundo(HolaMundoModelo modelo, HolaMundoVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        inicializarEventos();
    }

    private void inicializarEventos() {

        vista.getBtnSaludar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = vista.getNombre();
                String saludo = modelo.generarSaludo(nombre);
                vista.setMensaje(saludo);
            }
        });

        vista.getBtnReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.limpiarNombre();
                vista.setMensaje(modelo.getMensaje());
            }
        });
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}
