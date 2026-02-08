package ejemplos.conversormonedas;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ModeloConversorMonedas modelo = new ModeloConversorMonedas();
                VistaConversorMonedas vista = new VistaConversorMonedas();
                ControladorConversorMonedas controlador = new ControladorConversorMonedas(modelo, vista);

                controlador.iniciar();
            }
        });
    }
}
