package main;

import view.Practica03bView;
import controller.Practica03cController;

public class Practica03_c {
    public static void main(String[] args) {
        Practica03bView vista = new Practica03bView("Administracion de Categorias con Archivo");
        new Practica03cController(vista);
        vista.setVisible(true);
    }
}

