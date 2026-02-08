package ejemplos.dialogos;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ModeloDialogos modelo = new ModeloDialogos();
                VistaDialogos vista = new VistaDialogos();
                ControladorDialogos controlador = new ControladorDialogos(modelo, vista);

                controlador.iniciar();
            }
        });
    }
}
