package utilidades;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.beans.PropertyVetoException;


public class BibliotecaFrames {
    

    public static void centrarFrame(JInternalFrame frame, JDesktopPane desktop) {
        int x = (desktop.getWidth() - frame.getWidth()) / 2;
        int y = (desktop.getHeight() - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

    public static void maximizarFrame(JInternalFrame frame) {
        try {
            frame.setMaximum(true);
        } catch (PropertyVetoException e) {
            System.err.println("Error al maximizar frame: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean estaAbierta(JDesktopPane desktop, Class<?> claseFrame) {
        JInternalFrame[] frames = desktop.getAllFrames();
        
        for (JInternalFrame frame : frames) {
            if (frame.getClass().equals(claseFrame)) {

                frame.toFront();
                
                try {

                    frame.setSelected(true);
                } catch (PropertyVetoException e) {
                    System.err.println("Error al seleccionar frame: " + e.getMessage());
                }
                
                return true;
            }
        }
        
        return false;
    }

    public static void restaurarFrame(JInternalFrame frame) {
        try {
            if (frame.isIcon()) {
                frame.setIcon(false);
            }
            if (frame.isMaximum()) {
                frame.setMaximum(false);
            }
        } catch (PropertyVetoException e) {
            System.err.println("Error al restaurar frame: " + e.getMessage());
        }
    }
}
