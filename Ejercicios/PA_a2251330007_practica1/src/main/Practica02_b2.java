package main;

import java.awt.EventQueue;
import view.Practica02bView;
import controller.Practica02b2Controller;

public class Practica02_b2 {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Practica02bView vista = new Practica02bView("Frame Practica02_b2 (Nemonico MVC)");
                    new Practica02b2Controller(vista);
                    vista.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

