package main;

import view.Practica03View;
import controller.Practica03dController;

public class Practica03_d {
    public static void main(String[] args) {
        Practica03View vista = new Practica03View("Administracion de Productos MVC");
        new Practica03dController(vista);
        vista.setVisible(true);
    }
}

