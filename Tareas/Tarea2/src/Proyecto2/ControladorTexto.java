package Proyecto2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorTexto implements ActionListener {
    private VistaTexto vista;
    private ModeloTexto modelo;

    public ControladorTexto(VistaTexto vista, ModeloTexto modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        
        this.vista.getBtnCopiar().addActionListener(this);
    }

    public void iniciar() {
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String texto = vista.getTxtOrigen().getText();
        
        modelo.setTexto(texto);
        modelo.incrementarContador();
        
        // 3. Actualizar la vista
        vista.getTxtDestino().setText(modelo.getTexto());
        vista.getLblContador().setText("Veces copiado: " + modelo.getContador());
    }
}
