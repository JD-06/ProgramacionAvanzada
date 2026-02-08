package ejemplos.layouts;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ModeloLayout modelo = new ModeloLayout();
                VistaLayout vista = new VistaLayout();
                ControladorLayout controlador = new ControladorLayout(modelo, vista);

                controlador.iniciar();
            }
        });
    }
}
