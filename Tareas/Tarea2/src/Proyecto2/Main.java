package Proyecto2;

public class Main {
    public static void main(String[] args) {
     
        /*
        ModeloTexto mod = new ModeloTexto();
        VistaTexto view = new VistaTexto();
        ControladorTexto ctrl = new ControladorTexto(view, mod);
        ctrl.iniciar();
        */


        ModeloConversor modC = new ModeloConversor();
        VistaConversor viewC = new VistaConversor();
        ControladorConversor ctrlC = new ControladorConversor(viewC, modC);
        ctrlC.iniciar();

   
        /*
        VistaTabla viewT = new VistaTabla();
        ControladorTabla ctrlT = new ControladorTabla(viewT);
        ctrlT.iniciar();
        */
    }
}