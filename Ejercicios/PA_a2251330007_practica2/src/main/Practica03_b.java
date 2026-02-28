package main;

import controller.AppService;
import controller.CategoriaController;
import model.StorageType;
import view.CategoriaInternalFrame;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Practica03_b {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppService service = new AppService();
            service.setStorageType(StorageType.TXT);

            JFrame frame = new JFrame("Parte1 - Practica03_b");
            frame.setSize(700, 470);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JDesktopPane desktopPane = new JDesktopPane();
            frame.setContentPane(desktopPane);

            CategoriaInternalFrame child = new CategoriaInternalFrame();
            desktopPane.add(child);
            new CategoriaController(child, service);
            child.setVisible(true);

            frame.setVisible(true);
        });
    }
}
