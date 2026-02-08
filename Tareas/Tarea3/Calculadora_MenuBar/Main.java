package ejemplos.calculadoramenu;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ModeloCalculadora modelo = new ModeloCalculadora();
                VistaCalculadora vista = new VistaCalculadora();
                ControladorCalculadora controlador = new ControladorCalculadora(modelo, vista);

                controlador.iniciar();
            }
        });
    }
}
