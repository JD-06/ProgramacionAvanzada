package main;

import java.awt.EventQueue;
import view.Practica02bView;
import controller.Practica02bController;

public class Practica02_b {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Practica02bView vista = new Practica02bView("Frame Practica02_b (Vista-Controlador)");
                    new Practica02bController(vista);
                    vista.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
