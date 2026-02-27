package main;

import view.Practica03View;
import controller.Practica03aController;

public class Practica03_a {
    public static void main(String[] args) {
        Practica03View vista = new Practica03View("Administracion de Productos");
        new Practica03aController(vista);
        vista.setVisible(true);
    }
}

