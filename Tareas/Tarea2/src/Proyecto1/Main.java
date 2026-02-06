package Proyecto1;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
 
            
            new VistaLogin().setVisible(true);
            new VistaPrincipal().setVisible(true);
        });
    }
}