package main;

import controller.AppService;
import controller.MainController;
import model.StorageType;
import view.MenuPrincipalFrame;

import javax.swing.SwingUtilities;

public class PracticaFinal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppService service = new AppService();
            service.setStorageType(StorageType.TXT);
            MenuPrincipalFrame frame = new MenuPrincipalFrame();
            new MainController(frame, service);
            frame.setVisible(true);
        });
    }
}
