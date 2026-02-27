package main;

import view.Practica03bView;
import controller.Practica03bController;

public class Practica03_b {
    public static void main(String[] args) {
        Practica03bView vista = new Practica03bView("Administracion de Categorias");
        new Practica03bController(vista);
        vista.setVisible(true);
    }
}

