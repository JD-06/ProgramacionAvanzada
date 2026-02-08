package ejemplos.conversormonedas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorConversorMonedas {
    private ConversorMonedasModelo modelo;
    private ConversorMonedasVista vista;

    public ControladorConversorMonedas(ConversorMonedasModelo modelo, ConversorMonedasVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        inicializar();
        inicializarEventos();
    }

    private void inicializar() {
        vista.cargarMonedas(modelo.getMonedasDisponibles());
        actualizarTasaCambio();
    }

    private void inicializarEventos() {

        vista.getBtnConvertir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertir();
            }
        });

        vista.getBtnIntercambiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.intercambiarMonedas();
                actualizarTasaCambio();
            }
        });

        vista.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.limpiar();
            }
        });

        vista.cmbMonedaOrigen.addActionListener(e -> actualizarTasaCambio());
        vista.cmbMonedaDestino.addActionListener(e -> actualizarTasaCambio());
    }

    private void convertir() {
        try {
            double cantidad = vista.getCantidad();
            String monedaOrigen = vista.getMonedaOrigen();
            String monedaDestino = vista.getMonedaDestino();

            modelo.setCantidad(cantidad);
            modelo.setMonedaOrigen(monedaOrigen);
            modelo.setMonedaDestino(monedaDestino);

            double resultado = modelo.convertir();
            vista.setResultado(resultado, monedaDestino);

        } catch (NumberFormatException ex) {
            vista.mostrarError("Por favor, ingresa una cantidad válida");
        }
    }

    private void actualizarTasaCambio() {
        String origen = vista.getMonedaOrigen();
        String destino = vista.getMonedaDestino();
        double tasa = modelo.getTasaCambio(origen, destino);
        vista.setTasaCambio(tasa, origen, destino);
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}
