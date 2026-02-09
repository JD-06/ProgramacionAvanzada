package controlador;

import vista.Vejercicioclase02;
import modelo.Mejercicio02;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class Cejercicioclase02 implements ActionListener {
    
    private Vejercicioclase02 vista;
    private Mejercicio02 modelo;
    

    public Cejercicioclase02() {
        vista = new Vejercicioclase02();
        modelo = new Mejercicio02();

        vista.getComboBox().setModel(modelo.getListacombo());
        vista.getListadesplegable().setModel(modelo.getLista());
        
        vista.setVisible(true);
        
        vista.getBagregar().addActionListener(this);
        vista.getBeliminar().addActionListener(this);
        vista.getBsalir().addActionListener(this);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.getBagregar()) {
            
            String dato = vista.getTdato().getText().trim();
            
            if (!dato.isEmpty()) {
                modelo.agregarCombo(dato);
                modelo.agregarLista(dato);
                vista.limpiarText();
                
                JOptionPane.showMessageDialog(
                    vista,
                    "Equipo agregado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    vista,
                    "Por favor, ingrese un equipo",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
        
        if (e.getSource() == vista.getBeliminar()) {
            int s1 = vista.getComboBox().getSelectedIndex();
            int s2 = vista.getListadesplegable().getSelectedIndex();
            
            if (s1 < 0 && s2 < 0) {
                JOptionPane.showMessageDialog(
                    vista,
                    "Seleccione un elemento para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
            } else {
                int confirmacion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Está seguro de eliminar el elemento seleccionado?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirmacion == 0) {
                    if (s1 >= 0) {
                        modelo.eliminarCombo(s1);
                    }
                    if (s2 >= 0) {
                        modelo.eliminarLista(s2);
                    }
                    
                    JOptionPane.showMessageDialog(
                        vista,
                        "Elemento eliminado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        }
        
        if (e.getSource() == vista.getBsalir()) {
            int opcion = JOptionPane.showConfirmDialog(
                vista,
                "¿Desea salir del sistema?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
            );
            
            if (opcion == 0) {
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(
                    vista,
                    "Gracias por usar el sistema",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }
}
