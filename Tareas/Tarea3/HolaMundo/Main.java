package ejemplos.holamundo;

public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                ModeloHolaMundo modelo = new ModeloHolaMundo();
                VistaHolaMundo vista = new VistaHolaMundo();
                ControladorHolaMundo controlador = new ControladorHolaMundo(modelo, vista);

                controlador.iniciar();
            }
        });
    }
}
