package ejemplos.calculadoragrid;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorCalculadoraGrid {
    private CalculadoraGridModelo modelo;
    private CalculadoraGridVista vista;

    public ControladorCalculadoraGrid(CalculadoraGridModelo modelo, CalculadoraGridVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        inicializarEventos();
    }

    private void inicializarEventos() {

        JButton[] botonesNumeros = vista.getBotonesNumeros();
        for (int i = 0; i < botonesNumeros.length; i++) {
            final String digito = String.valueOf(i);
            botonesNumeros[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    modelo.agregarDigito(digito);
                    vista.actualizarDisplay(modelo.getDisplay());
                }
            });
        }

        vista.getBtnPunto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.agregarPunto();
                vista.actualizarDisplay(modelo.getDisplay());
            }
        });

        vista.getBtnSumar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.setOperacion("+");
                vista.actualizarDisplay(modelo.getDisplay());
            }
        });

        vista.getBtnRestar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.setOperacion("-");
                vista.actualizarDisplay(modelo.getDisplay());
            }
        });

        vista.getBtnMultiplicar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.setOperacion("×");
                vista.actualizarDisplay(modelo.getDisplay());
            }
        });

        vista.getBtnDividir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.setOperacion("÷");
                vista.actualizarDisplay(modelo.getDisplay());
            }
        });

        vista.getBtnPorcentaje().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.setOperacion("%");
                vista.actualizarDisplay(modelo.getDisplay());
            }
        });

        vista.getBtnIgual().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.calcular();
                vista.actualizarDisplay(modelo.getDisplay());
            }
        });

        vista.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.limpiar();
                vista.actualizarDisplay(modelo.getDisplay());
            }
        });

        vista.getBtnBorrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.borrar();
                vista.actualizarDisplay(modelo.getDisplay());
            }
        });

        vista.getBtnSigno().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.cambiarSigno();
                vista.actualizarDisplay(modelo.getDisplay());
            }
        });
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}
