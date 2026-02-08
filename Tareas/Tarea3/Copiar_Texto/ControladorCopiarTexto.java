package ejemplos.copiartexto;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorCopiarTexto {
    private CopiarTextoModelo modelo;
    private CopiarTextoVista vista;

    public ControladorCopiarTexto(CopiarTextoModelo modelo, CopiarTextoVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        inicializarEventos();
    }

    private void inicializarEventos() {

        vista.getBtnCopiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.setTextoOrigen(vista.getTextoOrigen());
                modelo.copiarTexto();
                vista.setTextoDestino(modelo.getTextoDestino());
                actualizarEstadisticasDestino();
            }
        });

        vista.getBtnInvertir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.setTextoOrigen(vista.getTextoOrigen());
                modelo.copiarTextoInvertido();
                vista.setTextoDestino(modelo.getTextoDestino());
                actualizarEstadisticasDestino();
            }
        });

        vista.getBtnMayusculas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.setTextoOrigen(vista.getTextoOrigen());
                modelo.copiarTextoMayusculas();
                vista.setTextoDestino(modelo.getTextoDestino());
                actualizarEstadisticasDestino();
            }
        });

        vista.getBtnMinusculas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.setTextoOrigen(vista.getTextoOrigen());
                modelo.copiarTextoMinusculas();
                vista.setTextoDestino(modelo.getTextoDestino());
                actualizarEstadisticasDestino();
            }
        });

        vista.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.limpiar();
                vista.limpiarCampos();
                vista.actualizarEstadisticasOrigen(0, 0);
                vista.actualizarEstadisticasDestino(0, 0);
            }
        });
    }

    private void actualizarEstadisticasDestino() {
        String texto = modelo.getTextoDestino();
        int caracteres = modelo.contarCaracteres(texto);
        int palabras = modelo.contarPalabras(texto);
        vista.actualizarEstadisticasDestino(caracteres, palabras);
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}
