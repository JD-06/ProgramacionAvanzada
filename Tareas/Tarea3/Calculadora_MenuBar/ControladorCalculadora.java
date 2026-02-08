package ejemplos.calculadoramenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorCalculadora {
    private CalculadoraModelo modelo;
    private CalculadoraVista vista;

    public ControladorCalculadora(CalculadoraModelo modelo, CalculadoraVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        inicializarEventos();
    }

    private void inicializarEventos() {

        vista.getBtnCalcular().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarCalculo();
            }
        });

        vista.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.resetear();
                vista.limpiar();
            }
        });

        vista.getBtnSeno().addActionListener(e -> calcularSeno());
        vista.getBtnCoseno().addActionListener(e -> calcularCoseno());
        vista.getBtnTangente().addActionListener(e -> calcularTangente());
        vista.getBtnRaiz().addActionListener(e -> calcularRaiz());
        vista.getBtnLog().addActionListener(e -> calcularLogaritmo());

        vista.getItemNuevo().addActionListener(e -> {
            modelo.resetear();
            vista.limpiar();
        });

        vista.getItemVerHistorial().addActionListener(e -> {
            vista.mostrarHistorial(modelo.getHistorial());
        });

        vista.getItemLimpiarHistorial().addActionListener(e -> {
            modelo.limpiarHistorial();
            vista.limpiarHistorial();
        });

        vista.getItemSalir().addActionListener(e -> System.exit(0));

        vista.getItemModoBasico().addActionListener(e -> {
            modelo.setEsCientifico(false);
            vista.mostrarModoCientifico(false);
        });

        vista.getItemModoCientifico().addActionListener(e -> {
            modelo.setEsCientifico(true);
            vista.mostrarModoCientifico(true);
        });

        vista.getItemSeno().addActionListener(e -> calcularSeno());
        vista.getItemCoseno().addActionListener(e -> calcularCoseno());
        vista.getItemTangente().addActionListener(e -> calcularTangente());
        vista.getItemRaiz().addActionListener(e -> calcularRaiz());
        vista.getItemLogaritmo().addActionListener(e -> calcularLogaritmo());

        vista.getItemAcercaDe().addActionListener(e -> vista.mostrarAcercaDe());
    }

    private void realizarCalculo() {
        try {
            double op1 = vista.getOperando1();
            double op2 = vista.getOperando2();
            String operacion = vista.getOperacion();

            modelo.setOperando1(op1);
            modelo.setOperando2(op2);
            modelo.setOperacion(operacion);

            double resultado = modelo.calcular();
            vista.setResultado(resultado);
            vista.mostrarHistorial(modelo.getHistorial());

        } catch (NumberFormatException ex) {
            vista.mostrarError("Por favor, ingresa números válidos");
        } catch (ArithmeticException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    private void calcularSeno() {
        try {
            double angulo = vista.getAngulo();
            double resultado = modelo.calcularSeno(angulo);
            vista.setResultado(resultado);
            vista.mostrarHistorial(modelo.getHistorial());
        } catch (NumberFormatException ex) {
            vista.mostrarError("Por favor, ingresa un ángulo válido");
        }
    }

    private void calcularCoseno() {
        try {
            double angulo = vista.getAngulo();
            double resultado = modelo.calcularCoseno(angulo);
            vista.setResultado(resultado);
            vista.mostrarHistorial(modelo.getHistorial());
        } catch (NumberFormatException ex) {
            vista.mostrarError("Por favor, ingresa un ángulo válido");
        }
    }

    private void calcularTangente() {
        try {
            double angulo = vista.getAngulo();
            double resultado = modelo.calcularTangente(angulo);
            vista.setResultado(resultado);
            vista.mostrarHistorial(modelo.getHistorial());
        } catch (NumberFormatException ex) {
            vista.mostrarError("Por favor, ingresa un ángulo válido");
        }
    }

    private void calcularRaiz() {
        try {
            double numero = vista.getAngulo();
            double resultado = modelo.calcularRaizCuadrada(numero);
            vista.setResultado(resultado);
            vista.mostrarHistorial(modelo.getHistorial());
        } catch (NumberFormatException ex) {
            vista.mostrarError("Por favor, ingresa un número válido");
        } catch (ArithmeticException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    private void calcularLogaritmo() {
        try {
            double numero = vista.getAngulo();
            double resultado = modelo.calcularLogaritmo(numero);
            vista.setResultado(resultado);
            vista.mostrarHistorial(modelo.getHistorial());
        } catch (NumberFormatException ex) {
            vista.mostrarError("Por favor, ingresa un número válido");
        } catch (ArithmeticException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}
