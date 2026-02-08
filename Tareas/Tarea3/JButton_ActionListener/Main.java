package ejemplos.jbutton;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ModeloBoton modelo = new ModeloBoton();
                VistaBoton vista = new VistaBoton();
                ControladorBoton controlador = new ControladorBoton(modelo, vista);

                controlador.iniciar();
            }
        });
    }
}
