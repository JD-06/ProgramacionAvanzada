package controlador;

import vista.Vprincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class Cprincipal implements ActionListener {
    
    private Vprincipal ventana;
    

    public Cprincipal() {
        ventana = new Vprincipal();
        ventana.setVisible(true);
        
        ventana.getMipresupuestacion().addActionListener(this);
        ventana.getMiobras().addActionListener(this);
        ventana.getMiconfiguracion().addActionListener(this);
        ventana.getMisalida().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        this.ventana.setEstadoMenus(false);
        
        if (e.getSource() == ventana.getMipresupuestacion()) {
            JOptionPane.showMessageDialog(
                ventana.getEscritorio(),
                "Módulo de Presupuestación\n(En desarrollo)",
                "Información",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        
        if (e.getSource() == ventana.getMiobras()) {
            JOptionPane.showMessageDialog(
                ventana.getEscritorio(),
                "Módulo de Gestión de Obras\n(En desarrollo)",
                "Información",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        
        if (e.getSource() == ventana.getMiconfiguracion()) {
            JOptionPane.showMessageDialog(
                ventana.getEscritorio(),
                "Módulo de Configuración\n(En desarrollo)",
                "Información",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        
        if (e.getSource() == ventana.getMisalida()) {
            int opcion = JOptionPane.showConfirmDialog(
                ventana.getEscritorio(),
                "¿Está seguro que desea salir del sistema?",
                "Confirmación de Salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (opcion == 0) {
                ventana.dispose();
                System.exit(0);
            }
        }
        
        // Habilitar menús nuevamente
        this.ventana.setEstadoMenus(true);
    }
}
