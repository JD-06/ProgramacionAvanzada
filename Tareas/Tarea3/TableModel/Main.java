package ejemplos.tablemodel;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ModeloTableModel modelo = new ModeloTableModel();
                VistaTableModel vista = new VistaTableModel();
                ControladorTableModel controlador = new ControladorTableModel(modelo, vista);

                controlador.iniciar();
            }
        });
    }
}
