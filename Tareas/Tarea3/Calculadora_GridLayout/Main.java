package ejemplos.calculadoragrid;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ModeloCalculadoraGrid modelo = new ModeloCalculadoraGrid();
                VistaCalculadoraGrid vista = new VistaCalculadoraGrid();
                ControladorCalculadoraGrid controlador = new ControladorCalculadoraGrid(modelo, vista);

                controlador.iniciar();
            }
        });
    }
}
