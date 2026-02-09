package controlador;

import vista.Vejercicioclase01;
import modelo.Lista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class Cejercicioclase01_b implements ActionListener {
    
    private Vejercicioclase01 vista;
    private Lista lista;

    public Cejercicioclase01_b() {
        vista = new Vejercicioclase01();
        lista = new Lista();
        vista.setVisible(true);
        
        vista.bagregar().addActionListener(this);
        vista.bsalir().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.bsalir()) {
            int opcion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro que desea salir?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (opcion == 0) {
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(
                    vista,
                    "Gracias por continuar usando el sistema",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
        
        if (e.getSource() == vista.bagregar()) {
            
            String nombre = vista.getT1().trim();
            String apellido = vista.getT2().trim();
            
            if (nombre.isEmpty() || apellido.isEmpty()) {
                JOptionPane.showMessageDialog(
                    vista,
                    "Por favor, complete todos los campos",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                return; 
            }
            
            lista.insertar(nombre, apellido);
            vista.limparText();
            vista.setTr(lista.info());
            
            JOptionPane.showMessageDialog(
                vista,
                "Persona registrada exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
