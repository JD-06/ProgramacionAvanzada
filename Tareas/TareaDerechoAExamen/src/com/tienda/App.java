package com.tienda;

import com.tienda.view.MainView;
import com.tienda.controller.MainController;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {
    public static void main(String[] args) {
        try {
            // Set look and feel to system default
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainView view = new MainView();
            new MainController(view);
            view.setVisible(true);
        });
    }
}