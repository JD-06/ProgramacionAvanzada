package main;

import view.MainMenuView;
import controller.MainMenuController;
import javax.swing.SwingUtilities;

public class MenuPrincipal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainMenuView vista = new MainMenuView();
                new MainMenuController(vista);
                vista.setVisible(true);
            }
        });
    }
}
