package main;

import view.Practica02cView;
import controller.Practica02cController;

public class Practica02_c {
    public static void main(String[] args) {
        Practica02cView vista = new Practica02cView();
        new Practica02cController(vista);
        vista.setVisible(true);
    }
}
