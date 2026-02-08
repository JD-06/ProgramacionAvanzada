package ejemplos.copiartexto;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ModeloCopiarTexto modelo = new ModeloCopiarTexto();
                VistaCopiarTexto vista = new VistaCopiarTexto();
                ControladorCopiarTexto controlador = new ControladorCopiarTexto(modelo, vista);

                controlador.iniciar();
            }
        });
    }
}
